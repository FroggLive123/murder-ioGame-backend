package org.mainLogic.entity;

public class User {
    private long timeOfMeasuring;
    private byte RPC;

    public User() {
    }

    public long getTimeOfMeasuring() {
        return timeOfMeasuring;
    }

    public void updateTimeOfMeasuring() {
        timeOfMeasuring = System.currentTimeMillis();
    }

    public byte getRPC() {
        return RPC;
    }

    public void increaseCounter() {
        RPC += RPC;
    }

    public void resetCounter() {
        RPC = 0;
    }
}
