package com.example.dronecontroller.Services.Utils;

import java.nio.ByteBuffer;

public class Bytes {
    public static byte[] intToBytes(final int i) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.putInt(i);
        return bb.array();
    }

    public static byte[] floatToBytes(final float f) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.putFloat(f);
        return bb.array();
    }
}
