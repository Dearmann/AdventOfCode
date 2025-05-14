package Day8;

import Helpers.Helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day8Part2 {
    public static void main(String[] args) throws FileNotFoundException {
        // Input
        int mapSize = 50;
        char[][] input = new char[mapSize][];
        char[][] antinodes = new char[mapSize][];
        Scanner scanner = new Scanner(new File("2024/Day8/input.txt"));
        for (int i = 0; scanner.hasNextLine(); i++) {
            char[] line = scanner.nextLine().toCharArray();
            input[i] = line;
            antinodes[i] = line;
        }
        scanner.close();
        Helpers.print2dArray(input);

        // Part 2
        int numberOfAntinodesOnMap = 0;
        Map<Character, List<Map.Entry<Integer, Integer>>> typesOfFrequencies = new HashMap<>();
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                if (input[i][j] != '.') {
                    if (typesOfFrequencies.containsKey(input[i][j])) {
                        typesOfFrequencies.get(input[i][j]).add(new AbstractMap.SimpleEntry<>(i, j));
                    } else {
                        List<Map.Entry<Integer, Integer>> coordinatesList = new ArrayList<>();
                        coordinatesList.add(new AbstractMap.SimpleEntry<>(i, j));
                        typesOfFrequencies.put(input[i][j], coordinatesList);
                    }
                }
            }
        }

        System.out.println(typesOfFrequencies);

        for (char frequencyType : typesOfFrequencies.keySet()) {
            if (typesOfFrequencies.get(frequencyType).size() > 1) {
                numberOfAntinodesOnMap += typesOfFrequencies.get(frequencyType).size(); // In part 2 each antenna is now also antinode if there is at least 2 of them
            }
            for (var firstAntenna : typesOfFrequencies.get(frequencyType)) {
                for (var secondAntenna : typesOfFrequencies.get(frequencyType)) {
                    if (firstAntenna != secondAntenna) {
                        int xDiff = firstAntenna.getValue() - secondAntenna.getValue();
                        int yDiff = firstAntenna.getKey() - secondAntenna.getKey();
                        while (
                                firstAntenna.getKey() + yDiff >= 0 &&
                                firstAntenna.getKey() + yDiff < mapSize &&
                                firstAntenna.getValue() + xDiff >= 0 &&
                                firstAntenna.getValue() + xDiff < mapSize
                        ) {
                            if (antinodes[firstAntenna.getKey() + yDiff][firstAntenna.getValue() + xDiff] == '.') {
                                antinodes[firstAntenna.getKey() + yDiff][firstAntenna.getValue() + xDiff] = '#';
                                numberOfAntinodesOnMap++;
                            }
                            xDiff += firstAntenna.getValue() - secondAntenna.getValue();
                            yDiff += firstAntenna.getKey() - secondAntenna.getKey();
                        }
                    }
                }
            }
        }
        Helpers.print2dArray(antinodes);

        // 1393 - too high
        System.out.println("Result - part 2: " + numberOfAntinodesOnMap); // 1280
    }
}
