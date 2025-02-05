package org.mainLogic.service;

import org.mainLogic.entity.AgentEntity;

import java.net.Socket;
import java.util.Collection;
import java.util.UUID;

public interface AgentService {

    void move(int x, int y, UUID uuid);

    float[] getPosition(AgentEntity agent);

    Collection<AgentEntity> getAll();

    void die(AgentEntity agent);

    void reborn(AgentEntity agent);

    void addUser(String userSha1, Socket socket) throws Exception;

    AgentEntity randomAgent(String hash) throws Exception;
}

