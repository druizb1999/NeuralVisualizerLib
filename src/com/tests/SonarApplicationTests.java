package com.tests;

import java.io.FileNotFoundException;
import java.util.LinkedList;

import com.configuration.ConfigurationMap;
import com.exceptions.LayerNotFoundException;
import com.exceptions.SplitErrorException;
import com.neuralvisualizer.utilities.modeler.Model;
import com.neuralvisualizer.utilities.modeler.Renderer;
import com.neuralvisualizer.utilities.resources.layers.Conv2D;
import com.neuralvisualizer.utilities.resources.layers.Dense;
import com.neuralvisualizer.utilities.resources.layers.Input;
import com.neuralvisualizer.utilities.resources.layers.Layers;
import org.junit.Assert;
import org.junit.Test;


public class SonarApplicationTests {

    
    @Test
    public void simpleSplitAndJoinTest() throws FileNotFoundException, LayerNotFoundException, SplitErrorException {
    	
        Model model= new Model();
        model.add(new Input(224,224,3,11,11));
        
        Model model2= new Model();
        model2.add(new Conv2D(55,55,96,5,5));
        model2.add(new Conv2D(27,27,256,3,3));
        model2.add(new Conv2D(13,13,384,3,3));
        
        Model model3= new Model();
        model3.add(new Conv2D(55,55,96,5,5));
        model3.add(new Conv2D(13,13,384,3,3));
        
        LinkedList<Model> modelList=new LinkedList<Model>();
        modelList.add(model2);
        modelList.add(model3);
        model.split(modelList);
        
        model.join(new Conv2D(13,13,256,3,3));
        
        model.add(new Dense(2048));
        model.add(new Dense(1000));
        
        ConfigurationMap config = new ConfigurationMap();
        config.setShowNumbers(false);
        config.setDistanceBetweenLayers(150);
        config.setDistanceBetweenLevels(300);
        config.setDistanceBetweenSiameses(500);
        config.setRandomKernelPos(false);
        config.setLogarithmicDistances(false);
        
        model.setConfiguration(config);
        
        Renderer renderer= new Renderer(model);
        renderer.build();
        renderer.scale(0.3, 0.3, 0.3);
        renderer.rotateX(-0.4);
        renderer.rotateY(0.6);
        renderer.rotateZ(0.0);
        renderer.translate(200, 200, 0);

        renderer.printModel();
        renderer.toFile("C:\\Users\\TheBrightKing\\Desktop\\fileSplitAndJoin.svg");
        Assert.assertTrue(true);
    }
    
    
    @Test
    public void simpleLineTest() throws FileNotFoundException, LayerNotFoundException, SplitErrorException {
    	
        Model model= new Model();
        model.add(new Input(224,224,3,11,11)); 
        model.add(new Conv2D(55,55,96,5,5));
        model.add(new Conv2D(27,27,256,3,3));
        model.add(new Conv2D(13,13,384,3,3));
        model.add(new Conv2D(13,13,384,3,3));
        model.add(new Conv2D(13,13,256,3,3));
        model.add(new Dense(2048));
        model.add(new Dense(2048));
        model.add(new Dense(1000));
        
        ConfigurationMap config = new ConfigurationMap();
        config.setShowNumbers(true);
        config.setCubeLineWidth(1);
        config.setKernelLineWidth(0.5);
        config.setPyramidStrokeWidth(0.3);
        config.setNumberSize(12);
        config.setNumberFill("Red");
        config.setCubeFill("LawnGreen");
        config.setKernelFill("Aqua");
        config.setPyramidFill("Aquamarine");
        config.setInputColor("Yellow");
        config.setDenseColor("Orange");
        config.setCubeAlpha(0.3);
        config.setKernelAlpha(0.7);
        config.setPyramidAlpha(0.4);
        config.setViewHeight(1500);
        config.setViewWidth(1500);
        
        model.setConfiguration(config);
        Renderer renderer= new Renderer(model);
        renderer.build();
        renderer.scale(1.5, 1.5, 1.5);
        renderer.rotateX(-0.2);
        renderer.rotateY(0.6);
        renderer.rotateZ(0.0);
        renderer.translate(1200, 750, 0);

        renderer.printModel();
        renderer.toFile("C:\\Users\\TheBrightKing\\Desktop\\fileSimpleLine.svg");
        Assert.assertTrue(true);
    }

 
    
    
    @Test
    public void simpleEncoderDecoderTest() throws FileNotFoundException, LayerNotFoundException, SplitErrorException {
    	
        Model model= new Model();
        model.add(new Input(224,224,3,11,11)); 
        model.add(new Conv2D(55,55,96,5,5));
        model.add(new Conv2D(13,13,384,3,3));
        model.add(new Conv2D(13,13,256,3,3));
       
        Layers l1=new Conv2D(13,13,384,3,3);
        Layers l2=new Conv2D(55,55,96,5,5);
        Layers l3=new Input(224,224,3,11,11);


        model.add(l1);
        model.add(l2);
        model.add(l3);
        model.reverse(l1);
        model.reverse(l2);
        model.reverse(l3);
    
        Renderer renderer= new Renderer(model);
        renderer.build();
        renderer.scale(1.5, 1.5, 1.5);
        renderer.rotateX(-0.3);
        renderer.rotateY(0.5);
        renderer.rotateZ(0.0);
        renderer.translate(800, 750, 0);

        renderer.printModel();
        renderer.toFile("C:\\Users\\TheBrightKing\\Desktop\\fileEncoderDecoder.svg");
        Assert.assertTrue(true);
    }
    
    
    @Test
    public void simpleJumpTest() throws FileNotFoundException, LayerNotFoundException, SplitErrorException {
    	
    	
    	 Layers l1=new Conv2D(55,55,96,5,5);
    	 Layers l2=new Conv2D(13,13,256,3,3);

    	 Model model= new Model();

    	 model.add(new Input(224,224,3,11,11)); 
         model.add(l1);
         model.add(new Conv2D(27,27,256,3,3));
         model.add(new Conv2D(13,13,384,3,3));
         model.add(new Conv2D(13,13,384,3,3));
         model.add(l2);
         model.add(new Dense(2048));
         model.add(new Dense(2048));
         model.add(new Dense(1000));
    	 model.createJump(l1, l2, 50);
         
         ConfigurationMap config = new ConfigurationMap();
         config.setCubeFill("#EEEEEE");
         config.setKernelFill("#99DDFF");
         config.setPyramidFill("#FFBBBB");
         config.setCubeLineWidth(0.3);
         config.setPyramidStrokeWidth(0.3);
         config.setKernelLineWidth(0.3);
         config.setCompactNumbers(true);
         config.setShowNumbers(true);
         config.setNumberFill("#003300");
         config.setDistanceBetweenSiameses(150);
         config.setDistanceBetweenLevels(50);
         config.setDistanceBetweenLayers(10);
         model.setConfiguration(config);
         
         Renderer renderer= new Renderer(model);
         renderer.build();
         renderer.scale(1.5, 1.5, 1.5);
         renderer.rotateX(-0.3);
         renderer.rotateY(0.6);
         renderer.rotateZ(0.0);
         renderer.translate(1200, 1000, 0);

         renderer.printModel();
         renderer.toFile("C:\\Users\\TheBrightKing\\Desktop\\fileJumps.svg");
         Assert.assertTrue(true);
    }
    
    @Test
    public void complexTest() throws FileNotFoundException, LayerNotFoundException, SplitErrorException {

        
        Layers layerAux= new Conv2D(55,55,96,5,5);
        Layers layer2= new Conv2D(13,13,384,3,3);
        Layers layer3= new Conv2D(13,13,384,3,3);
        layer2.setReversed(true);
        layer3.setReversed(true);
        
        
        Model model2 = new Model();
        model2.add(new Conv2D(27,27,256,3,3));
        model2.add(layer2);
        
        Model model3 = new Model();
        model3.add(new Conv2D(27,27,256,3,3));
        model3.add(layer3);
        
        LinkedList<Model> lista1 = new LinkedList<Model>();
        lista1.add(model3);
        lista1.add(model2);


        
        Model model= new Model();
        model.add(new Input(224,224,3,11,11)); 
        model.add(layerAux);
        
        model.split(lista1);
        model.join(new Conv2D(13,13,384,3,3));

        model.add(new Dense(2000));
        model.add(new Dense(1000));
        model.createJump(layerAux, layer2, 50);
        
        ConfigurationMap config = new ConfigurationMap();
        model.setConfiguration(config);
        
        Renderer renderer= new Renderer(model);
        renderer.build();
        renderer.scale(1.5, 1.5, 1.5);
        renderer.rotateX(-0.4);
        renderer.rotateY(0.7);
        renderer.rotateZ(0.0);
        renderer.translate(700, 600, 0);

        
        renderer.printModel();
        renderer.toFile("C:\\Users\\TheBrightKing\\Desktop\\file.svg");
        Assert.assertTrue(true);
    }
    
    @Test
    public void denseTest() throws FileNotFoundException, LayerNotFoundException, SplitErrorException {

        
        Layers layer2= new Conv2D(13,13,384,3,3);
        Layers layer3= new Conv2D(13,13,384,3,3);
        layer2.setReversed(true);
        layer3.setReversed(true);
        Model [] firstDepth = new Model[48];
        Model [] secondDepth = new Model[24];
        Model [] thirdDepth = new Model[12];
        Model [] fourthDepth = new Model[6];
        Model [] fifthDepth = new Model[3];
        
        int x=0;
        int y=0;
        int z=0;
       while (x<48) {
    	   firstDepth[x]=new Model();
    	   firstDepth[x].add(new Input(224,224,3,11,11));
    	   firstDepth[x].add(new Conv2D(13,13,384,3,3));
    	   
    	   firstDepth[x+1]=new Model();
    	   firstDepth[x+1].add(new Input(224,224,3,11,11));
    	   firstDepth[x+1].add(new Conv2D(13,13,384,3,3));
    	   
    	   LinkedList<Model> lista1 = new LinkedList<Model>();
    	   lista1.add(firstDepth[x+1]);
    	   
    	   firstDepth[x].join(lista1, new Conv2D(13,13,384,3,3));
    	   secondDepth[y]=firstDepth[x];
    	   secondDepth[y].add(new Conv2D(13,13,384,3,3));
    	   
    	   firstDepth[x+2]=new Model();
    	   firstDepth[x+2].add(new Input(224,224,3,11,11));
    	   firstDepth[x+2].add(new Conv2D(13,13,384,3,3));
    	   
    	   firstDepth[x+3]=new Model();
    	   firstDepth[x+3].add(new Input(224,224,3,11,11));
    	   firstDepth[x+3].add(new Conv2D(13,13,384,3,3));
    	   
    	   lista1.clear();
    	   lista1.add(firstDepth[x+3]);
    	   
    	   firstDepth[x+2].join(lista1, new Conv2D(13,13,384,3,3));
    	   secondDepth[y+1]=firstDepth[x+2];
    	   secondDepth[y+1].add(new Conv2D(13,13,384,3,3));
    	   
    	   lista1.clear();
    	   lista1.add(secondDepth[y+1]);
    	   
    	   secondDepth[y].join(lista1, new Conv2D(13,13,384,3,3));
    	   thirdDepth[z]=secondDepth[y];
    	   
    	   z=z+1;
    	   y=y+2;
    	   x=x+4;
       }
       
       x=0;
       y=0;
       z=0;
       while (x<12) {
    	   LinkedList<Model> lista1 = new LinkedList<Model>();
    	   lista1.add(thirdDepth[x+1]);
    	   
    	   thirdDepth[x].join(lista1, new Conv2D(13,13,384,3,3));
    	   fourthDepth[y]=thirdDepth[x];
    	   fourthDepth[y].add(new Conv2D(13,13,384,3,3));
    	
    	   
    	   lista1.clear();
    	   lista1.add(thirdDepth[x+3]);
    	   
    	   thirdDepth[x+2].join(lista1, new Conv2D(13,13,384,3,3));
    	   fourthDepth[y+1]=thirdDepth[x+2];
    	   fourthDepth[y+1].add(new Conv2D(13,13,384,3,3));
    	   
    	   lista1.clear();
    	   lista1.add(fourthDepth[y+1]);
    	   
    	   fourthDepth[y].join(lista1, new Conv2D(13,13,384,3,3));
    	   fifthDepth[z]=fourthDepth[y];
    	   
    	   z=z+1;
    	   y=y+2;
    	   x=x+4;
       }
        
       LinkedList<Model> lista2 = new LinkedList<Model>();
       lista2.clear();
	   lista2.add(fifthDepth[1]);
	   lista2.add(fifthDepth[2]);
	   fifthDepth[0].join(lista2, new Conv2D(13,13,384,3,3));
	   fifthDepth[0].add(new Conv2D(13,13,384,3,3));
	   

	   fifthDepth[0].add(new Dense(2000));
	   fifthDepth[0].add(new Dense(1000));
        
        ConfigurationMap config = new ConfigurationMap();
        config.setCubeLineWidth(0.1);
        config.setShowNumbers(false);
        config.setKernelLineWidth(0.05);
        config.setPyramidStrokeWidth(0.07);
        config.setViewHeight(1500);
        config.setViewWidth(1500);
        config.setDistanceBetweenSiameses(40);
        fifthDepth[0].setConfiguration(config);
        
        Renderer renderer= new Renderer(fifthDepth[0]);
        renderer.build();
        renderer.scale(1.5, 1.5, 1.5);
        renderer.rotateX(-0.4);
        renderer.rotateY(0.7);
        renderer.rotateZ(0.0);
        renderer.translate(700, 600, 0);

        
        renderer.printModel();
        renderer.toFile("C:\\Users\\TheBrightKing\\Desktop\\fileDenseNet.svg");
        Assert.assertTrue(true);
    }
    
    @Test
    public void mediumTest() throws FileNotFoundException, LayerNotFoundException, SplitErrorException {
        Model [] firstDepth = new Model[48];
        
        int x=1;
        LinkedList<Model> lista1 = new LinkedList<Model>();
       while (x<48) {
    	   firstDepth[x]=new Model();
    	   firstDepth[x].add(new Input(224,224,3,11,11));
    	   firstDepth[x].add(new Conv2D(13,13,384,3,3));
    	   
    	   lista1.add(firstDepth[x]);
    	   x=x+1;
       }
       
       firstDepth[0]=new Model();
       firstDepth[0].add(new Input(224,224,3,11,11));
	   firstDepth[0].add(new Conv2D(13,13,384,3,3));
	   
	   firstDepth[0].join(lista1, new Conv2D(13,13,384,3,3));
	   firstDepth[0].add(new Conv2D(13,13,384,3,3));
	   

	   firstDepth[0].add(new Dense(2000));
	   firstDepth[0].add(new Dense(1000));
        
       ConfigurationMap config = new ConfigurationMap();
       config.setCubeLineWidth(0.1);
       config.setShowNumbers(true);
       config.setKernelLineWidth(0.05);
       config.setPyramidStrokeWidth(0.07);
       config.setViewHeight(1500);
       config.setViewWidth(1500);
       config.setDistanceBetweenSiameses(40);
       firstDepth[0].setConfiguration(config);
        
        Renderer renderer= new Renderer(firstDepth[0]);
        renderer.build();
        renderer.scale(1.5, 1.5, 1.5);
        renderer.rotateX(-0.4);
        renderer.rotateY(0.7);
        renderer.rotateZ(0.0);
        renderer.translate(700, 600, 0);

        
        renderer.printModel();
        renderer.toFile("C:\\Users\\TheBrightKing\\Desktop\\fileMediumNet.svg");
        Assert.assertTrue(true);
    }


}
