package com.neuralvisualizer.utilities.resources.structures;

public class Matrix {
	//The numbers inside the matrix
    private double[][] data;

    public Matrix (double[][] dat){
        this.data=dat;
    }

    public double[][] getData() {
        return data;
    }

    //Multiplies matrixes (Obsolete, only used in tests)
    public Matrix multiply(Matrix m){
        int dimXt=this.getData().length;
        int dimYt=this.getData()[0].length;
        int dimYm=m.getData()[0].length;
        double[][] a=new double[dimXt][dimYm];
        Matrix toReturn = new Matrix(a);
        for(int i = 0; i < dimXt; i++) {
            for (int j = 0; j < dimYm; j++) {
                for (int k = 0; k < dimYt; k++) {
                    toReturn.getData()[i][j] += this.getData()[i][k] * m.getData()[k][j];
                }
            }
        }
        return toReturn;
    }
}
