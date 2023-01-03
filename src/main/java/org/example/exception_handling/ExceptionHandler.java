package org.example.exception_handling;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ExceptionHandler {
    private final Map<Pair<String, Integer>, Map<Pair<String, Integer>, String>> decisionTree;

    public ExceptionHandler(final Path config) throws IOException {
        decisionTree = new HashMap<>();
        final List<String> lines = Files.readAllLines(config);

        for (final String line : lines) {
            try (Scanner scanner = new Scanner(line)) {
                // init first level
                String filename = scanner.next();
                int number = scanner.nextInt();
                Pair<String, Integer> key = new Pair<>(filename, number);

                decisionTree.put(key, new HashMap<>());

                // init subtree
                String exception = scanner.next();
                int hash = scanner.nextInt();
                String message = scanner.nextLine();

                decisionTree.get(key).put(new Pair<>(exception, hash), message);
            }
        }
    }

    public void handle(final Exception e) {
        final StackTraceElement[] stackTrace = e.getStackTrace();
        int number = stackTrace[0].getLineNumber();
        int hash = Arrays.hashCode(stackTrace);

        final String filename = stackTrace[0].getFileName();
        final String exception = e.getClass().getSimpleName();
        final String message = decisionTree
                .getOrDefault(new Pair<>(filename, number), new HashMap<>())
                .getOrDefault(new Pair<>(exception, hash), "Unknown error");

        System.err.printf(
                "Exception %s, " +
                        "line %d, " +
                        "file %s, " +
                        "stack trace hash %d:" +
                        " %s.%n",
                exception,
                number,
                filename,
                hash,
                message);
    }
}
