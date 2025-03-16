package org.mainLogic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mainLogic.entity.AgentEntity;
import org.mainLogic.service.message.KillNotificationMessage;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class NotifyAgentService implements AgentService {

    private final AgentService agentService;
    private final Publisher publisher;
    private final ObjectMapper objectMapper;

    public NotifyAgentService(final AgentService agentService,final ObjectMapper objectMapper,final Publisher publisher) {
        this.objectMapper = objectMapper;
        this.agentService = agentService;
        this.publisher = publisher;
    }

    @Override
    public void move(final short x,final short y,final UUID uuid) {
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
    public AgentEntity randomAgent(final short userid) throws Exception {
        return agentService.randomAgent(userid);
    }

    @Override
    public List<UUID> kill(final int direction,final UUID uuid) {
        final KillNotificationMessage killNotificationMessage = new KillNotificationMessage("slash", uuid, direction);
        try {
            publisher.broadcast(objectMapper.writeValueAsBytes(killNotificationMessage));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return agentService.kill(direction, uuid);
    }

    @Override
    public void rebornAll() {
        agentService.rebornAll();
    }

    @Override
    public AgentEntity getAgent(final short userid) {
        return agentService.getAgent(userid);
    }
}
