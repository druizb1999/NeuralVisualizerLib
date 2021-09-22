package com.neuralvisualizer.utilities.resources.structures;

//represents each of the faces of a 3D object
public class Face {
	//Z index of the face
    double zCoord;
    //String that represents the face in svg
    String toPrint;
    

    public Face(double zCoord, String toPrint) {
        this.zCoord = zCoord;
        this.toPrint = toPrint;
    }

	public double getzCoord() {
        return zCoord;
    }

    public String getToPrint() {
        return toPrint;
    }
}
