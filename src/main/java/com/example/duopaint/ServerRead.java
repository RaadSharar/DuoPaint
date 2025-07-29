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
                switch (message.type) {
                    case Message.Type.START -> {
                        gameServer.startGame();
                    }

                    case Message.Type.DRAW_CMD -> {
                        System.err.println("draw cmd received");
                        serverToWrite.put(message);
                    }

                    case Message.Type.CLEAR_CMD ->  {
                        System.err.println("clear cmd received");
                        serverToWrite.put(message);
                    }

                    case Message.Type.CHAT -> {
                        System.err.println("chat received");
                        if (((String)message.payload).equalsIgnoreCase(guessWord)) {
                            for (int i = 0; i < clients.size(); i++) if (clients.get(i).player.name.equals(message.sender)){
                                clients.get(i).player.score += 200 + (60 - timeTaken) * 5;
                                System.err.println(clients.get(i).player.score + " " + clients.get(i).player.name);
                                break;
                            }
                            ArrayList<Player> names = new ArrayList<>();
                            for (var J : clients) {
                                names.add(J.player);
                            }
                            serverToWrite.add(new Message(Message.Type.PLAYER_LIST, null, names));
                            serverToWrite.put(new Message(Message.Type.CHAT, null, message.sender + " has guessed the word"));
                        } else {
                            serverToWrite.put(message);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Client disconnected: " + socketWrapper.getSocket().getInetAddress());
        }
    }
}
