package Day9;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;

public class Day9Part2 {
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();

        String input = new Scanner(new File("2024/Day9/input.txt")).nextLine();

        int id = 0;
        List<Integer> idsToCheck = new ArrayList<>();
        List<String> memory = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            int numberOfReps = Integer.parseInt(String.valueOf(input.charAt(i)));
            if (numberOfReps > 0) {
                if (i % 2 == 0) {
                    idsToCheck.add(id);
                    for (int j = 0; j < numberOfReps; j++) {
                        memory.add(String.valueOf(id));
                    }
                    id++;
                } else {
                    for (int j = 0; j < numberOfReps; j++) {
                        memory.add(".");
                    }
                }
            }
        }
        System.out.println("Input: " + memory);

        idsToCheck.remove(0);
        idsToCheck.sort(Collections.reverseOrder());
        List<String> idsToCheckString = idsToCheck.stream().map(String::valueOf).toList();
        for (String idToCheck : idsToCheckString) {
            int memoryStart = memory.indexOf(idToCheck);
            int memoryEnd = memory.lastIndexOf(idToCheck);
            int memorySize = memoryEnd - memoryStart + 1;
            for (int i = 0; i < memory.size(); i++) {
                if (i >= memoryStart) {
                    break;
                }
                int freeSpaceStart;
                int freeSpaceEnd;
                if (memory.get(i) == ".") {
                    freeSpaceStart = i;
                    freeSpaceEnd = i;
                    while (i + 1 < memory.size() && memory.get(i + 1) == ".") {
                        i++;
                        freeSpaceEnd = i;
                    }
                    int freeSpaceSize = freeSpaceEnd - freeSpaceStart + 1;
                    // Change memory location if there is space for it
                    if (freeSpaceSize >= memorySize) {
                        for (int j = 0; j < memorySize; j++) {
                            memory.set(freeSpaceStart + j, idToCheck);
                            memory.set(memoryStart + j, ".");
                        }
                        break;
                    }
                }
            }
        }
        System.out.println("After processing: " + memory);

        // Checksum
        BigDecimal checksum = BigDecimal.ZERO;
        for (int i = 0; i < memory.size(); i++) {
            if (!Objects.equals(memory.get(i), ".")) {
                checksum = checksum.add(new BigDecimal(memory.get(i)).multiply(new BigDecimal(i)));
            }
        }

        // 8553289251139 - too high
        // 8513546095762 - too high
        // 6414780303935 - too high
        System.out.println("Checksum: " + checksum); // 6398096697992

        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + " ms");
    }
}
