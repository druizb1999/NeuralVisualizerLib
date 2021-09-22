package com.neuralvisualizer.utilities.resources.objects;

import com.neuralvisualizer.utilities.resources.structures.Face;

import java.util.LinkedList;
import java.util.List;


//3D figure that joins dense layers with cubes and distant layers
public class Arrow extends Shape {

    boolean dense;


    public Arrow(Point from, Point to){
       Point [] points = new Point[2];
       points[0]=from;
       points[1]=to;
       setPoints(points);
    }
    @Override
    public List<Shape> getUnderlyingShape() {
        return new LinkedList<>();
    }

    @Override
    public double getStart() {
        return getPoints()[0].getX();
    }

    @Override
    public double getEnd() {
        return getPoints()[1].getX();
    }

    @Override
    public Point getCenterPoint() {
        return new Point((getPoints()[0].getX()+getPoints()[1].getX())/2,(getPoints()[0].getY()+getPoints()[1].getY())/2,(getPoints()[0].getZ()+getPoints()[1].getZ())/2);
    }

    @Override
    public List<Face> getFaces() {
        String s1= "<polyline points=\""+getPoints()[0].getX()+","+getPoints()[0].getY()+" "+getPoints()[1].getX()+","+getPoints()[1].getY()+"\" style=\"stroke:"+" black"+";stroke-width:"+"2"+";\" />\n";
        
        double maximumZ= Double.max(getPoints()[0].getZ(),getPoints()[1].getZ());
        
        Face face = new Face(maximumZ+1, s1);
        LinkedList<Face> toReturn = new LinkedList<>();
        toReturn.add(face);
        return toReturn;
    }
	@Override
	public void build() {
		throw new UnsupportedOperationException();
	}
}
