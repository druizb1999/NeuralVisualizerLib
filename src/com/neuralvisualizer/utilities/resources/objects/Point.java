package com.neuralvisualizer.utilities.resources.objects;


import com.neuralvisualizer.utilities.resources.structures.Matrix;

//Class that represents a point in space
public class Point {
	//coordinates in space
    double x;
    double y;
    double z;
    //The matrix that gets multiplied for transformations
    Matrix pointVector;

    public Point(double x, double y, double z){
        this.x=x;
        this.y=y;
        this.z=z;
        double[][] vector = {{this.x},{this.y},{this.z},{1}};
        pointVector = new Matrix(vector);
    }

    //getters and setters
    public void setX(double x) {
        this.x = x;
        double[][] vector = {{x},{this.y},{this.z},{1}};
        pointVector = new Matrix(vector);
    }

    public void setY(double y) {
        this.y = y;
        double[][] vector = {{this.x},{y},{this.z},{1}};
        pointVector = new Matrix(vector);
    }

    public void setZ(double z) {
        this.z = z;
        double[][] vector = {{this.x},{this.y},{z},{1}};
        pointVector = new Matrix(vector);
    }

    public void setPointVector(Matrix pointVector) {
        this.pointVector = pointVector;
        updatePoint();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    //Updates de matrix of the point
    private void updatePoint(){
        this.x=this.pointVector.getData()[0][0];
        this.y=this.pointVector.getData()[1][0];
        this.z=this.pointVector.getData()[2][0];
    }
    
    //3D transformations, each with its new method of matrix multiplication applying directly the 
    //functions instead of multipliying the whole matrix
    public void translate(double x, double y, double z){
    	double [][] result = {{pointVector.getData()[0][0]+x},{pointVector.getData()[1][0]+y},{pointVector.getData()[2][0]+z},{1}};
        pointVector=new Matrix(result);
        updatePoint();
    }
    
    public void scale(double x, double y, double z){
    	double [][] result = {{pointVector.getData()[0][0]*x},{pointVector.getData()[1][0]*y},{pointVector.getData()[2][0]*z},{1}};
        pointVector=new Matrix(result);
        updatePoint();
    }
    
    public void rotateX(double theta){
    	double [][] result = {{pointVector.getData()[0][0]},{pointVector.getData()[1][0]*Math.cos(theta)-Math.sin(theta)*pointVector.getData()[2][0]},{pointVector.getData()[1][0]*Math.sin(theta)+Math.cos(theta)*pointVector.getData()[2][0]},{1}};
        pointVector=new Matrix(result);
        updatePoint();
    }
    
    public void rotateY(double theta){
    	double [][] result = {{pointVector.getData()[0][0]*Math.cos(theta)+Math.sin(theta)*pointVector.getData()[2][0]},{pointVector.getData()[1][0]},{-Math.sin(theta)*pointVector.getData()[0][0]+pointVector.getData()[2][0]*Math.cos(theta)},{1}};
        pointVector=new Matrix(result);
        updatePoint();
    }
    
    public void rotateZ(double theta){
    	double [][] result = {{Math.cos(theta)*pointVector.getData()[0][0]-Math.sin(theta)*pointVector.getData()[1][0]},{Math.sin(theta)*pointVector.getData()[0][0]+Math.cos(theta)*pointVector.getData()[1][0]},{pointVector.getData()[2][0]},{1}};
        pointVector=new Matrix(result);
        updatePoint();
    }
}
