package com.neuralvisualizer.utilities.resources.structures;


import com.neuralvisualizer.utilities.resources.layers.Layers;
import com.neuralvisualizer.utilities.resources.objects.Shape;

//represents a distant connection between layers
public class Jump {
	//Origin layer
	private Layers fromLayer;
	//Destination layer
	private Layers toLayer;
	//Height of the arrow in the 3D space
	private int size;
	//Origin 3D object
	private Shape fromShape;
	//Destination 3D object
    private Shape toShape;

    public Jump(Layers fromLayer, Layers toLayer, int size) {
        this.fromLayer = fromLayer;
        this.toLayer = toLayer;
        this.size=-size;
    }

    public Layers getFromLayer() {
        return fromLayer;
    }

    public Layers getToLayer() {
        return toLayer;
    }

    public int getSize() {
        return size;
    }  
    
    public Shape getFromShape() {
		return fromShape;
	}

	public void setFromShape(Shape fromShape) {
		this.fromShape = fromShape;
	}

	public Shape getToShape() {
		return toShape;
	}

	public void setToShape(Shape toShape) {
		this.toShape = toShape;
	}

}
