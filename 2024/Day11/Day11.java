package Day11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day11 {

    private static final int numberOfBlinks = 25;

    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();

        List<Long> input = new ArrayList<>(Arrays.stream(new Scanner(new File("2024/Day11/input.txt"))
                        .nextLine()
                        .split(" "))
                        .mapToLong(Long::parseLong)
                        .boxed()
                        .toList());

        for (int i = 0; i < numberOfBlinks; i++) {
            for (int j = 0; j < input.size(); j++) {
                String number = input.get(j).toString();
                if (input.get(j) == 0) {
                    input.set(j, 1L);
                } else if (number.length() % 2 == 0) {
                    String start = number.substring(0, number.length() / 2);
                    String end = number.substring(number.length() / 2);
                    input.remove(j);
                    input.add(j, Long.parseLong(end));
                    input.add(j, Long.parseLong(start));
                    j++;
                } else {
                    input.set(j, input.get(j) * 2024);
                }
            }
            System.out.println("Current blink: " + (i + 1) + ", number of stones: " + input.size());
        }

        System.out.println("Result - part 1: " + input.size()); // 220722

        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + " ms"); // 1136 ms
    }

}
