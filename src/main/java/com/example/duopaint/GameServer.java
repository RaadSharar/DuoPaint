package com.example.duopaint;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameServer extends Thread {
    private final int port = 55000;
    private final List<Socket> clients = Collections.synchronizedList(new ArrayList<>());
    private ServerSocket serverSocket;

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Game server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept(); // BLOCKING
                clients.add(clientSocket);
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                // Optional: handle per-client logic in its own thread
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Socket> getClients() {
        return clients;
    }

    public void shutdown() {
        try {
            serverSocket.close();
            for (Socket s : clients) s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
