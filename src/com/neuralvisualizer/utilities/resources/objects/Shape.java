package com.neuralvisualizer.utilities.resources.objects;



import com.configuration.ConfigurationMap;
import com.neuralvisualizer.utilities.resources.structures.Face;

import java.util.List;
//Class from which all shapes inherit
public abstract class Shape implements ShapeInterface {
	
	//The array containing the points of the shape
	private Point[] points = null;
	//The shape being reversed or not
	private boolean reversed  = false;
	//Configuration of the shape
	private ConfigurationMap config  = null;
	//the list of faces of the shape
	private List<Face> faces;
    
    //Getters, setters and tranformation methods
    public Point[] getPoints() {
		return points;
	}

	public void setPoints(Point[] points) {
		this.points = points;
	}

	public ConfigurationMap getConfig() {
		return config;
	}

	public void setConfig(ConfigurationMap config) {
		this.config = config;
	}

	public List<Face> getFaces() {
		return faces;
	}

	public void setFaces(List<Face> faces) {
		this.faces = faces;
	}

	public boolean isReversed() {
    	return this.reversed;
    }
    
    public void setReversed(boolean reversed) {
    	this.reversed=reversed;
    }
    
    
    private void translateA(double x, double y, double z){
        for (Point p:points){
            p.translate(x, y, z);
        }
    }

    private void scaleA(double x, double y, double z){
        for (Point p:points){
            p.scale(x, y, z);
        }
    }

    private void rotateXA(double theta){
        for (Point p:points){
            p.rotateX(theta);
        }
    }

    private void rotateYA(double theta){
        for (Point p:points){
            p.rotateY(theta);
        }
    }

    private void rotateZA(double theta){
        for (Point p:points){
            p.rotateZ(theta);
        }
    }

    
    @Override
    public void translate(double x, double y, double z){
        this.translateA(x,y,z);
        for (Shape s:getUnderlyingShape()) {
            s.translate(x, y, z);
        }
    }

    @Override
    public void scale(double x, double y, double z){
        this.scaleA(x,y,z);
        for (Shape s:getUnderlyingShape()) {
            s.scale(x, y, z);
        }
    }

    @Override
    public void rotateX(double theta){
        this.rotateXA(theta);
        for (Shape s:getUnderlyingShape()) {
            s.rotateX(theta);
        }
    }

    @Override
    public void rotateY(double theta){
        this.rotateYA(theta);
        for (Shape s:getUnderlyingShape()) {
            s.rotateY(theta);
        }
    }

    @Override
    public void rotateZ(double theta){
        this.rotateZA(theta);
        for (Shape s:getUnderlyingShape()) {
            s.rotateZ(theta);
        }
    }
    
    
	public double getDepth() {
		if (getPoints().length==0) return 0;
		return Math.abs(getPoints()[0].z - getPoints()[getPoints().length-1].z);
	}

	public double getWidth() {
		if (getPoints().length==0) return 0;
		return Math.abs(getPoints()[0].x - getPoints()[getPoints().length-1].x);
	}

	public double getHeight() {
		if (getPoints().length==0) return 0;
		return Math.abs(getPoints()[0].y - getPoints()[getPoints().length-1].y);
	}


	public Point getCenterPoint() {
		double x = 0;
		double y = 0;
		double z = 0;
		for (Point p : getPoints()) {
			x += p.getX();
			y += p.getY();
			z += p.getZ();
		}
		return new Point(x / 8, y / 8, z / 8);
	}
    
    //Creates a generic default cube, this method is here because several shapes create cubes
    protected void createCubeShape(double width, double height, double depth) {
    	
    	Point[] pointsToBuild = new Point[8];
    	pointsToBuild[0] = new Point(-width / 2, height / 2, depth / 2);
    	pointsToBuild[1] = new Point(width / 2, height / 2, depth / 2);
    	pointsToBuild[2] = new Point(width / 2, -height / 2, depth / 2);
    	pointsToBuild[3] = new Point(-width / 2, -height / 2, depth / 2);

    	pointsToBuild[4] = new Point(-width / 2, height / 2, -depth / 2);
    	pointsToBuild[5] = new Point(width / 2, height / 2, -depth / 2);
    	pointsToBuild[6] = new Point(width / 2, -height / 2, -depth / 2);
    	pointsToBuild[7] = new Point(-width / 2, -height / 2, -depth / 2);
		setPoints(pointsToBuild);
    }

}
