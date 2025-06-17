package org.mainLogic.entity;

import java.net.Socket;

public class ClientSession {
    private final Socket socket;


    public ClientSession(Socket socket) {
        this.socket = socket;
    }
}
