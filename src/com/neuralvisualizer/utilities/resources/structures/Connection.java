package com.neuralvisualizer.utilities.resources.structures;

import java.util.List;

//Represents a connection between nodes
public class Connection {
	private Lane lane;
    private List<Lane> laneList;

    public Connection(Lane lane, List<Lane> nodelist) {
        this.lane = lane;
        this.laneList = nodelist;
    }

    public Lane getLane() {
        return lane;
    }

    public List<Lane> getLaneList() {
        return laneList;
    }
}
