package com.neuralvisualizer.utilities.resources.layers;

public class Conv2D extends Layers {

	//Conv 2D layer parameters
    public Conv2D(int width, int height, int depth, int kernelWidth, int kernelHeight){
        this.width=width;
        this.height=height;
        this.depth=depth;
        this.kernelWidth=kernelWidth;
        this.kernelHeight=kernelHeight;
        this.reversed=false;
    }

}


