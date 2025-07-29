package com.example.duopaint;
import java.util.ArrayList;

import static com.example.duopaint.ServerData.*;

public class Game extends Thread {

    public Game() {
        this.start();
    }

    public void run() {
        System.out.println("Game Started");
        for (int i = 0; i < 2 * clients.size(); i++) {
            int I = i % clients.size();
            areTheyDrawing.put(clients.get(I).player.name, true);
            serverToWrite.add(new Message(Message.Type.ROUND_STARTS, null, new Round(clients.get(I).player.name, guessWord)));

            Timer wow = new Timer(60);
            timeTaken = 0;
            try {
                while (timeTaken < 60) {
                    serverToWrite.add(new Message(Message.Type.TIME_REM, null, timeQueue.take().toString()));
                    timeTaken++;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ArrayList<Player> names = new ArrayList<>();
            for (var J : clients) {
                names.add(J.player);
            }
            serverToWrite.add(new Message(Message.Type.ROUND_ENDS, null, null));
            serverToWrite.add(new Message(Message.Type.PLAYER_LIST, null, names));
            areTheyDrawing.put(clients.get(I).player.name, false);
            guessedCorrectly.clear();
        }
    }
}
