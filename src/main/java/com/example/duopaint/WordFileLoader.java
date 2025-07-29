package com.example.duopaint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class WordFileLoader {
    public static List<String> words = new ArrayList<>();

    private WordFileLoader() {

    }
    public static void loadFromResource(String resourceName) throws IOException {
        if (!words.isEmpty()) {
            return; // already loaded
        }
        String res = resourceName.startsWith("/") ? resourceName : "/" + resourceName;
        InputStream is = WordFileLoader.class.getResourceAsStream(res);
        if (is == null) {
            throw new IOException("Resource not found: " + resourceName);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (!trimmed.isEmpty()) {
                    words.add(trimmed);
                }
            }
        }
    }

    public static void loadFromFile(Path path) throws IOException {
        if (!words.isEmpty()) {
            return; // already loaded
        }
        List<String> fileWords = Files.lines(path, StandardCharsets.UTF_8)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
        words.addAll(fileWords);
    }

    public static List<String> getWords() {
        return Collections.unmodifiableList(words);
    }

    public static void reset() {
        words.clear();
    }
}
