package Day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Stream;

public class Day2Incorrect {
    public static void main(String[] args) throws FileNotFoundException {
        // Incorrect probably because Problem Dampener is used only on first two elements

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
            List<Integer> numbersMutable = new ArrayList<>(numbers);
            boolean decreaseOrIncreaseFlag = true;
            boolean maxMinDiffFlag = true;
            if (numbersMutable.size() > 1) {
                boolean problemDampenerUsed = false;
                if (numbersMutable.get(0) - numbersMutable.get(1) > 0) {
                    for (int i = 0; i < numbersMutable.size() - 1; i++) {
                        int diff = numbersMutable.get(i) - numbersMutable.get(i + 1);
                        if (diff < 0) {
                            if (!problemDampenerUsed) {
                                problemDampenerUsed = true;
                                numbersMutable.remove(i);
                                if (checkWithoutDampener(numbersMutable)) {
                                    i = -1;
                                    continue;
                                }
                                numbersMutable = new ArrayList<>(numbers);
                                numbersMutable.remove(i + 1);
                                if (checkWithoutDampener(numbersMutable)) {
                                    i = -1;
                                } else {
                                    decreaseOrIncreaseFlag = false;
                                }
                            } else {
                                decreaseOrIncreaseFlag = false;
                            }
                        }
                        if (Math.abs(diff) < 1 || Math.abs(diff) > 3) {
                            if (!problemDampenerUsed) {
                                problemDampenerUsed = true;
                                numbersMutable.remove(i);
                                if (checkWithoutDampener(numbersMutable)) {
                                    i = -1;
                                    continue;
                                }
                                numbersMutable = new ArrayList<>(numbers);
                                numbersMutable.remove(i + 1);
                                if (checkWithoutDampener(numbersMutable)) {
                                    i = -1;
                                } else {
                                    maxMinDiffFlag = false;
                                }
                            } else {
                                maxMinDiffFlag = false;
                            }
                        }
                    }
                } else if (numbersMutable.get(0) - numbersMutable.get(1) < 0) {
                    for (int i = 0; i < numbersMutable.size() - 1; i++) {
                        int diff = numbersMutable.get(i) - numbersMutable.get(i + 1);
                        if (diff > 0) {
                            if (!problemDampenerUsed) {
                                problemDampenerUsed = true;
                                numbersMutable.remove(i);
                                if (checkWithoutDampener(numbersMutable)) {
                                    i = -1;
                                    continue;
                                }
                                numbersMutable = new ArrayList<>(numbers);
                                numbersMutable.remove(i + 1);
                                if (checkWithoutDampener(numbersMutable)) {
                                    i = -1;
                                } else {
                                    decreaseOrIncreaseFlag = false;
                                }
                            } else {
                                decreaseOrIncreaseFlag = false;
                            }
                        }
                        if (Math.abs(diff) < 1 || Math.abs(diff) > 3) {
                            if (!problemDampenerUsed) {
                                problemDampenerUsed = true;
                                numbersMutable.remove(i);
                                if (checkWithoutDampener(numbersMutable)) {
                                    i = -1;
                                    continue;
                                }
                                numbersMutable = new ArrayList<>(numbers);
                                numbersMutable.remove(i + 1);
                                if (checkWithoutDampener(numbersMutable)) {
                                    i = -1;
                                } else {
                                    maxMinDiffFlag = false;
                                }
                            } else {
                                maxMinDiffFlag = false;
                            }
                        }
                    }
                } else {
                    decreaseOrIncreaseFlag = false;
                }
            } else {
                decreaseOrIncreaseFlag = false;
            }

            if (decreaseOrIncreaseFlag && maxMinDiffFlag) {
                result++;
//                System.out.println("Correct combination: " + numbers);
                System.out.println("Correct - mutable: " + numbersMutable + "     -      { " + numbers + " }");
            } else {
                System.out.println("Incorrect: " + numbersMutable + "     -     { " + numbers + " }");
            }
        }

        // 631 - too high
        // 434 - too high
        // 396 - too low
        // 392 - too low
        // 403 - not right
        // 434-396
        System.out.println("Result: " + result);
    }

    private static boolean ascendingOrderCheck(List<Integer> numbers) {
        return true;
    }

    private static boolean descendingOrderCheck(List<Integer> numbers) {
        return true;
    }

    private static boolean checkWithoutDampener(List<Integer> numbers) {
        if (numbers.size() > 1) {
            if (numbers.get(0) - numbers.get(1) > 0) {
                for (int i = 0; i < numbers.size() - 1; i++) {
                    int diff = numbers.get(i) - numbers.get(i + 1);
                    if (diff < 0) {
                        return false;
                    }
                    if (Math.abs(diff) < 1 || Math.abs(diff) > 3) {
                        return false;
                    }
                }
            } else if (numbers.get(0) - numbers.get(1) < 0) {
                for (int i = 0; i < numbers.size() - 1; i++) {
                    int diff = numbers.get(i) - numbers.get(i + 1);
                    if (diff > 0) {
                        return false;
                    }
                    if (Math.abs(diff) < 1 || Math.abs(diff) > 3) {
                        return false;
                    }
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
}
