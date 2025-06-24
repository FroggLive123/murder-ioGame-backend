package org.mainLogic.entity;

import java.net.Socket;

public class ClientSession {
    private final Socket socket;
    private long syncTime;

    public ClientSession(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void resync() {
        syncTime = System.currentTimeMillis();
    }

    public boolean isActive() {
        //after 3 second without TTL message session become enactive
        if(currentTime() - syncTime >= 3000) {
            return false;
        }

        return true;
    }

    public long currentTime() {
        return System.currentTimeMillis();
    }
}
