package com.neuralvisualizer.utilities.resources.layers;

import com.neuralvisualizer.utilities.resources.structures.Jump;
//Class from wich every layer inherits
public abstract class Layers {
    int width;
    int depth;
    int height;
    int kernelWidth;
    int kernelHeight;
    Jump jump;
    String color;
    boolean reversed;

    public Jump getJump() {
        return jump;
    }

    public void setJump(Jump jump) {
        this.jump = jump;
    }
    

    public boolean getReversed() {
        return reversed;
    }

    public void setReversed(boolean reversed) {
        this.reversed = reversed;
    }
    
    public String getColor(){
        return this.color;
    }
    
    public void setColor(String c){
        this.color=c;
    }

    public int getWidth() {
    	return this.width;
    }

    public int getDepth() {
    	return this.depth;
    }

    public int getHeight() {
    	return this.height;
    }

    public int getKernelWidth() {
    	return this.kernelWidth;
    }

    public int getKernelHeight() {
    	return this.kernelHeight;
    }
}
