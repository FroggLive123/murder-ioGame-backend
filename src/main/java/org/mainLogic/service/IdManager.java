package org.mainLogic.service;

import org.mainLogic.entity.AgentEntity;

import java.net.Socket;
import java.util.*;

public class IdManager {
    private final short amountOfPlayers;
    Map<Short, Socket> idMap = new HashMap<>();

    public IdManager(final short amountOfPlayers) {
        this.amountOfPlayers = amountOfPlayers;
    }

    public short add(final Socket socket) {
        short idMapSize = (short) idMap.size();

        if(idMapSize == amountOfPlayers - 1) {
            throw new IllegalArgumentException("IdManager is full");
        }
        short id = (short) (idMapSize + 1);

        idMap.put(id, socket);
        return id;
    }

    public Socket get(final short id) {
        return idMap.get(id);
    }

    public void remove(final short id) {
        idMap.remove(id);
    }
}
