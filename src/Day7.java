import java.util.List;

import static util.InputReaderUtils.readInputLines;

public class Day7 {
    public int beamSplitting() {
        List<String> inputLines
                = readInputLines("./inputs/day7.txt");

        // build the map of dots and splitters
        char[][] map = new char[inputLines.size()][inputLines.getFirst().length()];
        int row = 0;
        for (String line: inputLines) {
            for (int i = 0; i < line.length(); i++) {
                map[row][i] = line.charAt(i);
            }
            row++;
        }

        int part1 = 0;
        int part2 = 0;

        // new array to keep track of timeline counts, size = number of columns
        int cols = map[0].length;
        int counts[] = new int[cols];
        int startCol = inputLines.get(0).indexOf('S');

        for (row = 1; row < map.length; row++) {
            int[] next = new int[cols];
            String currentLine = inputLines.get(row);

            for (int col = 0; col < map[row].length; col++) {
                if (map[row][col] =='.'
                        &&
                        (map[row - 1][col] == 'S' || map[row-1][col] == '|')) {
                    map[row][col] = '|';
                } else {
                    // a splitter
                    if (map[row][col]=='^') {
                        // a beam is split only if it enters the splitter
                        if (map[row - 1][col]=='|') {
                            if (col - 1 >= 0) {
                                if (map[row][col-1] == '.') {
                                    map[row][col - 1] = '|';
                                }
                            }

                            if (col + 1 < map[row].length) {
                                if (map[row][col + 1] == '.') {
                                    map[row][col + 1] = '|';
                                }
                            }
                            part1++;
                        }
                    }
                }

                // part 2 computations
                int numBeams = counts[col];
                if (numBeams == 0) continue;
                char cell = map[row][col];

                if (cell == '^') {
                    if (col -1 >= 0) {
                        next[col-1] += numBeams;
                    }
                    if (col+1 < cols) {
                        next[col+1] = numBeams;
                    }
                } else {
                    next[col] += numBeams;
                }
            }
            counts = next;
        }

        return part1;
    }


    public static long countBeamsPart2() {
        List<String> grid
                = readInputLines("./inputs/day7.txt");

        int rows = grid.size();
        int cols = grid.get(0).length();

        // Find starting point
        int startCol = grid.get(0).indexOf('S');

        long[] counts = new long[cols];
        counts[startCol] = 1;

        for (int row = 1; row < rows; row++) {
            long[] next = new long[cols];
            String line = grid.get(row);

            for (int col = 0; col < cols; col++) {
                long numBeams = counts[col];
                if (numBeams == 0) continue;

                char cell = line.charAt(col);

                // splitter
                if (cell == '^') {
                    // Split the beam to the left and right. The original beam "disappears"
                    if (col - 1 >= 0) {
                        next[col - 1] += numBeams;
                    }
                    if (col + 1 < cols) {
                        next[col + 1] += numBeams;
                    }
                } else {
                    next[col] += numBeams;
                }
            }

            counts = next;
        }

        long totalTimelines = 0;
        for (long count : counts) totalTimelines += count;
        return totalTimelines;
    }

    /**
     * Prints the grid - for debugging purposes
     */
    private void printMap(char[][] map) {
        for (int row = 0; row < map.length; row++) {
            System.out.println();
            for (int col = 0; col < map[row].length; col++) {
                System.out.print(map[row][col]);
            }
        }
    }
}
