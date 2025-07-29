package com.example.duopaint;

import java.io.Serializable;

import static com.example.duopaint.WordFileLoader.words;

public class WordChooser implements Serializable {
    public String forWhom;
    public String[] arr = new String[3];
    public WordChooser(String forWhom) {
        this.forWhom = forWhom;
        arr[0] = words.get((int)(Math.random() * 500) % words.size());
        arr[1] = words.get((int)(Math.random() * 500) % words.size());
        arr[2] = words.get((int)(Math.random() * 500) % words.size());
    }
    public String get(int i) {
        return arr[i];
    }
}
