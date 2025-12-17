import java.util.List;

import static util.InputReaderUtils.readInputLines;

public class Day3 {

    public long[] maxJoltagePart() {
        List<String> batterybanks
                = readInputLines("./inputs/day3.txt");
        long resultPt1 = 0;
        long resultPt2 = 0;
        for(String batteryBank : batterybanks) {
            // get first and second max for part 1
            int firstMaxPos = getMaxIndex(batteryBank, 0, 1);
            int secondMaxPos = getMaxIndex(batteryBank, firstMaxPos + 1, 0);
            String bankJoltage =
                    "" + batteryBank.charAt(firstMaxPos) + batteryBank.charAt(secondMaxPos);
            int joltage = Integer.parseInt(bankJoltage);
            resultPt1 += joltage;

            // get 12 digits for part 2
            StringBuilder accumulate = new StringBuilder();
            int previousMaxPos = -1;
            for (int i = 1; i <= 12; i++) {
                int maxPos = getMaxIndex(batteryBank, previousMaxPos + 1, (12 - i));
                previousMaxPos = maxPos;
                accumulate.append(batteryBank.charAt(maxPos));
            }
            resultPt2 += Long.parseLong(accumulate.toString());
        }
        return new long[]{ resultPt1, resultPt2 };
    }

    private int getMaxIndex(String digits, int startingPosition, int leaveChars) {
        int max = 0;
        int position = -1;
        for (int i = startingPosition; i < digits.length() - leaveChars; i++) {
            // ASCII 48 is "0"
            int currentDigit = digits.charAt(i) - 48;
            if (currentDigit > max) {
                max = currentDigit;
                position = i;
            }
        }
        return position;
    }
}
