package com.configuration;

import com.neuralvisualizer.utilities.resources.structures.Face;

import java.util.Comparator;


//Compares the Z axis value on the faces to order them later by Z index.
public class CompareFace implements Comparator<Face> {
    @Override
    public int compare(Face face, Face t1) {
    	return Double.compare(face.getzCoord(), t1.getzCoord());
    }
}
