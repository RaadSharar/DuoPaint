package com.example.duopaint;

import java.io.Serializable;

public class  Round implements Serializable {
    String artist;
    String word;
    public Round(String artist, String word) {
        this.artist = artist;
        this.word = word;
    }
}
