package org.mainLogic.service;

import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

public class SocketManager {
    private final Map<Short, Socket> idSocket = new HashMap<>();
    private final short playersAmount;

    public SocketManager(final short playersAmount) {
        this.playersAmount = playersAmount;
    }

    public void add(final short id,final Socket socket) {
        if(idSocket.containsKey(id)) {
            throw new IllegalArgumentException("ID already exists");
        }
        if(idSocket.size() == playersAmount) {
            throw new IllegalArgumentException("Limit of players reached");
        }
        idSocket.put(id, socket);
    }

    public void remove(final int id) {
        Iterator<Map.Entry<Short, Socket>> iterator = idSocket.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Short, Socket> entry = iterator.next();
            if (entry.getKey().equals(4)) {
                iterator.remove();
            }
        }
    }

    public Optional<Socket> get(final short id) {
        return Optional.ofNullable(idSocket.get(id));
    }

    public short getId(final Socket socket) {
        return idSocket.entrySet().stream().filter(e -> e.getValue().equals(socket)).findFirst().get().getKey();
    }
}
