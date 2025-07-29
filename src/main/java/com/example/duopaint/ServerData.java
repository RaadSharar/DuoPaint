package com.example.duopaint;

import java.net.ServerSocket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerData {
    public static boolean wordChosen = false;
    public static boolean roundRunning = false;
    public static HostBroadcaster broadcaster;
    public static ServerSocket serverSocket;
    public static BlockingQueue<Message> serverToWrite = new LinkedBlockingQueue<>();
    public static List<Client> clients;
    public static GameServer gameServer;
    public static Game currentGame;
    public static HashMap<String, Boolean> areTheyDrawing ;
    public static BlockingQueue<Integer> timeQueue = new LinkedBlockingQueue<>();
    public static int timeTaken;
    public static String guessWord = "wow";
    public static HashSet<String> guessedCorrectly = new HashSet<>();
    public static boolean allguessed;
    public static com.example.duopaint.Timer wow;
    public static ServerWrite serverWrite;
    public static void fullReset() {
        wordChosen = false;
        roundRunning = false;
        if (broadcaster != null) {
            broadcaster = null;
        }
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            serverSocket = null;
        }
        serverToWrite.clear();
        if (clients != null) {
            clients.clear();
            clients = null;
        }

        if (gameServer != null) {
            gameServer = null;
        }

        currentGame = null;

        if (areTheyDrawing != null) {
            areTheyDrawing.clear();
            areTheyDrawing = null;
        }

        timeQueue.clear();
        timeTaken = 0;
        guessWord = "wow";
        guessedCorrectly.clear();
        allguessed = false;

        if (wow != null) {
            wow = null;
        }
    }
}
