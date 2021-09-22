package com.neuralvisualizer.utilities.modeler;


import com.configuration.ConfigurationMap;
import com.neuralvisualizer.utilities.resources.layers.Layers;
import com.neuralvisualizer.utilities.resources.structures.CustomGraph;
import com.neuralvisualizer.utilities.resources.structures.Jump;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;

//Class that contains the structure of a convnet
public class Model {

	//The graph that contains the structure
	private CustomGraph graph;
	//Alternative text that can be set for display on a website
	private String altText;
	//Configuration of the model
    private ConfigurationMap config;
    //Distant connections on the model
    private List<Jump> jumps;
    
    public Model() {
    	jumps = new LinkedList<>();
        graph = new CustomGraph();
        setDefaultParams();
        config=new ConfigurationMap();
    }

    //Getters and setters
    private void setDefaultParams(){
        altText="";
    }
    
    public void setAltText(String altText) {
        this.altText = altText;
    }
    
    
    public String getAltText() {
        return altText;
    }
    
    public List<Jump> getJumps() {
        return this.jumps;
    }

    public ConfigurationMap getConfiguration() {
		return config;
	}

	public void setConfiguration(ConfigurationMap config) {
		this.config = config;
	}
	

    public CustomGraph getGraph() {
        return graph;
    }
    
    
	//Creates a new distant connection between l1 an l2, the size inictes the height
	//of the line connecting the layers
    public void createJump(Layers l1, Layers l2, int size){
        Jump jump = new Jump(l1,l2, size);
        jumps.add(new Jump(l1,l2, size));
        l1.setJump(jump);
        l2.setJump(jump);
    }

    //adds a layer to the model
    public void add(Layers l) {
        graph.addInLine(l);
    }
    
    //reverts the direction of a kernel
    public void reverse(Layers l) {
        l.setReversed(true);
    }

    //Joins the current model and the list of models provided into the layer l
    public void join(List<Model> m2, Layers l) {
        this.graph.joinLinear(m2, l);
    }

    //Joins the loose ends of the current model into the layer l
    public void join(Layers l) {
        this.graph.joinLinear(l);
    }

    //Splits the current model into the models contained in m2
    public void split(List<Model> m2) {
        this.graph.splitLinear(m2);
    }

   
}


