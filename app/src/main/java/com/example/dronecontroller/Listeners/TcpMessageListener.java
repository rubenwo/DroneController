package com.example.dronecontroller.Listeners;

import com.example.dronecontroller.Services.Conn.IMessage;

public interface TcpMessageListener {
    void onMessageReceived(IMessage message);
}
