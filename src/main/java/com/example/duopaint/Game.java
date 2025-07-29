package com.example.duopaint;
import java.io.IOException;
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
                    //System.err.println("still waiting for choosing");
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
            Hint hint = new Hint(guessWord.length());
            try {
                while (timeTaken < 60) {
                    serverToWrite.add(new Message(Message.Type.TIME_REM, null, timeQueue.take().toString()));
                    if (timeTaken == 60 - 60 / 2 && guessWord.length() >= 4) {
                        System.err.println("First hint given");
                        hint.getfirst();
                        System.err.println(hint.hint1);
                        serverToWrite.add(new Message(Message.Type.HINT1, null, hint.hint1 + ""));
                    }
                    if (timeTaken == 60 - 60 / 4 && guessWord.length() >= 7) {
                        System.err.println("Second hint given");
                        hint.getsecond();
                        System.err.println(hint.hint2);
                        serverToWrite.add(new Message(Message.Type.HINT2, null, hint.hint2 + ""));
                    }
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
            clients.get(I).player.score += (int) Math.abs(50 * (clients.size() / 2.0 - guessedCorrectly.size()));
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
        for (var J : clients) {
            try {
                serverToWrite.add(new Message(Message.Type.MENU, null, null));
                Thread.sleep(1000);
            } catch (InterruptedException s) {
                s.printStackTrace();
            }
        }
        serverWrite.interrupt();
        ServerData.fullReset();
    }
}
