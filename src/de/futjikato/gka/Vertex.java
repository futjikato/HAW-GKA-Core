package de.futjikato.gka;

import java.util.*;

public class Vertex {

    private List<Vertex> nextVertices;

    protected String name;

    public Vertex(String name) {
        this.name = name;

        nextVertices = new LinkedList<Vertex>();
    }

    public void connect(Vertex vertex) {
        nextVertices.add(vertex);
    }

    public List<Vertex> getVertices() {
        return new ArrayList<Vertex>(nextVertices);
    }

    public String toString() {
        return name;
    }

    public boolean equals(Object other) {
        if(other instanceof Vertex) {
            return ((Vertex) other).name.equals(this.name);
        }

        if(other instanceof String) {
            return other.equals(this.name);
        }

        return false;
    }
}
