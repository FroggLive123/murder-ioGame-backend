package org.mainLogic.service;

import org.mainLogic.dto.AgentDTO;
import org.mainLogic.entity.AgentEntity;
import org.mainLogic.repository.AgentRepository;

import java.net.Socket;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface AgentService {

    void move(int[] direction, AgentEntity agent);

    float[] getPosition(AgentEntity agent);

    void die(AgentEntity agent);

    void reborn(AgentEntity agent);

    void addUser(String userSha1, Socket socket) throws Exception;

    AgentEntity randomAgent(String hash) throws Exception;
}

