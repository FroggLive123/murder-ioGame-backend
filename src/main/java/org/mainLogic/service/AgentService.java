package org.mainLogic.service;

import org.mainLogic.entity.AgentEntity;

import java.net.Socket;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface AgentService {

    void move(final short x,final short y,final UUID uuid);

    float[] getPosition(final UUID uuid);

    Collection<AgentEntity> getAll();

    void die(final UUID uuid);

    void rebornAll();

    AgentEntity randomAgent(final short userid) throws Exception;

    List<UUID> kill(final int direction, final UUID uuid);

    AgentEntity getAgent(final short userid);
}

