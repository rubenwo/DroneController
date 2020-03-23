package com.example.dronecontroller.services;

import java.io.IOException;
import java.net.Socket;

public class Connection {

    private Socket socket;
    private boolean running;
    private Thread recv_thread;
    private volatile static Connection instance;

    public static Connection getInstance() throws IOException {
        if (instance == null)
            instance = new Connection();
        return instance;
    }

    private Connection() throws IOException {
        socket = new Socket("192.168.4.1", 1337);
        running = true;

        recv_thread = new Thread(recv());
        recv_thread.run();
    }

    public void disconnect() throws IOException {
        running = false;
        if (recv_thread.isAlive())
            try {
                recv_thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        socket.close();
    }

    public void send(byte[] data) throws IOException {
        socket.getOutputStream().write(data);
    }

    private Runnable recv() throws IOException {
        return () -> {
            while (running) {

            }
        };
    }
}
