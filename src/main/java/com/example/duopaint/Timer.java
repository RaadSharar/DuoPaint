package com.example.duopaint;

public class Timer extends Thread {
    int rem;
    Timer(int rem) {
        this.rem = rem;
        this.start();
    }
    public void run() {
        try {
            while (rem > 0) {
                rem--;
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.err.println("Timer interrupted");
        }
    }
}
