package com.example.duopaint;

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
                }
            }
        } catch (Exception e) {
            System.out.println("Client disconnected: " + socketWrapper.getSocket().getInetAddress());
        }
    }
}
