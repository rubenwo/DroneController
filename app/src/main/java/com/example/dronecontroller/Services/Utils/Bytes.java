package com.example.dronecontroller.Services.Utils;

import java.nio.ByteBuffer;

public class Bytes {
    public static byte[] intToBytes(final int i) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.putInt(i);
        return bb.array();
    }
}
