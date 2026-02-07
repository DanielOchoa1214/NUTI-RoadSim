package org.example.concurrent.model;

import org.example.concurrent.util.MapLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class City {
    private final CityElement[][] cityGrid;
    private final List<Agent> agents = new ArrayList<>();
    private final ExecutorService pool;

    public City(int numberOfAgents) throws IOException {
        this.cityGrid = MapLoader.loadMap("src/main/resources/city-grid.txt");
        this.generateAgents(numberOfAgents);
        this.pool = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );

        agents.forEach(agent -> ((Road) cityGrid[agent.getX()][agent.getY()]).tryEnter());
    }

    public void step() throws InterruptedException {
        // 1. Update semaphores (single-threaded)
        for (CityElement[] cityElements : cityGrid) {
            for (CityElement cityElement : cityElements) {
                if (cityElement instanceof Semaphore s) {
                    s.tick();
                }
            }
        }

        // 2. Move agents concurrently
        List<Callable<Void>> tasks = new ArrayList<>();

        for (Agent agent : agents) {
            tasks.add(() -> {
                agent.move(cityGrid);
                return null;
            });
        }

        pool.invokeAll(tasks);
    }

    public void shutdown() {
        pool.shutdown();
    }

    public void generateAgents(int numberOfAgents) {
        List<int[]> availableRoads = findAvailableRoads();
        if (numberOfAgents > availableRoads.size()) {
            throw new IllegalArgumentException(
                    "Not enough road spaces for " + numberOfAgents + " agents"
            );
        }
        Collections.shuffle(availableRoads);
        for (int i = 0; i < numberOfAgents; i++) {
            int[] pos = availableRoads.get(i);
            Direction dir = Direction.values()[new Random().nextInt(Direction.values().length)];
            Agent agent = new Agent(pos[0], pos[1], dir);
            agents.add(agent);
        }
    }

    private List<int[]> findAvailableRoads() {
        List<int[]> roads = new ArrayList<>();
        for (int i = 0; i < cityGrid.length; i++) {
            for (int j = 0; j < cityGrid[i].length; j++) {
                if (cityGrid[i][j] instanceof Road) {
                    roads.add(new int[]{i, j});
                }
            }
        }
        return roads;
    }

    public void print() {
        char[][] view = new char[cityGrid.length][cityGrid[0].length];

        for (int i = 0; i < cityGrid.length; i++) {
            for (int j = 0; j < cityGrid[i].length; j++) {
                view[i][j] = cityGrid[i][j].toString().charAt(0);
            }
        }

        for (Agent agent : agents) {
            view[agent.getX()][agent.getY()] = 'A';
        }

        for (char[] row : view) {
            System.out.println(new String(row));
        }

        System.out.println();
    }
}
