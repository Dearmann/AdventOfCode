package Day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Day4 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("2024/Day4/day4.txt"));
        int maxRows = 140;
        char[][] input = new char[maxRows][];

        int row = 0;
        while (scanner.hasNextLine()) {
            input[row] = scanner.nextLine().toCharArray();
            row++;
        }
        scanner.close();

        System.out.println("Input:");
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                System.out.print(input[i][j]);
            }
            System.out.println();
        }

        // Array for printing correct combinations
        char[][] correctCombinations = new char[maxRows][input[0].length];
        for (int k = 0; k < correctCombinations.length; k++) {
            Arrays.fill(correctCombinations[k], '.');
        }

        int xmasCount = 0;
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                xmasCount += findXmas(i, j, input, correctCombinations);
            }
        }

        System.out.println("Correct combinations:");
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                System.out.print(correctCombinations[i][j]);
            }
            System.out.println();
        }
        System.out.println("Xmas count: " + xmasCount); // 2603
    }

    private static int findXmas(int i, int j, char[][] input, char[][] correctCombinations) {
        char[] searchedWord = "XMAS".toCharArray();
        if (input[i][j] != searchedWord[0]) {
            return 0;
        }

        int correctCombinationsOnOneX = 0;
        int[] x = { 1, -1, 0,  0, 1,  1, -1, -1};
        int[] y = { 0,  0, 1, -1, 1, -1,  1, -1};

        for (int k = 0; k < x.length; k++) {
            for (int l = 1; l < searchedWord.length; l++) {
                try {
                    if (searchedWord[l] != input[i + y[k] * l][j + x[k] * l]) {
                        break;
                    } else {
                        correctCombinations[i][j] = 'X';
                        correctCombinations[i + y[k] * l][j + x[k] * l] = searchedWord[l];
                    }
                    if (l == searchedWord.length - 1) {
                        correctCombinationsOnOneX++;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    break;
                }
            }
        }

        return correctCombinationsOnOneX;
    }

}
