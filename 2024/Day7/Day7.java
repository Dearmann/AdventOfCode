package Day7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day7 {
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();

        // Input
        List<String> input = new ArrayList<>();
        Scanner scanner = new Scanner(new File("2024/Day7/input.txt"));
        while (scanner.hasNextLine()) {
            input.add(scanner.nextLine());
        }
        scanner.close();
        System.out.println("Input: " + input);

        // Part 1
        long result = 0;
        List<Long> correctEquationResults = new ArrayList<>();
        List<String> incorrectEquations = new ArrayList<>();
        char[] typesOfOperators = new char[] {'+', '*'};
        for (String equation : input) {
            String[] equationSplit = equation.split(": ");
            long inputResult = Long.parseLong(equationSplit[0]);
            long[] operands = Arrays.stream(equationSplit[1].split(" ")).mapToLong(Long::parseLong).toArray();
            int numberOfOperators = operands.length - 1;
            List<String> allPossibleChainOfOperators = new ArrayList<>();
            generateAllPermutationsWithRepetition(typesOfOperators, numberOfOperators, "", allPossibleChainOfOperators);
            boolean isEquationCorrect = false;
            for (String chainOfOperators : allPossibleChainOfOperators) {
                long equationResult = operands[0];
                for (int i = 0; i < chainOfOperators.length(); i++) {
                    if (chainOfOperators.charAt(i) == '+') {
                        equationResult = equationResult + operands[i + 1];
                    } else if (chainOfOperators.charAt(i) == '*') {
                        equationResult = equationResult * operands[i + 1];
                    }
                }
                if (equationResult == inputResult) {
                    isEquationCorrect = true;
                }
            }
            if (isEquationCorrect) {
                correctEquationResults.add(inputResult);
            } else {
                incorrectEquations.add(equation);
            }
        }

        result = correctEquationResults.stream().mapToLong(Long::longValue).sum();
        // 2941973818362 - too low
        System.out.println("Result - part 1: " + result); // 2941973819040

        // Part 2
        typesOfOperators = new char[] {'+', '*', '|'};
        for (String equation : incorrectEquations) {
            String[] equationSplit = equation.split(": ");
            long inputResult = Long.parseLong(equationSplit[0]);
            long[] operands = Arrays.stream(equationSplit[1].split(" ")).mapToLong(Long::parseLong).toArray();
            int numberOfOperators = operands.length - 1;
            List<String> allPossibleChainOfOperators = new ArrayList<>();
            generateAllPermutationsWithRepetition(typesOfOperators, numberOfOperators, "", allPossibleChainOfOperators);
            boolean isEquationCorrect = false;
            for (String chainOfOperators : allPossibleChainOfOperators) {
                long equationResult = operands[0];
                for (int i = 0; i < chainOfOperators.length(); i++) {
                    if (chainOfOperators.charAt(i) == '+') {
                        equationResult = equationResult + operands[i + 1];
                    } else if (chainOfOperators.charAt(i) == '*') {
                        equationResult = equationResult * operands[i + 1];
                    } else if (chainOfOperators.charAt(i) == '|') {
                        equationResult = Long.parseLong(String.valueOf(equationResult) + operands[i + 1]);
                    }
                }
                if (equationResult == inputResult) {
                    isEquationCorrect = true;
                }
            }
            if (isEquationCorrect) {
                correctEquationResults.add(inputResult);
            }
        }

        result = correctEquationResults.stream().mapToLong(Long::longValue).sum();
        System.out.println("Result - part 2: " + result); // 249943041417600

        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + " ms");
    }

    private static void generateAllPermutationsWithRepetition(char[] typesOfObjects, int desiredLength, String permutation, List<String> allPermutations) {
        if (permutation.length() >= desiredLength) {
            allPermutations.add(permutation);
        } else {
            for (char objectType : typesOfObjects) {
                generateAllPermutationsWithRepetition(typesOfObjects, desiredLength, permutation + objectType, allPermutations);
            }
        }
    }
}
