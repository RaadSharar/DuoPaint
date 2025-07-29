package com.example.duopaint;

import java.net.ServerSocket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerData {
    public static HostBroadcaster broadcaster;
    public static ServerSocket serverSocket;
    public static BlockingQueue<Message> serverToWrite = new LinkedBlockingQueue<>();
    public static List<Client> clients;
    public static GameServer gameServer;
    public static Game currentGame;
}
