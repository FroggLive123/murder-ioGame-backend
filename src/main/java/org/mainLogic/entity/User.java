package org.mainLogic.entity;

public class User {
    private long timeOfMeasuring;
    private byte RPS;

    public User() {
    }

    public long getTimeOfMeasuring() {
        return timeOfMeasuring;
    }

    public void updateTimeOfMeasuring() {
        timeOfMeasuring = System.currentTimeMillis();
    }
    //Todo Fetch updateTimeOfMeasuring and resetCounter ( NextChunk)

    public byte getRPS() {
        return RPS;
    }

    public void increaseCounter() {
        RPS += RPS;
    }

    public void resetCounter() {
        RPS = 0;
    }
}
