package Day11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day11Part2 {

    private static final int numberOfBlinks = 75;

    private static Map<Map.Entry<Long, Long>, Long> stoneWithBlinksToNumberOfStones = new HashMap<>();

    private static long result = 0;

    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();

        List<Long> input = new ArrayList<>(Arrays.stream(new Scanner(new File("2024/Day11/input.txt"))
                        .nextLine()
                        .split(" "))
                .mapToLong(Long::parseLong)
                .boxed()
                .toList());

        for (Long stone : input) {
            result += findNumberOfStones(stone, numberOfBlinks);
        }

        System.out.println("Result - part 2: " + result); // 261952051690787

        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + " ms"); // 96 ms
    }

    private static List<Long> stoneAfterBlink(Long stone) {
        List<Long> nextStone = new ArrayList<>();
        String number = String.valueOf(stone);
        if (stone == 0) {
            nextStone.add(1L);
        } else if (number.length() % 2 == 0) {
            String start = number.substring(0, number.length() / 2);
            String end = number.substring(number.length() / 2);
            nextStone.add(Long.parseLong(start));
            nextStone.add(Long.parseLong(end));
        } else {
            nextStone.add(stone * 2024);
        }
        return nextStone;
    }

    private static long findNumberOfStones(long stone, long blinks) {
        Map.Entry<Long, Long> currentStoneWithBlinksToPerform = new AbstractMap.SimpleEntry<>(stone, blinks);
        if (stoneWithBlinksToNumberOfStones.containsKey(currentStoneWithBlinksToPerform)) {
            return stoneWithBlinksToNumberOfStones.get(currentStoneWithBlinksToPerform);
        }

        List<Long> nextStone = stoneAfterBlink(stone);

        if (blinks == 1) {
            stoneWithBlinksToNumberOfStones.put(currentStoneWithBlinksToPerform, (long) nextStone.size());
            return nextStone.size();
        } else {
            long nrOfStones = 0;
            if (nextStone.size() == 1) {
                nrOfStones += findNumberOfStones(nextStone.getFirst(), blinks - 1);
            } else {
                nrOfStones += findNumberOfStones(nextStone.getFirst(), blinks - 1);
                nrOfStones += findNumberOfStones(nextStone.getLast(), blinks - 1);
            }
            stoneWithBlinksToNumberOfStones.put(currentStoneWithBlinksToPerform, nrOfStones);
            return nrOfStones;
        }
    }

}
