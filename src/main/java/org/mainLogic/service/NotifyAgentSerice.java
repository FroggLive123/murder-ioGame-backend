package org.mainLogic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mainLogic.entity.AgentEntity;
import org.mainLogic.service.message.KillNotificationMessage;

import java.io.IOException;
import java.net.Socket;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class NotifyAgentSerice implements AgentService {

    private final AgentService agentService;
    private final Publisher publisher;
    private final ObjectMapper objectMapper;

    public NotifyAgentSerice(AgentService agentService, ObjectMapper objectMapper, Publisher publisher) {
        this.objectMapper = objectMapper;
        this.agentService = agentService;
        this.publisher = publisher;
    }

    @Override
    public void move(int x, int y, UUID uuid) {
        agentService.move(x,y, uuid);
        }

    @Override
    public float[] getPosition(UUID uuid) {
        return agentService.getPosition(uuid);
    }

    @Override
    public Collection<AgentEntity> getAll() {
        return agentService.getAll();
    }

    @Override
    public void die(UUID uuid) {
        agentService.die(uuid);
    }

    @Override
    public void rebornAll(UUID uuid) {
        agentService.rebornAll(uuid);
    }

    @Override
    public void addUser(String userSha1, Socket socket) throws Exception {
        agentService.addUser(userSha1, socket);
    }

    @Override
    public AgentEntity randomAgent(String hash) throws Exception {
        return agentService.randomAgent(hash);
    }

    @Override
    public List<UUID> kill(int direction, UUID uuid) {
        final KillNotificationMessage killNotificationMessage = new KillNotificationMessage("slash", uuid, direction);
        try {
            publisher.broadcastAll(objectMapper.writeValueAsBytes(killNotificationMessage));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return agentService.kill(direction, uuid);
    }
}
