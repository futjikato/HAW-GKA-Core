package de.futjikato.gka;

import java.util.*;

public class Vertex<T extends Vertex> {

    private List<T> nextVertices;

    protected String name;

    public Vertex(String name) {
        this.name = name;

        nextVertices = new LinkedList<T>();
    }

    public void connect(T vertex) {
        if(!nextVertices.contains(vertex)) {
            nextVertices.add(vertex);
        }
    }

    public List<T> getVertices() {
        return new ArrayList<T>(nextVertices);
    }

    public String toString() {
        return name;
    }

    public boolean equals(Object other) {
        if (other instanceof Vertex) {
            return ((Vertex) other).name.equals(this.name);
        }

        return other instanceof String && other.equals(this.name);

    }
}
