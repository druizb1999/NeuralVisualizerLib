package com.neuralvisualizer.utilities.resources.objects;


import com.configuration.ConfigurationMap;
import com.neuralvisualizer.utilities.resources.structures.Face;

import java.util.LinkedList;
import java.util.List;

//3D object that represents the outer layers of a convolutional layer
public class Cube extends Shape{
	
    private static final String TEXTCLOSE= "</text>";
    private static final String TEXTOPEN= " <text x=\"";
    private static final String YCOORD= "\" y=\"";
    private static final String TEXTFILL="\" fill= \"" ;
    private static final String FONTSIZE= "\" font-size=\"";
    private static final String FONTFAMILY= "\" font-family=\"";
    private static final String CONFIGCLOSE="\">";
    

    boolean dense;
    List<Shape> shapeList;
    private int inputHeight;
    private int inputWidth;
    private int inputDepth;



    public Cube(int height, int width, int depth, boolean log, double logMult, Kernel k){
    	this.inputHeight=height;
    	this.inputWidth=width;
    	this.inputDepth=depth;
    	setConfig(new ConfigurationMap());
        setFaces(new LinkedList<>());
        this.shapeList=new LinkedList<>();
        this.shapeList.add(k);
        if (log) {
            createCube(Math.log(height) * logMult, Math.log(width) * logMult, Math.log(depth) * logMult);
        }
        else {
            createCube(height, width, depth);
        }
    }

    public void addShape(Shape s){
        this.shapeList.add(s);
    }

    private void createCube(double height, double width, double depth){
    createCubeShape(width,height,depth);
    
}

    public double getStart(){
        return getPoints()[0].z;
    }

    public double getEnd(){
    return getPoints()[4].z;
    }
    
    public void setDense(boolean dense){
        this.dense=dense;
    }

    public boolean isDense(){
        return dense;
    }

    public void removeKernel(){
        shapeList=new LinkedList<>();
    }

    public Point getBackSquareCenter(){
        return getCenterFace(getPoints()[4],getPoints()[5],getPoints()[6],getPoints()[7]);
    }
    public Point getFrontSquareCenter(){
        return getCenterFace(getPoints()[0],getPoints()[1],getPoints()[2],getPoints()[3]);
    }
    public Point getCenterTopFace(){
        return getCenterFace(getPoints()[2],getPoints()[3],getPoints()[6],getPoints()[7]);
    }

    public Point getCenterLine(Point p1,Point p2){
        return new Point((p1.getX()+p2.getX())/2,(p1.getY()+p2.getY())/2,(p1.getZ()+p2.getZ())/2);
    }

    public Point getCenterFace(Point p1,Point p2,Point p3, Point p4){
        double x=0;
        double y=0;
        double z=0;
        x=p1.getX()+p2.getX()+p3.getX()+p4.getX();
        y=p1.getY()+p2.getY()+p3.getY()+p4.getY();
        z=p1.getZ()+p2.getZ()+p3.getZ()+p4.getZ();
        return new Point(x/4,y/4,z/4);
    }


    public Point getFrontSquareCenterTop(){
        return getCenterLine(getPoints()[2],getPoints()[3]);
    }
    public Point getFrontSquareCenterSide(){
        return getCenterLine(getPoints()[0],getPoints()[3]);
    }
    public Point getTopCenterDepth(){
        return getCenterLine(getPoints()[2],getPoints()[6]);
    }

    @Override
    public List<Shape> getUnderlyingShape() {
        return shapeList;
    }


    private Face buildFace(Point p1, Point p2, Point p3, Point p4){
        String s=p1.getX()+","+p1.getY()+" "+p2.getX()+","+p2.getY()+" "+p3.getX()+","+p3.getY()+" "+p4.getX()+","+p4.getY()+" "+p1.getX()+","+p1.getY()+" ";
        String sfin="<polyline points=\""
        +s
        +"\"\n"
        + "style=\"fill:"
        +getConfig().getCubeFill()
        +";stroke:"
        +getConfig().getCubeColor()
        +";stroke-width:"
        +getConfig().getCubeLineWidth()
        +";fill-opacity:"
        +getConfig().getCubeAlpha()
        +"\" />\n";
        
        double maximumZ= Double.max(p1.getZ(),p2.getZ());
        maximumZ=Double.max(maximumZ,p3.getZ());
        maximumZ= Double.max(maximumZ,p4.getZ());
        
        return new Face(maximumZ, sfin);
    }

	private Face buildCompactNumberFace(){
		String numbers ="";
		if (!this.dense)
			numbers=this.inputHeight+" x "+ this.inputWidth+" x "+this.inputDepth;
		else
			numbers = ((Integer) this.inputHeight).toString();
		String s=TEXTOPEN + this.getCenterTopFace().getX() + YCOORD + this.getCenterTopFace().getY() + FONTFAMILY +  getConfig().getFont() + FONTSIZE + this.getConfig().getNumberSize()  + TEXTFILL + this.getConfig().getNumberFill() + CONFIGCLOSE + numbers + TEXTCLOSE;
		return new Face(Double.MAX_VALUE, s);
    }
    
	private Face buildHeightNumberFace(){
        String s=TEXTOPEN + this.getFrontSquareCenterTop().getX() +  YCOORD + this.getFrontSquareCenterTop().getY() + FONTFAMILY + getConfig().getFont() + FONTSIZE + this.getConfig().getNumberSize() + TEXTFILL + this.getConfig().getNumberFill() + CONFIGCLOSE + this.getHeight() + TEXTCLOSE;
        return new Face(Double.MAX_VALUE, s);
    }

    private Face buildWidthNumberFace(){
        String s=TEXTOPEN + this.getFrontSquareCenterSide().getX() +  YCOORD + this.getFrontSquareCenterSide().getY() + FONTFAMILY +getConfig().getFont() + FONTSIZE + this.getConfig().getNumberSize() + TEXTFILL + this.getConfig().getNumberFill() + CONFIGCLOSE + this.getWidth() + TEXTCLOSE;
        return new Face(Double.MAX_VALUE, s);
    }

    private Face buildDepthNumberFace(){
        String s=TEXTOPEN + this.getTopCenterDepth().getX() + YCOORD + this.getTopCenterDepth().getY() + FONTFAMILY + getConfig().getFont() + FONTSIZE + this.getConfig().getNumberSize() + TEXTFILL + this.getConfig().getNumberFill() + CONFIGCLOSE + this.getDepth() + TEXTCLOSE;
        return new Face(Double.MAX_VALUE, s);
    }

    public void build(){
    	
    List<Face> faces = new LinkedList<>();
    if (getConfig().getShowNumbers()) {
    	if (getConfig().getCompactNumbers()) {
    		faces.add(buildCompactNumberFace());
    	}
    	else {
	        faces.add(buildHeightNumberFace());
	          if (!this.isDense()) {
	              faces.add(buildWidthNumberFace());
	              faces.add(buildDepthNumberFace());
	          }
    	}
    }

    faces.add(buildFace(getPoints()[0],getPoints()[3],getPoints()[2],getPoints()[1]));
    faces.add(buildFace(getPoints()[4],getPoints()[7],getPoints()[6],getPoints()[5]));
    faces.add(buildFace(getPoints()[0],getPoints()[4],getPoints()[7],getPoints()[3]));
    faces.add(buildFace(getPoints()[3],getPoints()[2],getPoints()[6],getPoints()[7]));
    faces.add(buildFace(getPoints()[2],getPoints()[1],getPoints()[5],getPoints()[6]));
    faces.add(buildFace(getPoints()[0],getPoints()[1],getPoints()[5],getPoints()[4]));

    for(Shape s:shapeList){
        s.build();
        faces.addAll(s.getFaces());
    }
    
    setFaces(faces);
}

	public void removeFrontConnection() {
		for (Shape s: this.shapeList) {
			if (s instanceof Kernel) {
				((Kernel) s).removeConnections();
			}
		}
		
	}
}
