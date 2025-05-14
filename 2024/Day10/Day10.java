package Day10;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day10 {

    private static final int MAP_SIZE = 58;
    private static int[][] map = new int[MAP_SIZE][MAP_SIZE];
    private static Map<Point, Set<Point>> trailheadsWithReachable9 = new HashMap<>();
    private static int ratings = 0;

    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();

        // Input
        Scanner scanner = new Scanner(new File("2024/Day10/input.txt"));
        for (int row = 0; scanner.hasNextLine(); row++) {
            char[] inputRow = scanner.nextLine().toCharArray();
            for (int i = 0; i < inputRow.length; i++) {
                // Condition only needed for example with '.' in input
                if (inputRow[i] == '.') {
                    map[row][i] = -9;
                } else {
                    map[row][i] = Integer.parseInt(String.valueOf(inputRow[i]));
                }
            }
        }
        
        int result = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 0) {
                    findScoreOfTrailhead(i, j, new Point(j, i));
                }
            }
        }
        for (var trailhead : trailheadsWithReachable9.entrySet()) {
            result += trailhead.getValue().size();
        }
        System.out.println("Result - part 1: " + result); // 822
        System.out.println("Result - part 2: " + ratings); // 1801

        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + " ms"); // 18 ms
    }

    private static void findScoreOfTrailhead(int i, int j, Point currentTrailhead) {
        if (map[i][j] == 9) {
            ratings++;
            if (trailheadsWithReachable9.containsKey(currentTrailhead)) {
                Set<Point> points = trailheadsWithReachable9.get(currentTrailhead);
                points.add(new Point(j, i));
                trailheadsWithReachable9.put(currentTrailhead, points);
            } else {
                Set<Point> points = new HashSet<>();
                points.add(new Point(j, i));
                trailheadsWithReachable9.put(currentTrailhead, points);
            }
        } else {
            // Down
            if (i + 1 < map.length && map[i][j] + 1 == map[i + 1][j]) {
                 findScoreOfTrailhead(i + 1, j, currentTrailhead);
            }
            // Right
            if (j + 1 < map.length && map[i][j] + 1 == map[i][j + 1]) {
                findScoreOfTrailhead(i, j + 1, currentTrailhead);
            }
            // Up
            if (i - 1 >= 0 && map[i][j] + 1 == map[i - 1][j]) {
                findScoreOfTrailhead(i - 1, j, currentTrailhead);
            }
            // Left
            if (j - 1 >= 0 && map[i][j] + 1 == map[i][j - 1]) {
                findScoreOfTrailhead(i, j - 1, currentTrailhead);
            }
        }
    }
}
