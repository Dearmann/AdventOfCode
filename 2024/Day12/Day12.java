package Day12;

import Helpers.Helpers;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.*;

public class Day12 {

    private static final int inputSize = 140;

    private static final char[][] input = new char[inputSize][inputSize];

    private static final Set<char[][]> separatedPlots = new HashSet<>();
    private static final List<Point> plotsAlreadyCounted = new ArrayList<>();

    private static int price = 0;
    private static int discountPrice = 0;

    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();

        // Input
        Scanner scanner = new Scanner(new File("2024/Day12/input.txt"));
        int row = 0;
        while (scanner.hasNextLine()) {
            input[row] = scanner.nextLine().toCharArray();
            row++;
        }

        char[][] newArea = new char[inputSize][inputSize];
        for (var areaRow : newArea) {
            Arrays.fill(areaRow, '.');
        }
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                addPlotToArea(i, j, newArea);
            }
        }

        calculatePrice();
        calculateDiscountPrice();

        System.out.println("Result - part 1: " + price); // 1461806

        // 880828 - too low
        System.out.println("Result - part 2: " + discountPrice); // 887932

        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + " ms"); // 953 ms
    }

    private static void calculateDiscountPrice() {
        for (char[][] separatedArea : separatedPlots) {
            int areaAngles = 0;
            int areaSize = 0;
            for (int i = 0; i < inputSize; i++) {
                for (int j = 0; j < inputSize; j++) {
                    char currentPlant = separatedArea[i][j];
                    if (currentPlant != '.') {
                        areaSize++;
                        int anglesOfBlock = calculateAngleCount(i, j, separatedArea);
//                        debug(i, j, separatedArea, anglesOfBlock);
                        areaAngles += anglesOfBlock;
                    }
                }
            }
//            System.out.println();
//            System.out.println("Size = " + areaSize);
//            System.out.println("Angles = Sides = " + areaAngles);
//            System.out.println("Price: " + (areaAngles * areaSize));
//            Helpers.print2dArray(separatedArea);
            discountPrice += areaAngles * areaSize;
        }
    }

    private static void debug(int i, int j, char[][] separatedArea, int anglesOfBlock) {
        separatedArea[i][j] = Character.toLowerCase(separatedArea[i][j]);
        System.out.println();
        System.out.println("Angles: " + anglesOfBlock);
        Helpers.print2dArray(separatedArea);
        separatedArea[i][j] = Character.toUpperCase(separatedArea[i][j]);
    }

    private static int calculateAngleCount(int i, int j, char[][] separatedArea) {
        char currentPlant = separatedArea[i][j];
        boolean right = j + 1 < inputSize && separatedArea[i][j + 1] == currentPlant;
        boolean left = j - 1 >= 0 && separatedArea[i][j - 1] == currentPlant;
        boolean up = i - 1 >= 0 && separatedArea[i - 1][j] == currentPlant;
        boolean down = i + 1 < inputSize && separatedArea[i + 1][j] == currentPlant;
        boolean up_right = i - 1 >= 0 && j + 1 < inputSize && separatedArea[i - 1][j + 1] == currentPlant;
        boolean up_left = i - 1 >= 0 && j - 1 >= 0 && separatedArea[i - 1][j - 1] == currentPlant;
        boolean down_right = i + 1 < inputSize && j + 1 < inputSize && separatedArea[i + 1][j + 1] == currentPlant;
        boolean down_left = i + 1 < inputSize && j - 1 >= 0 && separatedArea[i + 1][j - 1] == currentPlant;
        int angles = 0;
        if (!up && !up_right && !right)
            angles++;
        if (!right && !down_right && !down)
            angles++;
        if (!down && !down_left && !left)
            angles++;
        if (!left && !up_left && !up)
            angles++;
        if (up && !up_right && right)
            angles++;
        if (right && !down_right && down)
            angles++;
        if (down && !down_left && left)
            angles++;
        if (left && !up_left && up)
            angles++;
        if (!up && up_left && !left || !right && up_right && !up)
            angles = angles + 2;
        return angles;
    }

    private static void calculatePrice() {
        for (char[][] separatedArea : separatedPlots) {
            int areaPerimeter = 0;
            int areaSize = 0;
            for (int i = 0; i < inputSize; i++) {
                for (int j = 0; j < inputSize; j++) {
                    char currentPlant = separatedArea[i][j];
                    if (currentPlant != '.') {
                        areaSize++;
                        // Right
                        if (j + 1 >= inputSize || separatedArea[i][j + 1] != currentPlant) {
                            areaPerimeter++;
                        }
                        // Left
                        if (j - 1 < 0 || separatedArea[i][j - 1] != currentPlant) {
                            areaPerimeter++;
                        }
                        // Up
                        if (i - 1 < 0 || separatedArea[i - 1][j] != currentPlant) {
                            areaPerimeter++;
                        }
                        // Down
                        if (i + 1 >= inputSize || separatedArea[i + 1][j] != currentPlant) {
                            areaPerimeter++;
                        }
                    }
                }
            }
//            System.out.println();
//            System.out.println("Size = " + areaSize);
//            System.out.println("Perimeter = " + areaPerimeter);
//            System.out.println("Price: " + (areaPerimeter * areaSize));
//            Helpers.print2dArray(separatedArea);
            price += areaPerimeter * areaSize;
        }
    }

    private static void addPlotToArea(int i, int j, char[][] area) {
        char currentPlant = input[i][j];
        if (currentPlant == area[i][j]) {
            // Right
            if (j + 1 < inputSize && input[i][j + 1] == currentPlant && area[i][j + 1] != currentPlant) {
                if (plotsAlreadyCounted.contains(new Point(j + 1, i))) {
                    return;
                }
                area[i][j + 1] = currentPlant;
                plotsAlreadyCounted.add(new Point(j + 1, i));
                addPlotToArea(i, j + 1, area);
            }
            // Left
            if (j - 1 >= 0 && input[i][j - 1] == currentPlant && area[i][j - 1] != currentPlant) {
                if (plotsAlreadyCounted.contains(new Point(j - 1, i))) {
                    return;
                }
                area[i][j - 1] = currentPlant;
                plotsAlreadyCounted.add(new Point(j - 1, i));
                addPlotToArea(i, j - 1, area);
            }
            // Up
            if (i - 1 >= 0 && input[i - 1][j] == currentPlant && area[i - 1][j] != currentPlant) {
                if (plotsAlreadyCounted.contains(new Point(j, i - 1))) {
                    return;
                }
                area[i - 1][j] = currentPlant;
                plotsAlreadyCounted.add(new Point(j, i - 1));
                addPlotToArea(i - 1, j, area);
            }
            // Down
            if (i + 1 < inputSize && input[i + 1][j] == currentPlant && area[i + 1][j] != currentPlant) {
                if (plotsAlreadyCounted.contains(new Point(j, i + 1))) {
                    return;
                }
                area[i + 1][j] = currentPlant;
                plotsAlreadyCounted.add(new Point(j, i + 1));
                addPlotToArea(i + 1, j, area);
            }
        } else {
            char[][] newArea = new char[inputSize][inputSize];
            for (var row : newArea) {
                Arrays.fill(row, '.');
            }
            newArea[i][j] = currentPlant;
            plotsAlreadyCounted.add(new Point(j, i));
            addPlotToArea(i, j, newArea);
        }
        separatedPlots.add(area);
    }
}
