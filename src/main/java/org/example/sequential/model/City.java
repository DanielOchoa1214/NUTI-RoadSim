package org.example.sequential.model;

import org.example.sequential.util.MapLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class City {
    private final CityElement[][] cityGrid;
    private final List<Agent> agents = new ArrayList<>();

    public City(int numberOfAgents) throws IOException {
        this.cityGrid = MapLoader.loadMap("src/main/resources/city_1000.txt");
        this.generateAgents(numberOfAgents);

        agents.forEach(agent -> ((Road) cityGrid[agent.getX()][agent.getY()]).enter());
    }

    public void step() {
        for (CityElement[] cityElements : cityGrid) {
            for (CityElement cityElement : cityElements) {
                if (cityElement instanceof Semaphore s) {
                    s.tick();
                }
            }
        }

        for (Agent agent : agents) {
            agent.move(cityGrid);
        }
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
