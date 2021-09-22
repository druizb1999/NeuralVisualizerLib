package com.neuralvisualizer.utilities.resources.structures;




import com.configuration.ConfigurationMap;
import com.configuration.Randomizer;
import com.neuralvisualizer.utilities.resources.layers.Conv2D;
import com.neuralvisualizer.utilities.resources.layers.Dense;
import com.neuralvisualizer.utilities.resources.layers.Input;
import com.neuralvisualizer.utilities.resources.layers.Layers;
import com.neuralvisualizer.utilities.resources.objects.*;

import java.util.LinkedList;
import java.util.List;

//Represents a group of layers
public class Lane {
	//The group of Objects 3d that form the layers
    private List<Shape> objects3D;
    //The group of layers
    private List<Layers> nodes;
    //The length that the lane occupies in 3D space
    private double length;
    //The width that the lane occupies in 3D space
    private double width;
    //The connections between the nodes of the lane
    private List<ConnectionNode> connections;
    //The configuration map
    private ConfigurationMap config;

    public Lane() {
        objects3D = new LinkedList<>();
        nodes = new LinkedList<>();
        connections = new LinkedList<>();
        length = 0;
        width = 0;
    }

    //Builds the lane in the 3D space
    public void buildLane(double dist, double logMult, boolean log, boolean random) {
        Cube cube;
        Kernel kernel;
        LinkedList<Shape> shapeList;
        Cube prevNode=null;
    	shapeList = new LinkedList<>();
    	
        for (Layers node:this.nodes) {

            kernel = new Kernel(node.getKernelHeight(), node.getKernelWidth(), node.getDepth(), log, logMult);
            cube = new Cube(node.getHeight(), node.getWidth(), node.getDepth(), log, logMult,kernel);
            cube.setReversed(node.getReversed());
            
            Point p =cube.getCenterPoint();
            p.setZ(cube.getStart());
           
            buildJump(node,cube);
            setNodeColors(node,cube); 
            setKernelAttributes(kernel);
          
            compareWidth(cube.getWidth()/2);
            
            createPreviousConnection(prevNode, cube);
            setRandom(cube,kernel, random);

            objects3D.add(cube);
            prevNode=cube;
            shapeList.add(cube);
        }
        
        moveToPos(shapeList, dist);
        translate(0, 0, -(length/2));
    }
    
    //Builds a distant connection from or to this lane
    private void buildJump(Layers node, Cube cube){
        JumpPoint jumpPoint;
        Point pointAux;
    	   if (node.getJump()!=null){
           	pointAux = new Point(cube.getCenterTopFace().getX(), cube.getCenterTopFace().getY()+node.getJump().getSize(), cube.getCenterTopFace().getZ());
               jumpPoint = new JumpPoint(pointAux);

               if (node==node.getJump().getFromLayer()){
                   node.getJump().setFromShape(cube);
               }
               if (node==node.getJump().getToLayer()){
                   node.getJump().setToShape(cube);
               }
               cube.addShape(jumpPoint);
           }
    }

    //Sets the colors of a node from a system of priority 
    //1st the layer specific color
    //2nd the layer type color
    //3rd the global model color
    private void setNodeColors(Layers node, Cube cube) {
    	String nodeColor=null;
    	 if (node instanceof Dense){
         	cube.setDense(true);
             nodeColor=this.config.getDenseColor();
         }
         else if (node instanceof Input)
         	nodeColor=this.config.getInputColor();
         else if (node instanceof Conv2D)
         	nodeColor=this.config.getConvColor();
    	
	   	 if (node.getColor()!=null) {
			 nodeColor=node.getColor();
		 }
   	 
    	 setCubeAttributes(cube, nodeColor);
    }
    
    //Creates the connection from the previous node to this one
    private void createPreviousConnection(Cube prevNode, Cube cube) {
        ConnectionNode connection;
        if (prevNode!=null) {
            connection= new ConnectionNode(prevNode,cube);
            this.connections.add(connection);
        }
    }
    
    //Sets a random kernel position
    private void setRandom(Cube cube, Kernel kernel, boolean random){
    	 if (random) {
             Randomizer r = new Randomizer();
             cube.getUnderlyingShape().get(0).translate(r.randomBetween((cube.getWidth() / 2 - kernel.getWidth() / 2)-1), r.randomBetween((cube.getHeight() / 2 - kernel.getHeight() / 2)-1), 0);
         }
    }
    
    //Sets a config for a cube
    private void setCubeAttributes(Cube c, String color){
    	c.setConfig(new ConfigurationMap(config));
    	if (color!=null)
    		c.getConfig().setCubeFill(color);
    	
    	
    	
    }

    //Sets the Kernel config
    private void setKernelAttributes(Kernel k){
    	k.setConfig(config);
    }

    //Gets the higher 3D width
    private void compareWidth(double newW){
        if (newW>width){
           width=newW;
        }
    }

    //Translates the nodes in the lane to their position in 3D space
    private void moveToPos(List<Shape> l, double dist) {
            for (Shape c:l){
            	if (length==0) {
                	length += (c.getDepth()) + dist;
                	c.translate(0, 0, c.getDepth()/2);
            	}
            	else {
            		length += c.getDepth()/2;
            		c.translate(0, 0, length);
            		length += (c.getDepth()/2) + dist;
            	}
            }
            
            centerLane(l);
            
    }
    
    //Centers the lane
    private void centerLane(List<Shape> l) {
        double end=this.getStart();
        double start=this.getEnd();
        length=Math.abs(end-start);
        
        for (Shape c:l){
        c.translate(0, 0, -length/2);
        }
    }

    //Getters, setters and transformations
    public void rotateX(double theta) {
        for (Shape s : objects3D) {
            s.rotateX(theta);
        }
    }

    public void rotateY(double theta) {
        for (Shape s : objects3D) {
            s.rotateY(theta);
        }
    }

    public void rotateZ(double theta) {
        for (Shape s : objects3D) {
            s.rotateZ(theta);
        }
    }

    public void scale(double x, double y, double z) {
        for (Shape s : objects3D) {
            s.scale(x, y, z);
        }
    }

    public void translate(double x, double y, double z) {
        for (Shape s : objects3D) {
            s.translate(x, y, z);
        }
    }

	public void setConfig(ConfigurationMap configuration) {
		this.config=configuration;
		
	}
	
	public void getConnections(List<ConnectionNode> list){
	        list.addAll(this.connections);
	}
	
    public double getStart() {
        return this.objects3D.get(0).getPoints()[5].getZ();
    }
    
    public List<Shape> getObjects3D() {
        return objects3D;
    }

    public double getEnd(){
        return this.objects3D.get(this.objects3D.size()-1).getPoints()[0].getZ();
    }

    public double getLength() {
        return length;
    }
    
    public double getWidth() {
        return width;
    }

    public void add(Layers l){
        nodes.add(l);
    }
}
