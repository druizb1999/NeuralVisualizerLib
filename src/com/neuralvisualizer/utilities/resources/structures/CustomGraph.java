package com.neuralvisualizer.utilities.resources.structures;

import com.neuralvisualizer.utilities.modeler.Model;
import com.neuralvisualizer.utilities.resources.layers.Layers;

import java.util.*;

import java.util.LinkedList;


//The custom graph used for the model structure
public class CustomGraph {



	//List that contains all the lists of lanes
    List<List<Lane>> grid;
    //List that contains all the connections
    List<Connection> connections;

    //Gettes and constructor
    public List<List<Lane>> getGrid(){
        return this.grid;
    }
    
    
    public List<Connection> getConnections() {
        return connections;
    }


    public CustomGraph (){
        grid=new LinkedList<>();
        connections = new LinkedList<>();
    }

    //Adds a layer to a lane
    public void addInLine (Layers elem){
        if (grid.isEmpty()) {
            Lane l= new Lane();
            l.add(elem);
            List<Lane> ln = new LinkedList<>();
            ln.add(l);
            grid.add(ln);
        }
        else if (!grid.isEmpty()){
        	Lane n= grid.get(grid.size()-1).get(grid.get(grid.size()-1).size()-1);
            n.add(elem);
        }
    }

    //Joins the current graph and the graphs of the models on g2 into the layer l
    public void joinLinear(List<Model> g2, Layers l){
        List<List<Lane>> mainGrid=this.getGrid();
        HashSet<Lane> allConnections= new HashSet<>();
        for (Model model:g2){
        	joinModels(model,mainGrid,allConnections);
        }

        Lane lane= new Lane();
        lane.add(l);
        LinkedList<Lane> newN = new LinkedList<>();
        mainGrid.add(newN);
        newN.add(lane);

        LinkedList<Lane> connected = new LinkedList<>();
        connected.addAll(allConnections);
        Connection link = new Connection(lane, connected);
        this.connections.add(link);
        this.grid=mainGrid;
    }
    
    //Auxiliary method for main grid selection (main grid being the longest one in terms of nodes)
    //The rest of grids will join this main grid
    private void joinModels(Model model, List<List<Lane>> mainGrid,  HashSet<Lane> allConnections){
    	List<List<Lane>> gridToUse;
    	 if (model.getGraph().getGrid().size()>mainGrid.size()){
    		 gridToUse=mainGrid;
             mainGrid=model.getGraph().getGrid();
         }
         else {
        	 gridToUse=model.getGraph().getGrid();
         }
         List<Lane> mainLastLane=mainGrid.get(mainGrid.size()-1);
         List<Lane> secondaryLastLane=gridToUse.get(mainGrid.size()-1);
         joinListsReverse(mainGrid,gridToUse);

         joinConnections(mainLastLane, secondaryLastLane, allConnections);

         this.connections.addAll(model.getGraph().getConnections());
    }

    //Joins the current graph's loose ends into the layer l
    public void joinLinear(Layers l){

        HashSet<Lane> allConnections= new HashSet<>();
        List<Lane> mainLastLane=this.getGrid().get(this.getGrid().size()-1);

        for (Lane mainLast: mainLastLane) {
            if (!allConnections.contains(mainLast)) {
                allConnections.add(mainLast);
            }
        }

        Lane lane= new Lane();
        lane.add(l);
        LinkedList<Lane> newN = new LinkedList<>();
        this.getGrid().add(newN);
        newN.add(lane);

        LinkedList<Lane> connected = new LinkedList<>();
        connected.addAll(allConnections);
        Connection link = new Connection(lane, connected);
        this.connections.add(link);
    }

    //Splits the current graph into the graphs of the models on g2
    public void splitLinear(List<Model> g2){
        List<List<Lane>> mainGrid=g2.get(0).getGraph().getGrid();
        Set<Lane> allConnections= new HashSet<>();
        for (Model model:g2){
        	splitModel(model,mainGrid,allConnections);
        }

        Lane con = this.getGrid().get(this.getGrid().size()-1)
        		.get(this.getGrid().get(this.getGrid().size()-1).size()-1);
        this.getGrid().addAll(mainGrid);

        LinkedList<Lane> connected = new LinkedList<>();

        connected.add(con);
        for (Lane n:allConnections){
            Connection link = new Connection(n, connected);
            this.connections.add(link);
        }
    }
    
    //Auxiliary method for main grid selection (main grid being the longest one in terms of nodes)
   //The rest of grids will join this main grid
    private void splitModel(Model model, List<List<Lane>> mainGrid,  Set<Lane> allConnections) {
    	List<List<Lane>> gridToUse;
    	if (model.getGraph().getGrid().size()>mainGrid.size()){
    		gridToUse=mainGrid;
            mainGrid=model.getGraph().getGrid();
        }
        else {
        	gridToUse=model.getGraph().getGrid();
        }

        List<Lane> mainFirstLane=null;
        List<Lane> secondaryFirstLane=null;
    	mainFirstLane=mainGrid.get(0);
    	secondaryFirstLane=gridToUse.get(0);

    	joinConnections(mainFirstLane, secondaryFirstLane, allConnections);
    
    
    	if (model.getGraph().getGrid()!= mainGrid){
        	joinLists(mainGrid,gridToUse);
    	}


        this.connections.addAll(model.getGraph().getConnections());
    }
    
    //Transfers all the connections from one lane to another, in order to keep the
    //connections of a graph when joining or splitting
    private void joinConnections(List<Lane> firstLane, List<Lane> secondLane, Set<Lane> allConnections) {

        for (Lane mainLast: firstLane) {
            if (!allConnections.contains(mainLast)) {
                allConnections.add(mainLast);
            }
        }
        for (Lane secondaryLast: secondLane) {
            if (!allConnections.contains(secondaryLast)) {
                allConnections.add(secondaryLast);
            }
        }
    }

    //Joins the lists of two graphs for the join function
    private void joinListsReverse(List<List<Lane>> mainGrid, List<List<Lane>> secondaryGrid){
        ListIterator<List<Lane>> it = mainGrid.listIterator(mainGrid.size()-1);
        ListIterator<List<Lane>> itAux = secondaryGrid.listIterator(secondaryGrid.size()-1);

        mainGrid.get(mainGrid.size()-1).addAll(secondaryGrid.get(secondaryGrid.size()-1));

        while (it.hasPrevious() && itAux.hasPrevious()){
            it.previous().addAll(itAux.previous());
        }
    }

    //Joins the lists of two graphs for the split function
    private void joinLists(List<List<Lane>> mainGrid, List<List<Lane>> secondaryGrid){
        ListIterator<List<Lane>> it = mainGrid.listIterator(0);
        ListIterator<List<Lane>> itAux = secondaryGrid.listIterator(0);

        while (it.hasNext() && itAux.hasNext()){
            it.next().addAll(itAux.next());
        }
    }
    
    //Transformations for 3D objects 
    
	public void rotateX(double theta) {
        for (List<Lane> list: getGrid()){
            for (Lane node: list){
                node.rotateX(theta);
            }
        }
    }

    public void rotateY(double theta) {
        for (List<Lane> list: getGrid()){
            for (Lane node: list){
                node.rotateY(theta);
            }
        }
    }

    public void rotateZ(double theta) {
        for (List<Lane> list: getGrid()){
            for (Lane node: list){
                node.rotateZ(theta);
            }
        }
    }

    public void scale(double x, double y, double z) {
        for (List<Lane> list: getGrid()){
            for (Lane node: list){
                node.scale(x,y,z);
            }
        }
    }

    public void translate(double x, double y, double z) {
        for (List<Lane> list: getGrid()){
            for (Lane node: list){
                node.translate(x,y,z);
            }
        }
    }
}
