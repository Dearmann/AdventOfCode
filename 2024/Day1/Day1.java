package Day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Stream;

class Day1 {

    public static void main(String[] args) throws FileNotFoundException {
        // Part 1
        List<Integer> listOne = new ArrayList<>();
        List<Integer> listTwo = new ArrayList<>();

        Scanner scanner = new Scanner(new File("2024/Day1/input.txt"));
        while (scanner.hasNextLine()) {
            int[] numbers = Stream.of(scanner.nextLine().split(" {3}"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            listOne.add(numbers[0]);
            listTwo.add(numbers[1]);
        }
        scanner.close();

        int totalDistance = 0;

        Collections.sort(listOne);
        Collections.sort(listTwo);

        if (listOne.size() == listTwo.size()) {
            for (int i = 0; i < listOne.size(); i++) {
                totalDistance += Math.abs(listOne.get(i) - listTwo.get(i));
            }
        }

        System.out.println("Part 1: " + totalDistance);

        // Part 2
        int similarityScore = 0;
        HashMap<Integer, Integer> countMap = new HashMap<>();

        listOne.forEach(e -> countMap.put(e, Collections.frequency(listTwo, e)));

        for (var entry : countMap.entrySet()) {
            similarityScore += entry.getKey() * entry.getValue();
        }

        System.out.println("Part 2: " + similarityScore);
    }
}