package Day7;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;

public class Day7BigDecimal {
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
        BigDecimal result = BigDecimal.ZERO;
        List<BigDecimal> correctEquationResults = new ArrayList<>();
        char[] typesOfOperators = new char[] {'+', '*'};
        for (String equation : input) {
            String[] equationSplit = equation.split(": ");
            BigDecimal inputResult = new BigDecimal(equationSplit[0]);
            String[] operandsString = equationSplit[1].split(" ");
            List<BigDecimal> operands = new ArrayList<>();
            for (String operand : operandsString) {
                operands.add(new BigDecimal(operand));
            }
            int numberOfOperators = operands.size() - 1;
            List<String> allPossibleChainOfOperators = new ArrayList<>();
            generateAllPermutationsWithRepetition(typesOfOperators, numberOfOperators, "", allPossibleChainOfOperators);
            boolean isEquationCorrect = false;
            for (String chainOfOperators : allPossibleChainOfOperators) {
                BigDecimal equationResult = operands.getFirst();
                for (int i = 0; i < chainOfOperators.length(); i++) {
                    if (chainOfOperators.charAt(i) == '+') {
                        equationResult = equationResult.add(operands.get(i + 1));
                    } else if (chainOfOperators.charAt(i) == '*') {
                        equationResult = equationResult.multiply(operands.get(i + 1));
                    }
                }
                if (Objects.equals(equationResult, inputResult)) {
                    isEquationCorrect = true;
                }
            }
            if (isEquationCorrect) {
                correctEquationResults.add(inputResult);
            }
        }

        System.out.println(correctEquationResults);
        for (BigDecimal correctResult : correctEquationResults) {
            result = result.add(correctResult);
        }

        // 2941973818362 - too low
        System.out.println("Result - part 1: " + result); // 2941973819040

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
