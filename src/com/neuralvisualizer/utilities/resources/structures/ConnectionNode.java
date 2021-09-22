package com.neuralvisualizer.utilities.resources.structures;

import com.neuralvisualizer.utilities.resources.objects.Cube;

//represents a connection between 3D shapes
public class ConnectionNode {
    Cube data;
    Cube connection;

    public ConnectionNode(Cube data, Cube connection) {
        this.data = data;
        this.connection = connection;
    }

    public Cube getData() {
        return data;
    }

    public Cube getConnection() {
        return connection;
    }
}

