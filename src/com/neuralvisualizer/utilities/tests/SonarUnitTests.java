package com.neuralvisualizer.utilities.tests;

import com.neuralvisualizer.utilities.resources.layers.Conv2D;
import com.neuralvisualizer.utilities.resources.layers.Layers;
import com.neuralvisualizer.utilities.resources.objects.*;
import com.neuralvisualizer.utilities.resources.structures.Matrix;
import org.junit.Assert;
import org.junit.Test;

public class SonarUnitTests {
	
	 @Test
	    public void MatrixTest() {
	        double[][] a={{1,2,-1},{2,0,1}};
	        double[][] b={{3,1},{0,-1},{-2,3}};
	        double[][] c={{2},{3},{4}};
	        double[][] d={{6,4,3}};
	        double[][] e={{1,2,3,4},{5,6,7,8}};
	        double[][] f={{1,2,3},{4,5,6},{7,8,9},{10,11,12}};
	        double[][] sl1={{5,-4},{4,5}};
	        double[][] sl2={{12,8,6},{18,12,9},{24,16,12}};
	        double[][] sl3={{70,80,90},{158,184,210}};
	        Matrix m1=new Matrix(a);
	        Matrix m2=new Matrix(b);
	        Matrix m3=new Matrix(c);
	        Matrix m4=new Matrix(d);
	        Matrix m5=new Matrix(e);
	        Matrix m6=new Matrix(f);
	        Assert.assertArrayEquals(sl1,m1.multiply(m2).getData());
	        Assert.assertArrayEquals(sl2,m3.multiply(m4).getData());
	        Assert.assertArrayEquals(sl3,m5.multiply(m6).getData());
	    }
    
    @Test 
    public void JumpPointTest(){
    	Point p1= new Point(5,5,5);
    	JumpPoint p = new JumpPoint(p1);
    	
    	Assert.assertEquals(p.getCenterPoint().getX(), p.getStart(),0.1);
    	Assert.assertEquals(p.getCenterPoint().getX(), p.getEnd(),0.1);
    	Assert.assertEquals(p.getCenterPoint(), p1);

    }
    
    @Test 
    public void CubeTest(){
    	Cube c = new Cube(10,10,10,false,10.0,null);
    	Point p1= new Point(0,-5,0);
    	Point p2= new Point(-5,0,0);
    	Point p3= new Point(5,0,0);
    	
    	
    	Assert.assertEquals(-5, c.getEnd(), 0.1);
    	Assert.assertEquals(c.getFrontSquareCenterTop().getY(), p1.getY(),0.1);
    	Assert.assertEquals(c.getFrontSquareCenterSide().getX(), p2.getX(),0.1);
    	Assert.assertEquals(c.getTopCenterDepth().getX(), p3.getX(),0.1);

    }
    
    @Test 
    public void PointTest(){
    	Point p1= new Point(0,0,0);
    	
    	p1.setX(3);
    	Assert.assertEquals(3, p1.getX(), 0.1);
    	
    	p1.setY(3);
    	Assert.assertEquals(3, p1.getY(), 0.1);
    	
    	double[][] vector = {{1},{1},{1},{1}};
        Matrix pointVector = new Matrix(vector);
    	p1.setPointVector(pointVector);
    	Assert.assertEquals(1, p1.getX(), 0.1);
    	Assert.assertEquals(1, p1.getY(), 0.1);
    	Assert.assertEquals(1, p1.getZ(), 0.1);

    }
    
    @Test 
    public void ArrowTest(){
    	Point p1= new Point(0,0,0);
    	Point p2= new Point(10,0,0);
    	
    	Arrow a1= new Arrow(p1,p2);
    	
    	
    	Assert.assertEquals(0,a1.getStart(), 0.1);
    	Assert.assertEquals(10,a1.getEnd(), 0.1);
    	Assert.assertEquals(5,a1.getCenterPoint().getX(),0.1);
    	

    }


    @Test 
    public void LayersColorTest(){
    	Layers l1=new Conv2D(13,13,384,3,3);
    	l1.setColor("#000000");
    	
    	Assert.assertEquals("#000000", l1.getColor());
    	

    }
    
    @Test 
    public void PyramidCenterTest(){
    	Point p1= new Point(0,0,0);
    	Point p2= new Point(10,0,0);
    	Point p3= new Point(0,10,0);
    	Point p4= new Point(10,10,0);
    	Point p5= new Point(15,15,15);
    	Pyramid pyr = new Pyramid(p1,p2,p3,p4,p5);
    	
    	Assert.assertEquals(15.0, pyr.getCenterPoint().getX(), 0.1);
    	

    }

}
