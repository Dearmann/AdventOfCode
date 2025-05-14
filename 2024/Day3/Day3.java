package Day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("2024/Day3/day3.txt"));
        StringBuilder stringBuilder = new StringBuilder();
        while (scanner.hasNextLine()) {
            stringBuilder.append(scanner.nextLine());
        }
        scanner.close();
        String input = stringBuilder.toString();
        System.out.println("Input: " + input);

        // Part 1
        String regex = "mul\\([0-9]{1,3},[0-9]{1,3}\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        List<String> matches = new ArrayList<>();
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        System.out.println("Matches: " + matches);

        List<Integer> results = matches.stream().map(mul -> {
            String numberPair = mul.substring(4, mul.length() - 1);
            String[] numbers = numberPair.split(",");
            int mulResult = Integer.parseInt(numbers[0]) * Integer.parseInt(numbers[1]);
            return mulResult;
        }).toList();

        int result = results.stream()
                .mapToInt(Integer::intValue)
                .sum();

        System.out.println("Part 1 result: " + result);

        // Part 2
        regex = "mul\\([0-9]{1,3},[0-9]{1,3}\\)|do\\(\\)|don't\\(\\)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(input);
        matches = new ArrayList<>();
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        System.out.println("Matches: " + matches);

        boolean instructionsEnabled = true;
        List<String> toRemoveList = new ArrayList<>();
        for (var match : matches) {
            if ("do()".equals(match)) {
                instructionsEnabled = true;
                toRemoveList.add(match);
            } else if ("don't()".equals(match)) {
                instructionsEnabled = false;
                toRemoveList.add(match);
            } else {
                if (!instructionsEnabled) {
                    toRemoveList.add(match);
                }
            }
        }
        System.out.println("To remove: " + toRemoveList);
        for (String toRemove : toRemoveList) {
            matches.remove(toRemove);
        }
        System.out.println("Matches: " + matches);

        results = matches.stream().map(mul -> {
            String numberPair = mul.substring(4, mul.length() - 1);
            String[] numbers = numberPair.split(",");
            int mulResult = Integer.parseInt(numbers[0]) * Integer.parseInt(numbers[1]);
            return mulResult;
        }).toList();

        result = results.stream()
                .mapToInt(Integer::intValue)
                .sum();

        System.out.println("Part 2 result: " + result);
    }
}
