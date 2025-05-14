package Helpers;

public class Helpers {
    public static <T> void print2dArray(T[][] array) {
        for (var row : array) {
            for (var col : row) {
                System.out.print(col);
            }
            System.out.println();
        }
    }

    public static void print2dArray(char[][] array) {
        for (var row : array) {
            for (var col : row) {
                System.out.print(col);
            }
            System.out.println();
        }
    }

    public static void print2dArray(int[][] array) {
        for (var row : array) {
            for (var col : row) {
                System.out.print(col);
            }
            System.out.println();
        }
    }
}
