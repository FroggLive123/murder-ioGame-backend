package org.mainLogic.service;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SocketManager {
    private final Map<String, Socket> hashSocket = new HashMap<>();
    private final Map<Short, Socket> idSocket = new HashMap<>();

    public SocketManager() {

    }

    public void add(final String hash,final Socket socket) {
        hashSocket.put(hash, socket);
    }

    public void add(final short id,final Socket socket) { idSocket.put(id, socket);  }

    public void remove(final String hash) {
        hashSocket.remove(hash);
    }

    public void remove(final int id) {
        idSocket.remove(id);
    }

    public Optional<Socket> get(final String hash) {
        return Optional.ofNullable(hashSocket.get(hash));
    }

    public Optional<Socket> get(final short id) {
        return Optional.ofNullable(idSocket.get(id));
    }

    public short getId(final Socket socket) {
        return idSocket.entrySet().stream().filter(e -> e.getValue().equals(socket)).findFirst().get().getKey();
    }
}
