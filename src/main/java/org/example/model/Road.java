package org.example.model;

public class Road implements CityElement {
    private int agents = 0;
    private static final int MAX_AGENTS = 2;

    public boolean canEnter() {
        return agents < MAX_AGENTS;
    }

    public void enter() {
        agents++;
    }

    public void leave() {
        agents--;
    }

    @Override
    public String toString() {
        return ".";
    }
}
