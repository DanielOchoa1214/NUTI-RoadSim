package org.example.sequential;

import org.example.sequential.model.City;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        try (Scanner scanner = new Scanner(System.in)) {
            var iterationTimes = new ArrayList<Long>();
            System.out.print("Enter the number or agents: ");
            int numberOfAgents = scanner.nextInt();
            var programStart = System.currentTimeMillis();
            City city = new City(numberOfAgents);

            for (int i = 0; i < 50; i++) {
                city.print();
                var iterationStart = System.currentTimeMillis();
                city.step();
                var iterationEnd = System.currentTimeMillis();
                iterationTimes.add(iterationEnd - iterationStart);
            }
            var programEnd = System.currentTimeMillis();
            System.out.println("Number or agents: " + numberOfAgents);
            System.out.println("Execution time: " + (programEnd - programStart) + " ms");
            System.out.println("Average iteration time: " + iterationTimes.stream().mapToLong(Long::longValue).average().orElse(0) + " ms");
        }
    }
}