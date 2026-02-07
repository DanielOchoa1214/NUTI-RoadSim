package org.example.concurrent.model;

import org.example.concurrent.util.MovementUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Agent {
    private int x;
    private int y;
    private boolean isMoving;
    private Direction direction;

    public Agent(int row, int col, Direction direction) {
        this.x = row;
        this.y = col;
        this.direction = direction;
    }

    public void move(CityElement[][] map) {

        // Try forward first
        if (direction != null && MovementUtils.tryDirectedMove(direction, map, this)) {
            return;
        }

        // Otherwise pick alternative (excluding opposite)
        List<Direction> options = new ArrayList<>();

        for (Direction d : Direction.values()) {
            if (direction != null && d == direction.opposite()) continue;
            if (MovementUtils.canMove(d, map, this)) options.add(d);
        }

        if (!options.isEmpty()) {
            Direction chosen = options.get(new Random().nextInt(options.size()));
            MovementUtils.tryDirectedMove(chosen, map, this);
            direction = chosen;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "{" + x + "," + y + "}";
    }
}
