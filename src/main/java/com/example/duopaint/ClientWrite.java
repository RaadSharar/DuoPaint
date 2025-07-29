package com.example.duopaint;

import java.io.IOException;
import static com.example.duopaint.StaticData.*;

public class ClientWrite extends Thread {
    public SocketWrapper socketWrapper;
    public ClientWrite(SocketWrapper socketWrapper) throws IOException {
        this.socketWrapper = socketWrapper;
        this.start();
    }
    public void run() {
        try {
            while (true) {
                Message msg = toWrite.take();
                socketWrapper.write(msg);
                System.out.println(msg.type.toString());
                System.out.println("written to the server");
            }
        } catch (InterruptedException e) {
            System.out.println(" ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
