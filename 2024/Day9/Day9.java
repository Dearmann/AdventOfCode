package Day9;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Day9 {
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();

        String input = new Scanner(new File("2024/Day9/input.txt")).nextLine();

        int id = 0;
        List<String> memory = new ArrayList<>();
        List<Integer> indexesWithFreeSpace = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            int numberOfReps = Integer.parseInt(String.valueOf(input.charAt(i)));
            if (i % 2 == 0) {
                for (int j = 0; j < numberOfReps; j++) {
                    memory.add(String.valueOf(id));
                }
                id++;
            } else {
                for (int j = 0; j < numberOfReps; j++) {
                    memory.add(".");
                    indexesWithFreeSpace.add(memory.size() - 1);
                }
            }
        }

//        Pattern pattern = Pattern.compile("[0-9]+\\.+");

        for (int i = memory.size() - 1; i >= 0; i--) {
//            long matchCount = pattern.matcher(String.valueOf(discRepresentation)).results().count();
//            if (matchCount == 1) {
//                break;
//            }
            int countOfMemoryBlocks = 0;
            for (int j = 0; j < memory.size() - 1; j++) {
                if (!".".equals(memory.get(j)) && ".".equals(memory.get(j + 1))) {
                    countOfMemoryBlocks++;
                    if (countOfMemoryBlocks >= 2) {
                        break;
                    }
                }
            }
            if (countOfMemoryBlocks == 1) {
                break;
            }
            if (!Objects.equals(memory.get(i), ".")) {
                memory.set(indexesWithFreeSpace.getFirst(), memory.get(i));
                indexesWithFreeSpace.removeFirst();
                memory.set(i, ".");
                indexesWithFreeSpace.add(i);
            }
        }

        // Checksum
        BigDecimal checksum = BigDecimal.ZERO;
        for (int i = 0; i < memory.size(); i++) {
            if (!Objects.equals(memory.get(i), ".")) {
                checksum = checksum.add(new BigDecimal(memory.get(i)).multiply(new BigDecimal(i)));
            }
        }

        // 691306826 - too low
        // 90885620042 - too low
        System.out.println("Checksum: " + checksum); // 6370402949053

        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + " ms");
    }
}
