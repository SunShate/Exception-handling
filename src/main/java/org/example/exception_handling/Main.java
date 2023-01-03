package org.example.exception_handling;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Objects;

public class Main {
    public static void main(final String[] args) {
        try {
            ExceptionHandler exceptionHandler = new ExceptionHandler(
                    Path.of(Objects.requireNonNull(
                            Main.class.getClassLoader().getResource("config.txt")
                    ).toURI())
            );

            try {
                throw new IllegalArgumentException();
            } catch (Exception e) {
                exceptionHandler.handle(e);
            }

            try {
                throw new NullPointerException();
            } catch (Exception e) {
                exceptionHandler.handle(e);
            }

            try {
                throw new IndexOutOfBoundsException();
            } catch (Exception e) {
                exceptionHandler.handle(e);
            }

            try {
                throw new NoSuchFileException("aboba.xml");
            } catch (Exception e) {
                exceptionHandler.handle(e);
            }

        } catch (IOException | URISyntaxException e) {
            System.err.println("file config.txt not found in resources");
            System.exit(1);
        }
    }
}
