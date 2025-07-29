package com.example.duopaint;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class HostBroadcaster extends Thread {
    private volatile boolean running = true;
    private final int port = 9876;

    @Override
    public void run() {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setBroadcast(true);

            String message = "HOST_AVAILABLE";
            byte[] buffer = message.getBytes();

            DatagramPacket packet = new DatagramPacket(
                    buffer,
                    buffer.length,
                    InetAddress.getByName("255.255.255.255"),
                    port
            );

            while (running) {
                socket.send(packet);
                //System.out.println("Broadcasted HOST_AVAILABLE...");
                //System.out.println(InetAddress.getLocalHost().getHostAddress());
                Thread.sleep(1000); // broadcast every second
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void stopBroadcast() {
        running = false;
    }
}
