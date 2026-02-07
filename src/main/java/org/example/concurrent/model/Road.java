package org.example.concurrent.model;

import java.util.concurrent.locks.ReentrantLock;

public class Road implements CityElement {
    private int agents = 0;
    private static final int MAX_AGENTS = 2;

    private final ReentrantLock lock = new ReentrantLock();

    public boolean tryEnter() {
        lock.lock();
        try {
            if (agents < MAX_AGENTS) {
                agents++;
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public void leave() {
        lock.lock();
        try {
            agents--;
        } finally {
            lock.unlock();
        }
    }

    public boolean canEnter() {
        lock.lock();
        try {
            return agents < MAX_AGENTS;
        } finally {
            lock.unlock();
        }
    }

    public ReentrantLock getLock() {
        return lock;
    }

    @Override
    public String toString() {
        return ".";
    }
}
