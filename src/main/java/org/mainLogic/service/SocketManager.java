package org.mainLogic.service;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SocketManager {
    private final Map<String, Socket> hashSocket = new HashMap<>();
    private final Map<Integer, Socket> idSocket = new HashMap<>();

    public SocketManager() {

    }

    public void add(final String hash,final Socket socket) {
        hashSocket.put(hash, socket);
    }

    public void add(final int id,final Socket socket) { idSocket.put(id, socket);  }

    public void remove(final String hash) {
        hashSocket.remove(hash);
    }

    public void remove(final int id) {
        idSocket.remove(id);
    }

    public Optional<Socket> get(final String hash) {
        return Optional.ofNullable(hashSocket.get(hash));
    }

    public Optional<Socket> get(final short id) {return Optional.ofNullable(idSocket.get(id));}
}
