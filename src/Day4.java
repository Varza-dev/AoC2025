import java.util.ArrayList;
import java.util.List;

import static util.InputReaderUtils.readInputLines;

public class Day4 {

    public int[] countRolls() {
        List<String> mapLines
                = readInputLines("./inputs/day4.txt");

        int rows = mapLines.size();
        int cols = mapLines.get(0).length();
        char[][] map = new char[rows][cols];
        char[][] mapCopy = new char[rows][cols];

        int row = 0;
        // build the map
        for (String line: mapLines) {
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i)=='.') {
                    map[row][i] = '.';
                    mapCopy[row][i] = '.';
                } else if (line.charAt(i)=='@') {
                    map[row][i] = '@';
                    mapCopy[row][i] = '@';
                }
            }
            row++;
        }

        int resultPt1 = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (map[r][c] == '@' && adjacentRolls(r, c, map) < 4) {
                    mapCopy[r][c] = 'x';
                    resultPt1++;
                }
            }
        }

        int resultPt2 = 0;

        int removed = -1;
        List<Integer> rs = new ArrayList<>();
        List<Integer> cs = new ArrayList<>();
        while (removed != 0) {
            removed = 0;
            rs.clear();
            cs.clear();
            // make the pass
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (map[r][c] == '@' && adjacentRolls(r, c, map) < 4) {
                        rs.add(r);
                        cs.add(c);
                    }
                }
            }
            for (int i = 0; i < rs.size(); i++) {
                int r = rs.get(i);
                int c = cs.get(i);
                map[r][c] = 'x';
                removed++;
            }
            resultPt2 += removed;
        }

        return new int[]{ resultPt1, resultPt2 };
    }

    private int adjacentRolls(int r, int c, char[][] map) {
        int total = 0;

        int[] rMath = {0, 0, 1, -1, 1, 1, -1, -1};
        int[] cMath = {-1, 1, 0, 0, 1, -1, 1, -1};

        for (int i = 0; i < 8; i++) {
            int newR = r + rMath[i];
            int newC = c + cMath[i];

            if (newR >= 0 && newR < map.length && newC >= 0 && newC < map[0].length) {
                if (map[newR][newC] == '@') {
                    total++;
                }
            }
        }
        return total;
    }

    // this prints the map for debugging purposes
    private void printMap(char[][] map) {
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[0].length; c++) {
                System.out.print(map[r][c]);
            }
            System.out.println();
        }
    }
}
