package Day6;

import Helpers.Helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day6Part1 {
    public static void main(String[] args) throws FileNotFoundException {
        // Input
        int mapSize = 130;
        char[][] input = new char[mapSize][];
        char[][] guardRouteMap = new char[mapSize][mapSize];
        Scanner scanner = new Scanner(new File("2024/Day6/input.txt"));
        int rowIndex = 0;
        while (scanner.hasNextLine()) {
            char[] line = scanner.nextLine().toCharArray();
            input[rowIndex] = line;
            guardRouteMap[rowIndex] = line;
            rowIndex++;
        }
        scanner.close();
        Helpers.print2dArray(input);

        // Part 1
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
        int guardNextXPosition = guardXPosition + xDir[currentDirection];
        int guardNextYPosition = guardYPosition + yDir[currentDirection];
        while (guardNextXPosition >= 0 && guardNextXPosition < mapSize && guardNextYPosition >= 0 && guardNextYPosition < mapSize) {
            guardRouteMap[guardYPosition][guardXPosition] = 'X';
//            Helpers.print2dArray(guardRouteMap);
//            System.out.println();
            guardNextXPosition = guardXPosition + xDir[currentDirection];
            guardNextYPosition = guardYPosition + yDir[currentDirection];
            if (guardNextXPosition < 0 || guardNextXPosition >= mapSize || guardNextYPosition < 0 || guardNextYPosition >= mapSize) {
                break;
            }
            if (input[guardNextYPosition][guardNextXPosition] == '#') {
                currentDirection = (currentDirection + 1) % 4;
                guardNextXPosition = guardXPosition + xDir[currentDirection];
                guardNextYPosition = guardYPosition + yDir[currentDirection];
            }
            if (input[guardNextYPosition][guardNextXPosition] != '#') {
                guardXPosition = guardNextXPosition;
                guardYPosition = guardNextYPosition;
            }
        }
        Helpers.print2dArray(guardRouteMap);
        int resultPart1 = 0;
        for (int i = 0; i < guardRouteMap.length; i++) {
            for (int j = 0; j < guardRouteMap[i].length; j++) {
                if (input[i][j] == 'X') {
                    resultPart1++;
                }
            }
        }
        System.out.println("Result - part 1: " + resultPart1); // 5329
    }
}
