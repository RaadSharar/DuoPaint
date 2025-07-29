package com.example.duopaint;

import java.io.*;
import java.nio.file.*;

public class CombineJavaFiles {
    public static void main(String[] args) throws IOException {
        Files.walk(Paths.get("."))
                .filter(p -> p.toString().endsWith(".java"))
                .forEach(p -> {
                    try {
                        System.out.println("\n--- FILE: " + p.toString() + " ---\n");
                        Files.lines(p).forEach(System.out::println);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }
}