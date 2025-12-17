import java.util.ArrayList;
import java.util.List;

import static util.InputReaderUtils.readInputLines;

public class Day1 {
    List<String> inputLines = new ArrayList<>();

    int[] password() {
        List<String> inputLines =
                readInputLines("./inputs/day1.txt");
        int current = 50;
        int landings = 0;
        int passes = 0;
        for(String line : inputLines) {
            char direction = line.charAt(0);
            line = line.substring(1);
            int distance = Integer.parseInt(line);
            if (direction == 'R') {
                // find the smallest number that makes (current + k) % 100 == 0.
                // if it's 0, we make it 100 as that's what we'd need to pass 0 again
                // we only pass 0 if the distance is greater than it
                int k = (100 - current) % 100;
                if (k == 0) k = 100;
                if (distance >= k) {
                    passes += 1 + (distance - k) / 100;
                }
                current = (current + distance) % 100;
            } else if (direction == 'L') {
                // finding the mallest number that makes (current - k) % 100 == 0
                // it is either current (the start value) or 100 if current is 0, as that
                // is what we'd need to subtract to pass 0 again
                int k = (current == 0) ? 100 : current;
                if (distance >= k) {
                    passes += 1 + (distance - k) / 100;
                }
                int distanceMod = distance % 100;
                current = ((current - distanceMod) + 100) % 100;
            }
            if (current == 0) landings++;
        }
        return new int[]{ landings, passes };
    }
}
