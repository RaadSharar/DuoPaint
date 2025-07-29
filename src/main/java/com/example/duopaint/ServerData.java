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
}
