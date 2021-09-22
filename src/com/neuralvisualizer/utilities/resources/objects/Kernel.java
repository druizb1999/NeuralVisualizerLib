package com.neuralvisualizer.utilities.resources.objects;

import com.configuration.ConfigurationMap;
import com.neuralvisualizer.utilities.resources.structures.Face;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

//3D object that represents the kernel of a convolutional layer
public class Kernel extends Shape {

	private List<Shape> shapeList;
	private List<Point> reversedConnections;
	private List<Point> connections;
    private double nWidth;
    private double nHeight;

	public Kernel(int height, int width, double depth, boolean log, double logMult) {
		nWidth=width;
		nHeight=height;
		setConfig(new ConfigurationMap());
		setReversed(false);

		shapeList = new LinkedList<>();
		setFaces(new LinkedList<>());
		connections = new LinkedList<>();
		reversedConnections = new LinkedList<>();
		if (log) {
			createKernel(Math.log(height) * logMult, Math.log(width) * logMult, Math.log(depth) * logMult);
		} else {
			createKernel(height, width, depth);
		}
	}

	public void removeConnections() {
		this.connections.clear();
	}

	private void createKernel(double height, double width, double depth) {
		createCubeShape(width,height,depth);
	}

	public double getStart() {
		return getPoints()[0].z;
	}

	public void setConnection(double x, double y, double z) {
		connections.add(new Point(x, y, z));
	}

	public void setReversedConnection(double x, double y, double z) {
		reversedConnections.add(new Point(x, y, z));
	}

	public double getEnd() {
		return getPoints()[4].z;
	}


	private Face buildFace(Point p1, Point p2, Point p3, Point p4) {
		if (Double.isNaN(p1.getX()) || Double.isNaN(p2.getX()) || Double.isNaN(p3.getX()) || Double.isNaN(p4.getX())) {
			return new Face(0, "");
		}

		String s = p1.getX() + "," + p1.getY() + " " + p2.getX() + "," + p2.getY() + " " + p3.getX() + "," + p3.getY()
				+ " " + p4.getX() + "," + p4.getY() + " " + p1.getX() + "," + p1.getY() + " ";
		String sfin = "<polyline points=\"" + s + "\"\n" + "style=\"fill:"
				+ getConfig().getKernelFill() + ";stroke:"
				+ getConfig().getKernelColor()+ ";stroke-width:"
				+ getConfig().getKernelLineWidth() + ";fill-opacity:"
				+ getConfig().getKernelAlpha() + "\" />\n";

		double maximumZ = Double.max(p1.getZ(), p2.getZ());
		maximumZ = Double.max(maximumZ, p3.getZ());
		maximumZ = Double.max(maximumZ, p4.getZ());

		return new Face(maximumZ, sfin);

		
	}

	@Override
	public List<Shape> getUnderlyingShape() {
		return shapeList;
	}

	public void build() {
		List<Face> faces= new LinkedList<>();

		faces.add(buildFace(getPoints()[0], getPoints()[3], getPoints()[2], getPoints()[1]));
		faces.add(buildFace(getPoints()[0], getPoints()[4], getPoints()[7], getPoints()[3]));
		faces.add(buildFace(getPoints()[3], getPoints()[2], getPoints()[6], getPoints()[7]));
		faces.add(buildFace(getPoints()[2], getPoints()[1], getPoints()[5], getPoints()[6]));
		faces.add(buildFace(getPoints()[0], getPoints()[3], getPoints()[2], getPoints()[1]));
		faces.add(buildFace(getPoints()[0], getPoints()[1], getPoints()[5], getPoints()[4]));

		Point[] pointAux = new Point[4];
		Point[] pointReversed = new Point[4];

		for (int x = 0, i = 4; i < 8; x++, i++) {
			pointReversed[x] = new Point(getPoints()[i].x, getPoints()[i].y, getPoints()[i].z);
		}
		for (int i = 0; i < 4; i++) {
			pointAux[i] = new Point(getPoints()[i].x, getPoints()[i].y, getPoints()[i].z);
		}

		for (Point p : connections) {
			Pyramid pyr = new Pyramid(pointAux[0], pointAux[1], pointAux[2], pointAux[3], p);
			this.shapeList.add(pyr);
			setPyramidAttributes(pyr);
			pyr.build();
			faces.addAll(pyr.getFaces());
		}
		for (Point p : reversedConnections) {
			Pyramid pyr = new Pyramid(pointReversed[0], pointReversed[1], pointReversed[2], pointReversed[3], p);
			this.shapeList.add(pyr);
			setPyramidAttributes(pyr);
			pyr.build();
			faces.addAll(pyr.getFaces());
		}
		setFaces(faces);
	}

	private void setPyramidAttributes(Pyramid pyr) {
		pyr.setConfig(this.getConfig());
		pyr.getConfig().setPyramidAttributes(nHeight, nWidth);
	}

}
