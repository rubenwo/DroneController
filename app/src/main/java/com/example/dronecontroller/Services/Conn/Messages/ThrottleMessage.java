package com.example.dronecontroller.Services.Conn.Messages;

import com.example.dronecontroller.Services.Conn.IMessage;
import com.example.dronecontroller.Services.Utils.Bytes;

public class ThrottleMessage implements IMessage {

    private int speed;
    private int angleX, angleY, angleZ;

    public ThrottleMessage(int speed, int angleX, int angleY, int angleZ) {
        this.speed = speed;
        this.angleX = angleX;
        this.angleY = angleY;
        this.angleZ = angleZ;
    }

    @Override
    public byte[] toBytes() {
        byte[] s = Bytes.intToBytes(speed);
        byte[] x = Bytes.intToBytes(angleX);
        byte[] y = Bytes.intToBytes(angleY);
        byte[] z = Bytes.intToBytes(angleZ);

        byte[] data = new byte[s.length + x.length + y.length + z.length];
        System.arraycopy(s, 0, data, 0, s.length);
        System.arraycopy(x, 0, data, s.length, x.length);
        System.arraycopy(y, 0, data, x.length + s.length, y.length);
        System.arraycopy(z, 0, data, y.length + x.length + s.length, z.length);

        return data;
    }
}
