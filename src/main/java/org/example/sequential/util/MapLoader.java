package org.example.sequential.util;

import org.example.sequential.model.Block;
import org.example.sequential.model.CityElement;
import org.example.sequential.model.Road;
import org.example.sequential.model.Semaphore;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class MapLoader {
    public static CityElement[][] loadMap(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(filePath));
        int rows = lines.size();
        int cols = lines.getFirst().length();

        CityElement[][] matrix = new CityElement[rows][cols];
        for (int i = 0; i < rows; i++) {
            String line = lines.get(i);
            for (int j = 0; j < cols; j++) {
                char c = line.charAt(j);
                matrix[i][j] = createCell(c, i , j);
            }
        }
        return matrix;
    }

    private static CityElement createCell(char c, int i, int j) {
        return switch (c) {
            case '#' -> new Block();
            case '.' -> new Road();
            case '+' -> new Semaphore(i, j);
            default -> throw new IllegalArgumentException(
                    "Unknown map character: " + c
            );
        };
    }
}
