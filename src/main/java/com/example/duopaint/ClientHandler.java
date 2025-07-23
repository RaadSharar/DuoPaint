package com.example.duopaint;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientHandler extends Thread {
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println("Welcome to the game!");

            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("Received from client: " + line);
                // echo or handle commands
                // broadcast to others if needed
            }

        } catch (IOException e) {
            System.out.println("Client disconnected: " + socket.getInetAddress());
        }
    }
}
