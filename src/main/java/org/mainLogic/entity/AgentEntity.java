package org.mainLogic.entity;

import java.net.Socket;
import java.time.Instant;
import java.util.UUID;

public class AgentEntity {

    private final UUID uuid;
    private int x;
    private int y;
    private String userSocket;
    private boolean isAlive  = true;
    private long timeOfDead ;

    public AgentEntity(final UUID uuid,final int x,final int y) throws InterruptedException {
        this.uuid = uuid;

        this.x = x;
        this.y = y;
    }

    public boolean isBot() {
        return userSocket == null;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public void setY(final int y) {
        this.y = y;
    }

    public void setUserSocket(final String userSocket) {
        this.userSocket = userSocket;
    }

    public void setAlive(final boolean alive) {
        isAlive = alive;
    }

    public void setTimeOfDead(final long timeOfDead) {
        this.timeOfDead = timeOfDead;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getUserSocket() {
        return userSocket;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public long getTimeOfDead() {
        return timeOfDead;
    }
}
