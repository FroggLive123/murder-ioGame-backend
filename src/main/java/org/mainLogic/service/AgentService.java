package org.mainLogic.service;

import org.mainLogic.entity.AgentEntity;

import java.net.Socket;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface AgentService {

    void move(final short x,final short y,final int id);

    float[] getPosition(final int id);
    //Todo change

    Collection<AgentEntity> getAll();

    void die(final int id);

    void rebornAll();

    AgentEntity randomAgent(final short userid) throws Exception;

    List<Integer> kill(final int direction, final short userid);

    AgentEntity getAgent(final short userid);
}

