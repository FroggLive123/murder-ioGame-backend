package org.mainLogic.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mainLogic.entity.AgentEntity;
import org.mainLogic.repository.AgentRepository;
import org.mainLogic.service.message.PositionChangeMessage;

import java.lang.runtime.ObjectMethods;
import java.net.Socket;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class NotifyAgentSerice implements AgentService {

    private final AgentService agentService;
    private final ObjectMapper objectMapper;

    public NotifyAgentSerice(AgentService agentService, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.agentService = agentService;
    }

    @Override
    public void move(int x, int y, UUID uuid) {
        agentService.move(x,y, uuid);

        PositionChangeMessage positionChangeMessage = new PositionChangeMessage(x, y, uuid);

        try {
            alart.send(objectMapper.writeValueAsBytes(positionChangeMessage));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public float[] getPosition(AgentEntity agent) {
        return agentService.getPosition(agent);
    }

    @Override
    public Collection<AgentEntity> getAll() {
        return agentService.getAll();
    }

    @Override
    public void die(AgentEntity agent) {
        agentService.die(agent);
    }

    @Override
    public void reborn(AgentEntity agent) {
        agentService.reborn(agent);
    }

    @Override
    public void addUser(String userSha1, Socket socket) throws Exception {
        agentService.addUser(userSha1, socket);
    }

    @Override
    public AgentEntity randomAgent(String hash) throws Exception {
        return agentService.randomAgent(hash);
    }
}
