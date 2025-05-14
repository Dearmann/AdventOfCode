package Day6;

import Helpers.Helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day6Part2 {
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        // Input
        int mapSize = 130;
        char[][] input = new char[mapSize][];
        char[][] guardRouteMap = new char[mapSize][mapSize];
        Scanner scanner = new Scanner(new File("2024/Day6/input.txt"));
        int rowIndex = 0;
        while (scanner.hasNextLine()) {
            char[] line = scanner.nextLine().toCharArray();
            input[rowIndex] = line;
            guardRouteMap[rowIndex] = line.clone();
            rowIndex++;
        }
        scanner.close();
        Helpers.print2dArray(input);

        // Part 2
        int[] xDir = { 0, 1, 0, -1};
        int[] yDir = {-1, 0, 1,  0};
        int currentDirection = 0;
        int guardXPosition = 0;
        int guardYPosition = 0;
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                if (input[i][j] == '^') {
                    guardXPosition = j;
                    guardYPosition = i;
                }
            }
        }
        System.out.println("Guard's position: x=" + guardXPosition + " y=" + guardYPosition);

        int initialXPosition = guardXPosition;
        int initialYPosition = guardYPosition;
        int deadlocks = 0;
        int directionChangeWithNoNewPlaceCount = 0;
        int guardNextXPosition;
        int guardNextYPosition;
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                if (input[i][j] == '.') {
                    input[i][j] = '#';
                    for (int k = 0; k < guardRouteMap.length; k++) {
                        guardRouteMap[k] = input[k].clone();
                    }
                    guardXPosition = initialXPosition;
                    guardYPosition = initialYPosition;
                    currentDirection = 0;
                    guardNextXPosition = guardXPosition + xDir[currentDirection];
                    guardNextYPosition = guardYPosition + yDir[currentDirection];
                    while (guardNextXPosition >= 0 && guardNextXPosition < mapSize && guardNextYPosition >= 0 && guardNextYPosition < mapSize) {
                        // Deadlock detection
                        if (guardRouteMap[guardYPosition][guardXPosition] != 'X') {
                            directionChangeWithNoNewPlaceCount = 0;
                        }
                        if (directionChangeWithNoNewPlaceCount >= 4) {
                            deadlocks++;
                            break;
                        }
                        guardRouteMap[guardYPosition][guardXPosition] = 'X';
                        guardNextXPosition = guardXPosition + xDir[currentDirection];
                        guardNextYPosition = guardYPosition + yDir[currentDirection];
                        if (guardNextXPosition < 0 || guardNextXPosition >= mapSize || guardNextYPosition < 0 || guardNextYPosition >= mapSize) {
                            break;
                        }
                        if (input[guardNextYPosition][guardNextXPosition] == '#') {
                            directionChangeWithNoNewPlaceCount++;
                            currentDirection = (currentDirection + 1) % 4;
                            guardNextXPosition = guardXPosition + xDir[currentDirection];
                            guardNextYPosition = guardYPosition + yDir[currentDirection];
                        }
                        if (input[guardNextYPosition][guardNextXPosition] != '#') {
                            guardXPosition = guardNextXPosition;
                            guardYPosition = guardNextYPosition;
                        }
                    }
                    input[i][j] = '.';
                }
            }
        }

        // 2112 - too low
        System.out.println("Result - part 2: " + deadlocks); // 2162
        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + " ms");
    }
}
