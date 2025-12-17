import java.util.List;

import static util.InputReaderUtils.readInputCommaSeparated;

public class Day2 {
    long[] sumInvalidIds() {
        List<String> idRanges
                = readInputCommaSeparated("./inputs/day2.txt");
        long part1 = 0;
        long part2 = 0 ;
        for (String range: idRanges) {
            String[] rangeNumbers = range.split("-");
            String start = rangeNumbers[0];
            String end = rangeNumbers[1];

            String current = start;
            while (!current.equals(end)) {
                // check current

                // Part 1 - numbers comprised ONLY of two repeating digit sequences
                if (current.length() % 2 == 0) {
                    int mid = current.length() / 2;
                    String[] parts = { current.substring(0, mid), current.substring(mid) };
                    if (parts[0].equals(parts[1])) {
                        long num = Long.parseLong(current);
                        part1 += num;
                    }
                }

                // Part 2 - numbers comprised of sequences repeated AT LEAST twice,
                // sequence can have any length
                for (int len = 1; len <= current.length()/2; len++) {
                    boolean isInvalid = true;
                    String seq = current.substring(0, len);
                    int i = 0;

                    for (; i < current.length()/seq.length() && (i+1) * len < current.length(); i++) {
                        String comp = current.substring(i * len, (i + 1) * len);
                        if (!seq.equals(comp)) {
                            isInvalid = false;
                            break;
                        }
                    }

                    String comp = current.substring(i * len);

                    if (!seq.equals(comp)) {
                        isInvalid = false;
                    }

                    if (isInvalid) {
                        long num = Long.parseLong(current);
                        part2 += num;
                        break;
                    }
                }
                current = increment(current);
            }
        }
        return new long[]{ part1, part2 };
    }

    String increment(String number) {
        long num = Long.parseLong(number);
        num++;
        return Long.toString(num);
    }
}
