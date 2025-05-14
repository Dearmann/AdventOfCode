package Day5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day5 {
    public static void main(String[] args) throws FileNotFoundException {
        // Input
        Scanner scanner = new Scanner(new File("2024/Day5/rules.txt"));
        Map<String,List<String>> rules = new HashMap<>();
        while (scanner.hasNextLine()) {
            String[] rule = scanner.nextLine().split("\\|");
            if (rules.containsKey(rule[0])) {
                List<String> pagesAfter = rules.get(rule[0]);
                pagesAfter.add(rule[1]);
                rules.put(rule[0], pagesAfter);
            } else {
                List<String> pagesAfter = new ArrayList<>();
                pagesAfter.add(rule[1]);
                rules.put(rule[0], pagesAfter);
            }
        }
        System.out.println(rules);
        scanner = new Scanner(new File("2024/Day5/pages.txt"));
        List<String> pagesList = new ArrayList<>();
        while (scanner.hasNextLine()) {
            pagesList.add(scanner.nextLine());
        }
        System.out.println(pagesList);
        scanner.close();

        // Part 1
        List<String> correctPagesList = new ArrayList<>(pagesList);
        Set<String> incorrectPagesList = new HashSet<>();
        for (String pages : pagesList) {
            String[] pagesSplit = pages.split(",");
            for (int i = 0; i < pagesSplit.length; i++) {
                if (rules.containsKey(pagesSplit[i])) {
                    List<String> pagesAfter = rules.get(pagesSplit[i]);
                    for (int j = 0; j < i; j++) {
                        if (pagesAfter.contains(pagesSplit[j])) {
                            incorrectPagesList.add(pages);
                            correctPagesList.remove(pages);
                        }
                    }
                }
            }
        }
        System.out.println("Correct pages: " + correctPagesList);

        int result = 0;
        for (String pages : correctPagesList) {
            String[] pagesSplit = pages.split(",");
            result += Integer.parseInt(pagesSplit[pagesSplit.length / 2]);
        }
        System.out.println("Result - part 1: " + result); // 4689

        // Part 2
        System.out.println("Incorrect pages: " + incorrectPagesList);
        ArrayList<String> fixedPagesList = new ArrayList<>();
        for (String pages : incorrectPagesList) {
            List<String> pagesSplit = Arrays.stream(pages.split(",")).toList();
            List<String> fixedPagesSplit = new ArrayList<>(pagesSplit);
            for (int i = pagesSplit.size() - 1; i >= 0; i--) {
                if (rules.containsKey(pagesSplit.get(i))) {
                    List<String> pagesAfterRules = rules.get(pagesSplit.get(i));
                    for (int j = 0; j < i; j++) {
                        if (pagesAfterRules.contains(pagesSplit.get(j))) {
                            String incorrectPage = pagesSplit.get(j);
                            fixedPagesSplit.remove(incorrectPage);

                            List<String> testingPages = new ArrayList<>(fixedPagesSplit);
                            int i1 = testingPages.indexOf(pagesSplit.get(i));
                            testingPages.add(i1, incorrectPage);
                            int offset = 0;
                            while (!correctPagesCombinationOfSplit(testingPages.subList(i1, pagesSplit.size()), rules) && (i1 + offset) != pagesSplit.size() - 1) {
                                testingPages.remove(incorrectPage);
                                offset++;
                                testingPages.add(i1 + offset, incorrectPage);
                            }
                            fixedPagesSplit.add(i1 + offset, incorrectPage);
                        }
                    }
                }
            }
            fixedPagesList.add(String.join(",", fixedPagesSplit));
        }
        System.out.println("Fixed pages: " + fixedPagesList);

        int resultPartTwo = 0;
        for (String pages : fixedPagesList) {
            String[] pagesSplit = pages.split(",");
            resultPartTwo += Integer.parseInt(pagesSplit[pagesSplit.length / 2]);
        }
        // 5630 - too low
        // 6111 - too low
        // 6140 - too low
        System.out.println("Result - part 2: " + resultPartTwo); // 6336
    }

    private static boolean correctPagesCombinationOfSplit(List<String> pagesSplit, Map<String,List<String>> rules) {
        for (int i = 0; i < pagesSplit.size(); i++) {
            if (rules.containsKey(pagesSplit.get(i))) {
                List<String> pagesAfter = rules.get(pagesSplit.get(i));
                for (int j = 0; j < i; j++) {
                    if (pagesAfter.contains(pagesSplit.get(j))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
