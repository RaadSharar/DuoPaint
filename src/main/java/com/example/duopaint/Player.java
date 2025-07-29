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
    @Override
    public String toString() {
        return name + " – Score: " + score;
    }
}

