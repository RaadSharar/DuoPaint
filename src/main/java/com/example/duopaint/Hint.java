package com.example.duopaint;

import java.io.Serializable;

public class Hint implements Serializable {
    public int hint1, hint2, length;
    public Hint(int length) {
        hint1 = -1;
        hint2 = -1;
        this.length = length;
    }
    public void getfirst() {
        if (length >= 4) {
            hint1 = (int) (Math.random() * length) % length;
            while (ServerData.guessWord.charAt(hint1) == ' ') {
                hint1 = (int) (Math.random() * length) % length;
            }
        }
    }
    public void getsecond() {
        if (length >= 7) {
            hint2 = (int) (Math.random() * length) % length;
            while (ServerData.guessWord.charAt(hint2) == ' ' || hint2 == hint1) {
                hint2 = (int) (Math.random() * length) % length;
            }
        }
    }
}
