package org.example.model;

public class Semaphore implements CityElement {
    private int x;
    private int y;
    private Direction blockedDirection = Direction.UP;

    public void tick() {
        blockedDirection = next(blockedDirection);
    }

    public boolean blocks(Direction dir) {
        return dir == blockedDirection;
    }

    private Direction next(Direction d) {
        return switch (d) {
            case UP -> Direction.RIGHT;
            case RIGHT -> Direction.DOWN;
            case DOWN -> Direction.LEFT;
            case LEFT -> Direction.UP;
        };
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "+";
    }
}
