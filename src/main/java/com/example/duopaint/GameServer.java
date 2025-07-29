package com.example.duopaint;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.example.duopaint.ServerData.*;

public class GameServer extends Thread {
    public final int port = 55000;
    public boolean gameStarted = false;
    public GameServer() {
        start();
    }
    @Override
    public void run() {
        ServerData.gameServer = this;
        try {
            clients = Collections.synchronizedList(new ArrayList<>());
            System.err.println("asas");
            serverSocket = new ServerSocket(port);
            System.err.println("Server listening on port " + port);
            Thread acceptClientThread = new Thread(() -> {
                while (!gameStarted) {
                    try {
                        Socket sock = serverSocket.accept();
                        System.err.println("sock = " + sock.getInetAddress().getHostName());
                        var handler = new Client(new SocketWrapper(sock));
                        System.err.println("New client connected from " + sock.getInetAddress().getHostAddress());
                        clients.add(handler);
                        handler.socket.write(new Message(Message.Type.NAME_PROMT, null, null));
                        Message namemsg = (Message) handler.socket.read();
                        handler.player.name = namemsg.sender;
                        System.err.println("New name message from " + namemsg.sender);
                        handler.start();
                        Thread.sleep(50);
                        broadcastPlayerList();
                    } catch (Exception e) {
                        System.err.println("Not accepting");
                        System.err.println(e.getMessage());
                        break;
                    }
                }
            });
            new ServerWrite().start();
            acceptClientThread.start();

        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("Server stopped");
        }
    }

    /** Broadcast the current list of player‑names to all clients */
    public void broadcastPlayerList() {
        try {
            ArrayList<Player> names = new ArrayList<>();
            for (var i : clients) {
                names.add(i.player);
            }
            var msg = new Message(Message.Type.PLAYER_LIST, null, names);
            for (var c : clients) {
                c.socket.write(msg);
            }
        } catch (IOException e) {
            System.err.println("Server stopped");
        }
    }

    /** Remove a handler (e.g. on disconnect) */
    public void removeHandler(Client handler) {
        clients.remove(handler);
        broadcastPlayerList();
    }

    public void startGame() {
        try {
            gameStarted = true;
            serverToWrite.put(new Message(Message.Type.START, null, null));
            currentGame = new Game();
        } catch (Exception e) {
            System.err.println("Start game failed");
        }
    }
}