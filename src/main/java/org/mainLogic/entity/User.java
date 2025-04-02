package org.mainLogic.entity;

public class User {
    private final short id;
    private long timeOfMeasuring;

    public User(short id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public long getTimeOfMeasuring() {
        return timeOfMeasuring;
    }

    public void updateTimeOfMeasuring() {
        timeOfMeasuring = System.currentTimeMillis();
    }
}
