package com.example.duopaint;

import static com.example.duopaint.ServerData.*;

class ServerWrite extends Thread {
    @Override
    public void run() {
        try  {
            while (true) {
                Message msg = serverToWrite.take();
                for (Client client : clients) {
                    client.socket.write(msg);
                }
            }
        } catch (Exception e) {
            System.out.println("server couldnt write");
        }
    }
}
