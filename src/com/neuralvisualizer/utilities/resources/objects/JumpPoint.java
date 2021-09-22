package com.neuralvisualizer.utilities.resources.objects;

import com.neuralvisualizer.utilities.resources.structures.Face;

import java.util.LinkedList;
import java.util.List;


//3D object that represents a point where distant layers join
public class JumpPoint extends Shape {

    public JumpPoint(Point p) {
    	Point [] points = new Point[1];
        points[0]=p;
        setPoints(points);
    }

    public Point getOnlyPoint(){
        return getPoints()[0];
    }

    @Override
    public List<Shape> getUnderlyingShape() {
        return new LinkedList<>();
    }

    @Override
    public void build() {
    	//Empty method because a jump does not print itself
    }

    @Override
    public double getStart() {
        return getPoints()[0].getX();
    }

    @Override
    public double getEnd() {
        return getPoints()[0].getX();
    }

    @Override
    public Point getCenterPoint() {
        return getPoints()[0];
    }

    @Override
    public LinkedList<Face> getFaces() {
        return new LinkedList<>();
    }
}
