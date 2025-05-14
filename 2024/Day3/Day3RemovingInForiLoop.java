package Day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3RemovingInForiLoop {
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

        System.out.println("Part 1 result: " + result); // 175015740

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
        for (int i = 0; i < matches.size(); i++) {
            if ("do()".equals(matches.get(i))) {
                instructionsEnabled = true;
                matches.remove(matches.get(i));
                i--;
            } else if ("don't()".equals(matches.get(i))) {
                instructionsEnabled = false;
                matches.remove(matches.get(i));
                i--;
            } else {
                if (!instructionsEnabled) {
                    matches.remove(matches.get(i));
                    i--;
                }
            }
        }

        results = matches.stream().map(mul -> {
            String numberPair = mul.substring(4, mul.length() - 1);
            String[] numbers = numberPair.split(",");
            int mulResult = Integer.parseInt(numbers[0]) * Integer.parseInt(numbers[1]);
            return mulResult;
        }).toList();

        result = results.stream()
                .mapToInt(Integer::intValue)
                .sum();

        System.out.println("Part 2 result: " + result); // 112272912
    }
}
