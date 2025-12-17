package util;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public final class InputReaderUtils {
    private InputReaderUtils() {
    }

    public static List<String> readInputLines(String path) {
        final List<String> inputLines = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            stream.forEach(inputLines::add);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return inputLines;
    }

    public static List<String> readInputCommaSeparated(String path) {
        final List<String> values = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            stream.forEach(line -> {
                values.addAll(Arrays.asList(line.split(",")));
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return values;
    }
}
