package com.neuralvisualizer.utilities.resources.layers;

public class Input extends Layers {

	//Input layer parameters
    public Input(int width, int height, int depth, int kernelWidth, int kernelHeight){
        this.width=width;
        this.height=height;
        this.depth=depth;
        this.kernelWidth=kernelWidth;
        this.kernelHeight=kernelHeight;
        this.reversed=false;
    }

}

