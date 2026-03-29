package org.example.generator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class CityGenerator {

    private static final int SIZE = 5000;
    private static final int ROAD_SPACING = 6; // cada 10 celdas hay calles
    private static final Random random = new Random();

    public static void main(String[] args) throws IOException {
        char[][] grid = new char[SIZE][SIZE];

        // 1. Inicializar todo como bloques
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = '#';
            }
        }

        // 2. Crear calles horizontales y verticales
        for (int i = 0; i < SIZE; i += ROAD_SPACING) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = '.';
                grid[j][i] = '.';
            }
        }

        // 3. Colocar semáforos en intersecciones
        for (int i = 0; i < SIZE; i += ROAD_SPACING) {
            for (int j = 0; j < SIZE; j += ROAD_SPACING) {
                grid[i][j] = '+';
            }
        }

        enforceBorders(grid);

        // 5. Guardar archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./city_1000.txt"))) {
            for (int i = 0; i < SIZE; i++) {
                writer.write(grid[i]);
                writer.newLine();
            }
        }

        System.out.println("Ciudad generada en city_1000.txt");
    }

    private static boolean hasRoadNeighbor(char[][] grid, int i, int j) {
        int[][] dirs = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}
        };

        for (int[] d : dirs) {
            int ni = i + d[0];
            int nj = j + d[1];

            if (ni >= 0 && nj >= 0 && ni < grid.length && nj < grid[0].length) {
                if (grid[ni][nj] == '.' || grid[ni][nj] == '+') {
                    return true;
                }
            }
        }
        return false;
    }

    private static void enforceBorders(char[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        // Primera y última fila
        for (int j = 0; j < m; j++) {
            grid[0][j] = '#';
            grid[n - 1][j] = '#';
        }

        // Primera y última columna
        for (int i = 0; i < n; i++) {
            grid[i][0] = '#';
            grid[i][m - 1] = '#';
        }
    }
}