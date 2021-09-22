package com.neuralvisualizer.utilities.modeler;

import com.configuration.CompareFace;
import com.configuration.ConfigurationMap;
import com.exceptions.LayerNotFoundException;
import com.exceptions.SplitErrorException;
import com.neuralvisualizer.utilities.resources.objects.*;
import com.neuralvisualizer.utilities.resources.structures.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Renderer {
	
	//The list containing all the individual faces of the 3D objects
    private List<Face> faces;
    //The list containing all the individual connections between the 3D objects
    private List<ConnectionNode> connectionsToPrint;
    //Parameter that specifies the global depth of the model
    private double mDepth;
    //The model to render
    private Model model;
    
    public Renderer(Model model) {
        faces=new LinkedList<>();
        connectionsToPrint= new LinkedList<>();
        mDepth=0.0;
        this.model=model;
    }
    
    //Builds the data structures of a model into a 3D figure
    public void build() {
    	double mDepthIni=0.0;
        for (List<Lane> list: model.getGraph().getGrid()){
            double l=buildGrid(list);
            moveGridToPosDepth(list,l,model.getConfiguration().getDistanceBetweenLayers(), mDepthIni);
        }
        
        mDepth=Math.abs(model.getGraph().getGrid().get(0).get(0).getStart()
        		-model.getGraph()
        		.getGrid()
        		.get(model.getGraph().getGrid().size()-1)
        		.get(model.getGraph().getGrid().get(model.getGraph().getGrid().size()-1).size()-1)
        		.getEnd());
        translate(0,0,-mDepth/2);
    }
    
    //Builds the 3d objects that form the connections
    private void buildAllConnections() throws LayerNotFoundException, SplitErrorException {
        getGraphConnections();


        for (Jump j: model.getJumps()){
        	renderJumps(j);
        }
	    for(ConnectionNode node:connectionsToPrint){
		    	buildNode(node);
	        }
    }
    
    //Builds specifically the 3D objects that form the jumps or distant connections
    private void renderJumps(Jump j) throws LayerNotFoundException {
        Point fromPoint;
        Point toPoint;
        Point fromBase;
        Point toBase;
        if (j.getFromLayer().getJump().getFromShape()==null || j.getToLayer().getJump().getToShape()==null) {
        	throw new LayerNotFoundException("Layer not found, add the layer to the model");
        }
        if (((!j.getFromLayer().getJump().getFromShape().getUnderlyingShape().isEmpty())
        		&&(!j.getToLayer().getJump().getToShape().getUnderlyingShape().isEmpty())) 
        		&& ((j.getFromLayer().getJump().getFromShape().getUnderlyingShape().get(j.getFromLayer().getJump().getFromShape().getUnderlyingShape().size()-1) instanceof JumpPoint)
        		&&(j.getToLayer().getJump().getToShape().getUnderlyingShape().get(j.getToLayer().getJump().getToShape().getUnderlyingShape().size()-1) instanceof JumpPoint))) {
                fromPoint = ((JumpPoint) j.getFromLayer().getJump().getFromShape().getUnderlyingShape().get(j.getFromLayer().getJump().getFromShape().getUnderlyingShape().size()-1)).getOnlyPoint();
                toPoint = ((JumpPoint)  j.getToLayer().getJump().getToShape().getUnderlyingShape().get(j.getToLayer().getJump().getToShape().getUnderlyingShape().size()-1)).getOnlyPoint();
                if (j.getFromLayer().getJump().getFromShape() instanceof Cube){
                    fromBase = ((Cube) j.getFromLayer().getJump().getFromShape()).getCenterTopFace();
                    toBase = ((Cube) j.getToLayer().getJump().getToShape()).getCenterTopFace();
                    this.faces.addAll(new Arrow(fromBase, fromPoint).getFaces());
                    this.faces.addAll(new Arrow(toBase, toPoint).getFaces());
                }
                this.faces.addAll(new Arrow(fromPoint, toPoint).getFaces());
            }
    	}
    
    //Builds the non-distant connections
    private void buildNode (ConnectionNode node) {
    	if (!node.getConnection().isReversed()) {
           buildNormalNode(node);
    	}
    	else {
    		buildReversedNode(node);
    	}
    }
    
    //Builds the non reversed kernel and cube individually
    private void buildNormalNode(ConnectionNode node) {
    	 double x=node.getConnection().getBackSquareCenter().getX();
    	 double y=node.getConnection().getBackSquareCenter().getY();
    	 double z=node.getConnection().getBackSquareCenter().getZ();

          if (!node.getData().getUnderlyingShape().isEmpty())
          {
          	Kernel k= (Kernel) node.getData().getUnderlyingShape().get(0);
          	k.setConnection(x,y,z);
          }
          if (node.getConnection().isDense()){
          	if(!node.getData().isReversed()) {
          		node.getData().removeKernel();
          		this.faces.addAll(new Arrow(node.getConnection().getBackSquareCenter(), node.getData().getFrontSquareCenter()).getFaces());
          	}
          	else {
          		node.getData().removeFrontConnection();
          		this.faces.addAll(new Arrow(node.getConnection().getBackSquareCenter(), node.getData().getFrontSquareCenter()).getFaces());
          	}
          }
    }
    
    //Builds the reversed kernel and cube individually
    private void buildReversedNode(ConnectionNode node) {
    	 double x=node.getData().getFrontSquareCenter().getX();
    	 double y=node.getData().getFrontSquareCenter().getY();
    	 double z=node.getData().getFrontSquareCenter().getZ();
         if (!node.getConnection().getUnderlyingShape().isEmpty())
         {
         	Kernel k= (Kernel) node.getConnection().getUnderlyingShape().get(0);
         	k.setReversedConnection(x,y,z);
         }
         if (node.getConnection().isDense()){
         	node.getConnection().removeFrontConnection();
         	this.faces.addAll(new Arrow(node.getConnection().getBackSquareCenter(), node.getData().getFrontSquareCenter()).getFaces());
         }
         if (node.getData().isDense() && !node.getConnection().isReversed()) {
         		node.getConnection().removeKernel();
         		this.faces.addAll(new Arrow(node.getData().getBackSquareCenter(), node.getConnection().getFrontSquareCenter()).getFaces());
         	}
         }
    
    //Gets all the non-distant connections in order to build them later
    private void getGraphConnections() throws SplitErrorException{
        for (Connection dest: model.getGraph().getConnections()) {
            for (Lane orig : dest.getLaneList()) {
            	try {
                ConnectionNode k= new ConnectionNode((Cube) orig.getObjects3D().get(orig.getObjects3D().size()-1),
                        (Cube)dest.getLane().getObjects3D().get(0));
                this.connectionsToPrint.add(k);
            	}
            	catch(Exception ex) {
            		 throw new SplitErrorException("You cannot split a dense layer");
            	}
            }
        }
    }

    //Gets the grid (list of lanes) that occupies more 3D space in length
    private double higherGridLength(double l1,double l2){
        if (l1>l2){
            return l1;
        }
        else{
            return l2;
        }
    }

    //Builds the objects 3D of a list of lanes
    private double buildGrid(List<Lane> list){
        double gridWidth=list.get(0).getWidth()/2;
        double gridLength=0.0;
        for (Lane node: list){
            setLaneAttributes(node);
            node.buildLane(model.getConfiguration().getDistanceBetweenLevels(),
            		model.getConfiguration().getLogarithmicMultiplicator(),
            		model.getConfiguration().getLogarithmicDistances(),
            		model.getConfiguration().getRandomKernelPos());
            gridWidth=moveLaneToPosWidth(node,
            		model.getConfiguration().getDistanceBetweenSiameses(),
            		gridWidth);
            gridLength=higherGridLength(gridLength,node.getLength());
            node.getConnections(this.connectionsToPrint);
        }
        centerGrid(list, gridWidth);
        return gridLength;
    }

    //Sets the config insterted on a lane per lane basis
    private void setLaneAttributes(Lane node){
        node.setConfig(model.getConfiguration());
    }

    //Sends the list of lanes to the center width-wise
    private void centerGrid(List<Lane> list, double width){
        double maxWidth=width;
        for (Lane node: list){
           node.translate(-((maxWidth-list.get(0).getWidth()/2)/2),0,0);
        }
    }

    //Positions the lists of lanes, once build in the 3D space depth wise
    private void moveGridToPosDepth(List<Lane> list, double length, double dist, double mDepthIni) {
    	if (this.mDepth==0) {
    		 this.mDepth += length/2 +dist;
    		 mDepthIni += length/2;
    	}
    	else {
			 this.mDepth += length/2;
			 translateGrid(list,0, 0, (this.mDepth + length/2));
			 this.mDepth += length/2 +dist;
    	}
    	translateGrid(list,0, 0, mDepthIni);
    }

    //Translates a full list of lanes
    private void translateGrid(List<Lane> list,double x,double y,double z){
        for (Lane node:list){
            node.translate(x,y,z);
        }
    }

   //Positions the lists of lanes, once build in the 3D space width wise
    private double moveLaneToPosWidth(Lane l, double dist, double width) {
        double z=0;
        if (width==0) {
        	 width += l.getWidth()/2 + dist;
        	 l.translate(0, 0, z);
        }
        else {
        	 width += l.getWidth()/2;
        	 l.translate(width, 0, z);
        	 width += l.getWidth()/2 + dist;
        }
        return width;
    }
 
    //Gets the faces of all 3D objects and saves them on the faces list, orders the faces list by the Z axis
    private void getAllObjects(){
        for (List<Lane> list: model.getGraph().getGrid()){
            for (Lane node:list){
                for (Shape s: node.getObjects3D()){
                    s.build();
                    this.faces.addAll(s.getFaces());
                }          
            }
        }
        Collections.sort(this.faces,new CompareFace());
    }
    
    //gets the model string without the svg header
   private String print() throws LayerNotFoundException, SplitErrorException {
        String aux = "";
	    buildAllConnections();
        getAllObjects();

        for(Face f:this.faces){
            aux+=f.getToPrint();
        }
        return aux;
    }
    
   //getters, setters and rotation, scaling and traslation operations
    public List<Face> getFaces() {
	return faces;
    }
	
	public void setConfig(ConfigurationMap config) {
		model.setConfiguration(config);
	}

	public void rotateX(double theta) {
		this.model.getGraph().rotateX(theta);
    }

    public void rotateY(double theta) {
    	this.model.getGraph().rotateY(theta);
    }

    public void rotateZ(double theta) {
       this.model.getGraph().rotateZ(theta);
    }

    public void scale(double x, double y, double z) {
       this.model.getGraph().scale(x, y, z);
    }

    public void translate(double x, double y, double z) {
       this.model.getGraph().translate(x, y, z);
    }

    //prints the whole model in a svg format
    public String printModel() throws LayerNotFoundException, SplitErrorException {
        String toPrint = "<svg alt=\""+ model.getAltText()+"\" width=\"" 
        		+ model.getConfiguration().getWidth() 
        		+ "px\" height=\"" 
        		+ model.getConfiguration().getHeight()
        		+ "px\" viewBox=\""
                + model.getConfiguration().getViewWidthini()
                + " " 
                + model.getConfiguration().getViewHeightini()
                + " " 
                + model.getConfiguration().getViewWidth()
                + " " 
                + model.getConfiguration().getViewHeight()
                +"\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\">\n";
        return toPrint + print() + "</svg>";
    }

    //Sends the model to a specified file
    public void toFile(String path) throws FileNotFoundException, LayerNotFoundException, SplitErrorException {
    	try (PrintStream out = new PrintStream(new FileOutputStream(path))) {
    	    out.print(this.printModel());
    	}
    }

}
