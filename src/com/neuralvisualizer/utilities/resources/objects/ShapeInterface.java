package com.neuralvisualizer.utilities.resources.objects;

import com.neuralvisualizer.utilities.resources.structures.Face;

import java.util.List;

//Interface with the methods a shape should have
public interface ShapeInterface {
    
	//Gets a shape contained in the current one eg: a kernel contained inside a cube
    public abstract List<Shape> getUnderlyingShape();
    //Builds the shape in the 3D space
    public abstract void build();
    //Gets the point that signifies the start in space of a shape
    public abstract double getStart();
    //Gets the point that signifies the end in space of a shape
    public abstract double getEnd();
    //Gets the center of a shape
    public abstract Point getCenterPoint();
    //Gets the faces that form a shape and its underlying shapes
    public abstract List<Face> getFaces();
    //Translates the shape
    public void translate(double x, double y, double z);
    //Scales the shape
    public void scale(double x, double y, double z);
    //Rotates the shape on X
    public void rotateX(double theta);
    //Rotates the shape on Y
    public void rotateY(double theta);
    //Rotates the shape on Z
    public void rotateZ(double theta);

}
