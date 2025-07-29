package com.example.duopaint;

import java.io.Serializable;

public class Player implements Serializable {
    String name = "";
    int score;
    public boolean isHost() {
        return isHost;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    boolean isHost;
    public Player(boolean isHost) {
        this.isHost = isHost;
    }
    public Player(boolean isHost, String name, int score) {
        this.isHost = isHost;
        this.name = name;
        this.score = score;
    }
    @Override
    public String toString() {
        return name + " – Score: " + score;
    }

    public Player(Player other) {
        this.isHost = other.isHost;
        this.name   = other.name;
        this.score  = other.score;
    }
}

