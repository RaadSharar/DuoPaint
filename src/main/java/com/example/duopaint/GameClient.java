package com.example.duopaint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class GameClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("192.168.0.101", 12345); // use hostIP
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to server.");
            System.out.println("Server says: " + in.readLine());

            while (true) {
                String msg = scanner.nextLine();
                out.println(msg);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}