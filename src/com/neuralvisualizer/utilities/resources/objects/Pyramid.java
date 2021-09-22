package com.neuralvisualizer.utilities.resources.objects;

import com.configuration.ConfigurationMap;
import com.neuralvisualizer.utilities.resources.structures.Face;

import java.util.LinkedList;
import java.util.List;

//3D object that represents a connection between layers
public class Pyramid extends Shape{
	//connection represents where does the previous shape join the next
    Point connection;
    
    public Pyramid(Point p1, Point p2, Point p3, Point p4, Point connection){
    	setConfig(new ConfigurationMap());
        setFaces(new LinkedList<>());
        Point[] points= new Point[4];
        points[0]=p1;
        points[1]=p2;
        points[2]=p3;
        points[3]=p4;
        setPoints(points);
        this.connection = connection;
    }

    public Point getCenterTopFace(){
        return getCenterFace(connection,getPoints()[0],getPoints()[1]);
    }
    
    public Point getCenterFace(Point p1,Point p2,Point p3){
        double x=0;
        double y=0;
        double z=0;
        x=p1.getX()+p2.getX()+p3.getX();
        y=p1.getY()+p2.getY()+p3.getY();
        z=p1.getZ()+p2.getZ()+p3.getZ();
        return new Point(x/3,y/3,z/3);
    }
    
    @Override
    public List<Shape> getUnderlyingShape() {
        return new LinkedList<>();
    }
    
	private Face buildCompactNumberFace(){
        String s=" <text x=\"" + this.getCenterTopFace().getX() + "\" y=\"" + this.getCenterTopFace().getY() + "\" font-family=\"" + this.getConfig().getFont() + "\" font-size=\"" + this.getConfig().getNumberSize() + "\" fill= \"" + this.getConfig().getNumberFill() + "\">" +(int) this.getConfig().getPyramidWidthNumber() + "x" + (int) this.getConfig().getPyramidHeightNumber() + "</text>";
        return new Face(Double.MAX_VALUE, s);
    }

    private Face buildFace(Point p1, Point p2, Point p3){
        String s=p1.getX()+","+p1.getY()+" "+p2.getX()+","+p2.getY()+" "+p3.getX()+","+p3.getY()+" "+p1.getX()+","+p1.getY()+" ";
        String sfin="<polyline points=\""
        		+s
        		+"\"\n"
        		+ "style=\"fill:"
        		+this.getConfig().getPyramidFill()
        		+";stroke:"
        		+this.getConfig().getPyramidColor()
        		+";stroke-width:"
        		+this.getConfig().getPyramidStrokeWidth()
        		+";fill-opacity:"
        		+this.getConfig().getPyramidAlpha()
        		+"\" />\n";
      
        double maximumZ= Double.max(p1.getZ(),p2.getZ());
        maximumZ=Double.max(maximumZ,p3.getZ());
        maximumZ= Double.max(maximumZ,connection.getZ());
        
        return new Face(maximumZ, sfin);
    }

    @Override
    public void build() {
    	List<Face> faces = new LinkedList<>();
    	if (this.getConfig().getShowNumbers())
    		faces.add(buildCompactNumberFace());
    	
        faces.add(buildFace(getPoints()[0],getPoints()[3],connection));
        faces.add(buildFace(getPoints()[3],getPoints()[2],connection));
        faces.add(buildFace(getPoints()[2],getPoints()[1],connection));
        faces.add(buildFace(getPoints()[1],getPoints()[0],connection));
        
        setFaces(faces);
    }

    @Override
    public Point getCenterPoint() {
        return connection;
    }

	@Override
	public double getStart() {
		throw new UnsupportedOperationException();
	}

	@Override
	public double getEnd() {
		throw new UnsupportedOperationException();
	}


}
