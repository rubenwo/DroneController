package com.example.dronecontroller.Services.Utils;

import com.example.dronecontroller.Services.Conn.IMessage;

public class MessageSerializer {
    /**
     * @param message
     * @return
     */
    public static byte[] serialize(IMessage message) {
        return message.toBytes();
    }

    /**
     * @param data
     * @param compressed
     * @return
     */
    public static IMessage deserialize(byte[] data, boolean compressed) {

        return null;
    }
}
