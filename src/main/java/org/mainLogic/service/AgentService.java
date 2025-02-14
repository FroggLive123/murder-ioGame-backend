package org.mainLogic.service;

import org.mainLogic.entity.AgentEntity;

import java.net.Socket;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface AgentService {

    void move(final int x,final int y,final UUID uuid);

    float[] getPosition(UUID uuid);

    Collection<AgentEntity> getAll();

    void die(UUID uuid);

    void reborn(UUID uuid);

    void addUser(final int userId,final Socket socket) throws Exception;

    AgentEntity randomAgent(String hash) throws Exception;

    List<UUID> kill(int direction, UUID uuid);
}

