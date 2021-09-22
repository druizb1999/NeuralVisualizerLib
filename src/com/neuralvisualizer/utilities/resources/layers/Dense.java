package com.neuralvisualizer.utilities.resources.layers;

public class Dense extends Layers {

	//Dense layer parameters
    public Dense(int size){
        this.width=5;
        this.depth=5;
        this.height=size;
        this.kernelWidth=0;
        this.kernelHeight=0;
        this.reversed=false;
    }

}