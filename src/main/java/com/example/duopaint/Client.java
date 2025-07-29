package com.example.duopaint;

import java.io.IOException;
import java.net.Socket;

public class Client extends Thread {
    public Player player;
    public SocketWrapper socket;
    public ServerRead serverRead;
    public Client(SocketWrapper socket) {
        this.socket = socket;
        this.player = new Player(StaticData.isHost == 1);
    }
    public void run() {
        serverRead = new ServerRead(socket);
        serverRead.start();
    }
}
