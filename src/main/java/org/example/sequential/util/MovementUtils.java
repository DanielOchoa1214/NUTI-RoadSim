package org.example.sequential.util;

import org.example.sequential.model.Agent;
import org.example.sequential.model.CityElement;
import org.example.sequential.model.Direction;
import org.example.sequential.model.Road;
import org.example.sequential.model.Semaphore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MovementUtils {
    public static boolean tryDirectedMove(Direction d, CityElement[][] map, Agent agent) {
        int nr = agent.getX() + d.dr;
        int nc = agent.getY() + d.dc;

        if (inBounds(nr, nc, map)) return false;

        CityElement target = map[nr][nc];

        // --- Semaphore logic ---
        if (target instanceof Semaphore sem) {
            if (sem.blocks(d)) {
                return divert(map, sem,  agent);
            }
            return divert(map, sem, agent); // always divert, never enter semaphore
        }

        if (!(target instanceof Road road) || !road.canEnter()) {
            return false;
        }

        moveTo(nr, nc, road, map, agent);
        return true;
    }

    private static boolean divert(CityElement[][] map, Semaphore sem, Agent agent) {
        List<int[]> alternatives = new ArrayList<>();

        for (Direction d : Direction.values()) {
            int nr = sem.getX() + d.dr;
            int nc = sem.getY() + d.dc;

            if (inBounds(nr, nc, map)) continue;
            if (nr == agent.getX() && nc == agent.getY()) continue;

            if (map[nr][nc] instanceof Road road && road.canEnter()) {
                alternatives.add(new int[]{nr, nc, d.ordinal()});
            }
        }

        if (alternatives.isEmpty()) return false;

        int[] choice = alternatives.get(new Random().nextInt(alternatives.size()));
        Direction newDir = Direction.values()[choice[2]];
        Road target = (Road) map[choice[0]][choice[1]];

        moveTo(choice[0], choice[1], target, map, agent);
        agent.setDirection(newDir);
        return true;
    }

    private static void moveTo(int nr, int nc, Road target, CityElement[][] map, Agent agent) {
        Road current = (Road) map[agent.getX()][agent.getY()];
        current.leave();
        target.enter();

        agent.setX(nr);
        agent.setY(nc);
    }

    public static boolean canMove(Direction d, CityElement[][] map, Agent agent) {
        int nr = agent.getX() + d.dr;
        int nc = agent.getY() + d.dc;

        if (inBounds(nr, nc, map)) return false;

        if (map[nr][nc] instanceof Road road) {
            return road.canEnter();
        }

        return map[nr][nc] instanceof Semaphore; // diversion handled later
    }

    private static boolean inBounds(int r, int c, CityElement[][] map) {
        return r < 0 || c < 0 ||
                r >= map.length || c >= map[0].length;
    }
}
