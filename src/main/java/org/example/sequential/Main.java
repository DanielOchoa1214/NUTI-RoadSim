package org.example.sequential;

import org.example.sequential.model.City;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the number or agents: ");
            int numberOfAgents = scanner.nextInt();
            City city = new City(numberOfAgents);

            for (int i = 0; i < 50; i++) {
                city.print();
                city.step();
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}