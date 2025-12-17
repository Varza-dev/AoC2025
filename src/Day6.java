import java.util.*;

import static util.InputReaderUtils.readInputLines;

public class Day6 {
    public long squidMath() {
        // read and process input
        List<String> inputLines
                = readInputLines("./inputs/day6.txt");
        // so, all the lines are numbers except for the last, which is operations.
        // Make a 2D array of all the numbers and an array of operators

        List<List<Integer>> numbers = new ArrayList<>();
        List<String> operations = new ArrayList<>();
        int row = 0;

        for (String line: inputLines) {
            String cleanLine = line.trim().replaceAll(" +", " ");
            String[] tokens = cleanLine.split(" ");
            try {
                // if it parses, it's a numbers line
                Integer.parseInt(tokens[0]);
                numbers.add(new ArrayList<>());
                for (String token: tokens) {
                    numbers.get(row).add(Integer.parseInt(token));
                }
            } catch(NumberFormatException e) { // if it's not a numbers line, it's the operations line.
                operations.addAll(Arrays.asList(tokens));
            }

            row++;
        }

        long resultPt1 = 0;

        // do the squid math (part 1)
        int col = 0;
        while (col < numbers.getFirst().size()) {
            row = 0;
            long result = operations.get(col).equals("*") ? 1 : 0;
            boolean multiply = operations.get(col).equals("*");
            while (row < numbers.size()) {
                long value = numbers.get(row).get(col);
                if (multiply) {
                    result *= value;
                } else {
                    result += value;
                }
                row++;
            }
            resultPt1 += result;
            col++;
        }
        return resultPt1;
    }

    public long squidMathPt2() {
        List<String> inputLines
                = readInputLines("./AoC2025/inputs/day6.txt");

        // get operation line, it's the last line in the input
        String opsLine = inputLines.get(inputLines.size() - 1);
        String[] opsTokens = opsLine.trim().replaceAll(" +", " ").split(" ");
        List<String> operations = new ArrayList<>(List.of(opsTokens));

        int lineLength = inputLines.getFirst().length();

        int col = lineLength - 1;
        int opIndex = operations.size() - 1;
        long resultPt2 = 0;
        long result = operations.get(opIndex).equals("*") ? 1 : 0;
        boolean multiply = operations.get(opIndex).equals("*");

        while (col >= 0) {
            StringBuilder accumulate = new StringBuilder();
            for (int row = 0; row < inputLines.size() - 1; row++) {
                if (inputLines.get(row).charAt(col)!= ' ') {
                    accumulate.append(inputLines.get(row).charAt(col));
                }
            }

            String numStr = accumulate.toString();
            if (!numStr.isEmpty()) {
                long val = Long.parseLong(numStr);
                if (multiply) {
                    result *= val;
                } else {
                    result += val;
                }
            } else {
                resultPt2 += result;
                opIndex--;
                result = operations.get(opIndex).equals("*") ? 1 : 0;
                multiply = operations.get(opIndex).equals("*");
            }

            col--;
        }
        resultPt2 += result; // add the last result too
        return resultPt2;
    }
}
