package org.mainLogic.service;

import org.mainLogic.entity.ClientSession;
import org.mainLogic.entity.User;

import java.net.Socket;
import java.util.*;

public class SocketManager {
    private final Map<Short, ClientSession> idSocket = new HashMap<>();
    //temporal adding of users into SocketManager, need to find better place for them
    private final List<User> userList = new ArrayList<>();
    private final short playersAmount;
    private static short nextid = 0;

    public SocketManager(final short playersAmount) {
        this.playersAmount = playersAmount;
    }

    public short add(final ClientSession clientSession) {
        if(idSocket.containsValue(clientSession)) {
            throw new IllegalArgumentException("Socket already exists");
        }
        if(idSocket.size() == playersAmount) {
            throw new IllegalArgumentException("Limit of players reached");
        }

        while (true) {
            if(!idSocket.containsKey(nextid)) {
                break;
            }
            nextid++;
        }

        final short id = nextid;
        nextid++;
        idSocket.put(id, clientSession);

        //User added
        User user = new User();
        user.updateTimeOfMeasuring();
        userList.add(user);

        return id;
    }

    public void remove(final short id) {
        idSocket.remove(id);
        //User deleted
        userList.remove(id);
    }

    public Optional<ClientSession> get(final short id) {
        return Optional.ofNullable(idSocket.get(id));
    }

    public User getUser(final short id) {
        return userList.get(id);
    }

    public short getId(final Socket socket) {
        return idSocket.entrySet().stream().filter(e -> e.getValue().equals(socket)).findFirst().get().getKey();
    }

    public Iterator<Short> getIdIterator() {
        return idSocket.keySet().iterator();
    }
}
