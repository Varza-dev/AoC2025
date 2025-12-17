import java.util.*;

import static util.InputReaderUtils.readInputLines;

public class Day5 {
    public static class Range implements Comparable<Range> {
        long start;
        long end;

        public Range(long start, long end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Range otherRange) {
            return Long.compare(this.start, otherRange.start);
        }
    }

    long[] countFresh() {
        // read and process input
        List<String> inputLines
                = readInputLines("./inputs/day5.txt");

        List<Range> ranges = new ArrayList<>();
        List<Long> values = new ArrayList<>();
        String line = inputLines.get(0);
        int index = 1;
        while (!line.isBlank()) {
            // it's all ranges up to the blank line
            String[] rangeValues = line.split("-");
            long start = Long.parseLong(rangeValues[0]);
            long end = Long.parseLong(rangeValues[1]);
            ranges.add(new Range(start, end));

            line = inputLines.get(index);
            index++;
        }

        // process values
        while (index < inputLines.size()) {
            long value = Long.parseLong(inputLines.get(index));
            values.add(value);
            index++;
        }

        // sort ranges by start
        Collections.sort(ranges);
        Collections.sort(values);

        int rangeIndex = 0;
        int valueIndex = 0;
        long resultPt1 = 0;
        long resultPt2 = 0;

        while (rangeIndex < ranges.size() && valueIndex < values.size()) {
            long start = ranges.get(rangeIndex).start;
            long end = ranges.get(rangeIndex).end;
            // skip the values in between
            while (values.get(valueIndex) < start) {
                valueIndex++;
            }

            while (values.get(valueIndex) >= start && values.get(valueIndex) <= end) {
                resultPt1++;
                valueIndex++;
            }
            rangeIndex++;
        }


        // for part 2, we merge the ranges to get a list of non-overlapping ranges,
        // then we calculate the number of values in between

        List<Range> mergedRanges = new ArrayList<>();
        // add the first range to merged ranges
        mergedRanges.add(ranges.getFirst());
        int mergedRangesIndex = 0;
        for (int i = 1; i < ranges.size(); i++) {
            Range lastMerged = mergedRanges.getLast();
            Range currentRange = ranges.get(i);

            if (currentRange.start <= lastMerged.end) {
                mergedRanges.get(mergedRangesIndex).end = Math.max(lastMerged.end, currentRange.end);
            } else {
                mergedRanges.add(new Range(currentRange.start, currentRange.end));
                mergedRangesIndex++;
            }
        }

        for (Range mergedRange : mergedRanges) {
            resultPt2 += (mergedRange.end - mergedRange.start) + 1;
        }

        return new long[]{ resultPt1, resultPt2 };
    }
}
