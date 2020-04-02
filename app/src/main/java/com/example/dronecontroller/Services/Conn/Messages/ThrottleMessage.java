package com.example.dronecontroller.Services.Conn.Messages;

import com.example.dronecontroller.Services.Conn.IMessage;
import com.example.dronecontroller.Services.Utils.Bytes;

public class ThrottleMessage implements IMessage {

    private int motor0;
    private int motor1;
    private int motor2;
    private int motor3;

    public ThrottleMessage(int motor0, int motor1, int motor2, int motor3) {
        this.motor0 = motor0;
        this.motor1 = motor1;
        this.motor2 = motor2;
        this.motor3 = motor3;
    }

    @Override
    public byte[] toBytes() {
        byte[] data = Bytes.intToBytes(motor0);
        return data;
    }
}
