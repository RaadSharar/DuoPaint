package com.example.duopaint;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketWrapper {
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public SocketWrapper(String s, int port) throws IOException { // used by the client
        System.out.println("Connecting to " + s);
        this.socket = new Socket(s, port);
        //System.out.println("Connected to " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
        System.out.println("trying to connect to " + s);
        oos = new ObjectOutputStream(socket.getOutputStream());
        oos.flush();
        ois = new ObjectInputStream(socket.getInputStream());
        System.out.println("Connected to " + socket.getInetAddress() + ":" + socket.getPort());
    }

    public SocketWrapper(Socket s) throws IOException { // used by the server
        this.socket = s;
        oos = new ObjectOutputStream(socket.getOutputStream());
        oos.flush();
        ois = new ObjectInputStream(socket.getInputStream());

    }

    public Object read() throws IOException, ClassNotFoundException {
        return ois.readUnshared();
    }

    public void write(Object o) throws IOException {
        oos.writeUnshared(o);
        oos.flush();
    }

    public void closeConnection() throws IOException {
        ois.close();
        oos.close();
    }

    public Socket getSocket() {
        return socket;
    }
}