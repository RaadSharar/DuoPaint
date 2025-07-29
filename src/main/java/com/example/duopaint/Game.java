package com.example.duopaint;
import java.util.ArrayList;
import java.util.Objects;

import static com.example.duopaint.ServerData.*;

public class Game extends Thread {

    int rounds;
    public Game(int rounds) {
        this.start();
        this.rounds = rounds;
    }

    public void run() {

        System.err.println("Game Started");
        roundRunning = true;
        for (int i = 0; i < rounds * clients.size(); i++) {
            int I = i % clients.size();
            if (clients.get(I).socket.getSocket().isClosed()) {
                continue;
            }

            WordChooser chooser = new WordChooser(clients.get(I).player.name);
            serverToWrite.add(new Message(Message.Type.WORD_CHOSEN, null, chooser));
            try {
                while (!wordChosen) {
                    Thread.sleep(50);
                    System.err.println("still waiting for choosing");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            wow = new Timer(60);
            allguessed = false;
            timeQueue.clear();
            areTheyDrawing.put(clients.get(I).player.name, true);
            serverToWrite.add(new Message(Message.Type.ROUND_STARTS, null, new Round(clients.get(I).player.name, guessWord)));
            timeTaken = 0;
            try {
                while (timeTaken < 60) {
                    serverToWrite.add(new Message(Message.Type.TIME_REM, null, timeQueue.take().toString()));
                    timeTaken++;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.err.println(i + "th round finished");
            ArrayList<Player> names = new ArrayList<>();
            for (var J : clients) {
                names.add(new Player(J.player));

            }
            serverToWrite.add(new Message(Message.Type.CLEAR_CMD, null, null));
            serverToWrite.add(new Message(Message.Type.ROUND_ENDS, null, null));
            serverToWrite.add(new Message(Message.Type.PLAYER_LIST, null, names));
            areTheyDrawing.put(clients.get(I).player.name, false);
            guessedCorrectly.clear();
            wordChosen = false;
        }
        roundRunning = false;
        ArrayList<Player> names = new ArrayList<>();
        for (var J : clients) {
            names.add(new Player(J.player));
        }
        serverToWrite.add(new Message(Message.Type.PLAYER_LIST, null, names));
        serverToWrite.add(new Message(Message.Type.RESULT, null, null));
    }
}
