package Day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day4PartTwo {
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

        int xmasCount = 0;
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                xmasCount += findXmas(i, j, input);
            }
        }

        System.out.println("Xmas count: " + xmasCount); // 1965
    }

    private static int findXmas(int i, int j, char[][] input) {
        char[] searchedWord = "MAS".toCharArray();
        if (input[i][j] != 'A') {
            return 0;
        }

        int correctCombinationsCount = 0;

        int correctDiagonals = 0;
        try {
            for (int l = 0; l < searchedWord.length; l++) {
                if (input[i - 1 + l][j - 1 + l] != searchedWord[l]) {
                    break;
                }
                if (l == searchedWord.length - 1) {
                    correctDiagonals++;
                }
            }
            for (int l = 0; l < searchedWord.length; l++) {
                if (input[i - 1 + l][j + 1 - l] != searchedWord[l]) {
                    break;
                }
                if (l == searchedWord.length - 1) {
                    correctDiagonals++;
                }
            }
            searchedWord = "SAM".toCharArray();
            for (int l = 0; l < searchedWord.length; l++) {
                if (input[i - 1 + l][j - 1 + l] != searchedWord[l]) {
                    break;
                }
                if (l == searchedWord.length - 1) {
                    correctDiagonals++;
                }
            }
            for (int l = 0; l < searchedWord.length; l++) {
                if (input[i - 1 + l][j + 1 - l] != searchedWord[l]) {
                    break;
                }
                if (l == searchedWord.length - 1) {
                    correctDiagonals++;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }

        if (correctDiagonals == 2) {
            correctCombinationsCount++;
        }

        return correctCombinationsCount;
    }

}
