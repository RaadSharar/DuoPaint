package com.example.duopaint;

import java.util.ArrayList;

import static com.example.duopaint.ServerData.*;

class ServerRead extends Thread {

    private SocketWrapper socketWrapper;

    public ServerRead(SocketWrapper socketWrapper) {
        this.socketWrapper = socketWrapper;
    }
    @Override
    public void run() {
        try  {
            while (true) {
                Message message = (Message) socketWrapper.read();
                label : switch (message.type) {
                    case Message.Type.START -> {
                        gameServer.startGame();
                    }

                    case Message.Type.DRAW_CMD -> {
                        //System.err.println("draw cmd received");
                        serverToWrite.put(message);
                    }

                    case Message.Type.CLEAR_CMD ->  {
                        //System.err.println("clear cmd received");
                        serverToWrite.put(message);
                    }

                    case Message.Type.CHAT -> {
                        System.err.println("chat received");
                        if (((String)message.payload).equalsIgnoreCase(guessWord) && roundRunning == true) {
                            System.err.println("guess word received");
                            for (int i = 0; i < clients.size(); i++) if (clients.get(i).player.name.equals(message.sender)){
                                if (!guessedCorrectly.contains(clients.get(i).player.name)) {
                                    guessedCorrectly.add(clients.get(i).player.name);
                                    clients.get(i).player.score += 200 + (60 - timeTaken) * 5;
                                    System.err.println(clients.get(i).player.score + " " + clients.get(i).player.name);
                                } else {
                                    break  label;
                                }
                                break;
                            }
                            if (guessedCorrectly.size() == clients.size() - 1) {
                                allguessed = true;
                            }
                            ArrayList<Player> names = new ArrayList<>();
                            for (var J : clients) {
                                names.add(new Player(J.player.isHost, J.player.name, J.player.score));
                            }
                            serverToWrite.add(new Message(Message.Type.PLAYER_LIST, null, names));
                            serverToWrite.put(new Message(Message.Type.CHAT, null, message.sender + " has guessed the word"));
                        } else {
                            serverToWrite.put(message);
                        }
                    }

                    case WORD_CHOSEN -> {
                        wordChosen = true;
                        guessWord = (String) message.payload;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Client disconnected: " + socketWrapper.getSocket().getInetAddress());
        }
    }
}
