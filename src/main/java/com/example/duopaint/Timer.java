package com.example.duopaint;

import static com.example.duopaint.ServerData.timeQueue;
import static com.example.duopaint.ServerData.*;

public class Timer extends Thread {
    int rem;
    Timer(int rem) {
        this.rem = rem;
        this.start();
    }
    public void run() {
        try {
            while (rem > 0) {
                timeQueue.add(rem);
                rem--;
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.err.println("Timer interrupted");
        }
    }
}
