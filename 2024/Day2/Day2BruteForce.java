package Day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Stream;

public class Day2BruteForce {
    public static void main(String[] args) throws FileNotFoundException {
        List<List<Integer>> data = new ArrayList<>();
        int result = 0;

        Scanner scanner = new Scanner(new File("2024/Day2/day2.txt"));
        while (scanner.hasNextLine()) {
            int[] numbers = Stream.of(scanner.nextLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            data.add(Arrays.stream(numbers).boxed().toList());
        }
        scanner.close();

        System.out.println(data);

        for (List<Integer> numbers : data) {
            for (int i = 0; i < numbers.size(); i++) {
                List<Integer> numbersMutable = new ArrayList<>(numbers);
                numbersMutable.remove(i);
                if (valid(numbersMutable)) {
                    result++;
                    break;
                }
            }
        }

        System.out.println("Result: " + result);
    }

    private static boolean valid(List<Integer> numbers) {
        boolean decreaseOrIncreaseFlag = true;
        boolean maxMinDiffFlag = true;
        if (numbers.size() > 1) {
            if (numbers.get(0) - numbers.get(1) > 0) {
                for (int i = 0; i < numbers.size() - 1; i++) {
                    int diff = numbers.get(i) - numbers.get(i + 1);
                    if (diff < 0) {
                        decreaseOrIncreaseFlag = false;
                    }
                    if (Math.abs(diff) < 1 || Math.abs(diff) > 3) {
                        maxMinDiffFlag = false;
                    }
                }
            } else if (numbers.get(0) - numbers.get(1) < 0) {
                for (int i = 0; i < numbers.size() - 1; i++) {
                    int diff = numbers.get(i) - numbers.get(i + 1);
                    if (diff > 0) {
                        decreaseOrIncreaseFlag = false;
                    }
                    if (Math.abs(diff) < 1 || Math.abs(diff) > 3) {
                        maxMinDiffFlag = false;
                    }
                }
            } else {
                decreaseOrIncreaseFlag = false;
            }
        } else {
            decreaseOrIncreaseFlag = false;
        }

        return decreaseOrIncreaseFlag && maxMinDiffFlag;
    }
}
