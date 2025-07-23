package com.example.duopaint;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;

public class HostListener extends Thread {
    private final int port = 9876;

    @Override
    public void run() {
        try (DatagramSocket socket = new DatagramSocket(port)) {
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            while (true) {
                socket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength());

                if ("HOST_AVAILABLE".equals(message)) {
                    String hostIP = packet.getAddress().getHostAddress();
                    System.out.println("Host found at: " + hostIP);
                    // Here, notify your UI or logic that host is available
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}