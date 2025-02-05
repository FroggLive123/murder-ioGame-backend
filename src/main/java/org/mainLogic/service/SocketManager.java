package org.mainLogic.service;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SocketManager {
    private final Map<String, Socket> sockets = new HashMap<>();

    public SocketManager() {

    }

    public void add(String hash,Socket socket) {
        sockets.put(hash, socket);
    }

    public void remove(String hash) {
        sockets.remove(hash);
    }

    public Optional<Socket> get(String hash) {
        return Optional.ofNullable(sockets.get(hash));
    }
}
