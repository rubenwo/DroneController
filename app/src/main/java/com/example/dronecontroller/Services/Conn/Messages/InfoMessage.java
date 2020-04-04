package com.example.dronecontroller.Services.Conn.Messages;

import com.example.dronecontroller.Services.Conn.IMessage;

import java.nio.ByteBuffer;

public class InfoMessage implements IMessage {

    private int angleX;
    private int angleY;
    private int angleZ;

    private int temperature;

    private int battery_level;

    private int motor_0_speed;
    private int motor_1_speed;
    private int motor_2_speed;
    private int motor_3_speed;


    public InfoMessage(int angleX, int angleY, int angleZ, int temperature, int battery_level, int motor_0_speed, int motor_1_speed, int motor_2_speed, int motor_3_speed) {
        this.angleX = angleX;
        this.angleY = angleY;
        this.angleZ = angleZ;
        this.temperature = temperature;
        this.battery_level = battery_level;
        this.motor_0_speed = motor_0_speed;
        this.motor_1_speed = motor_1_speed;
        this.motor_2_speed = motor_2_speed;
        this.motor_3_speed = motor_3_speed;
    }

    public int getAngleX() {
        return angleX;
    }

    public int getAngleY() {
        return angleY;
    }

    public int getAngleZ() {
        return angleZ;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getBattery_level() {
        return battery_level;
    }

    public int getMotor_0_speed() {
        return motor_0_speed;
    }

    public int getMotor_1_speed() {
        return motor_1_speed;
    }

    public int getMotor_2_speed() {
        return motor_2_speed;
    }

    public int getMotor_3_speed() {
        return motor_3_speed;
    }

    public static InfoMessage fromBytes(byte[] data) {
        return new InfoMessage(
                ByteBuffer.wrap(data, 0, 4).getInt(),
                ByteBuffer.wrap(data, 4, 4).getInt(),
                ByteBuffer.wrap(data, 8, 4).getInt(),
                ByteBuffer.wrap(data, 12, 4).getInt(),
                ByteBuffer.wrap(data, 16, 4).getInt(),
                ByteBuffer.wrap(data, 20, 4).getInt(),
                ByteBuffer.wrap(data, 24, 4).getInt(),
                ByteBuffer.wrap(data, 28, 4).getInt(),
                ByteBuffer.wrap(data, 32, 4).getInt()
        );
    }

    @Override
    public byte[] toBytes() {
        return new byte[0];
    }

    @Override
    public String toString() {
        return "InfoMessage{" +
                "angleX=" + angleX +
                ", angleY=" + angleY +
                ", angleZ=" + angleZ +
                ", temperature=" + temperature +
                ", battery_level=" + battery_level +
                ", motor_0_speed=" + motor_0_speed +
                ", motor_1_speed=" + motor_1_speed +
                ", motor_2_speed=" + motor_2_speed +
                ", motor_3_speed=" + motor_3_speed +
                '}';
    }
}
