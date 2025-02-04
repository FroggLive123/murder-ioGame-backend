package org.mainLogic.service;

import org.mainLogic.entity.AgentEntity;
import org.mainLogic.repository.AgentRepository;

import java.net.Socket;
import java.util.List;

public class NotifyAgentSerice implements AgentService {

    private final AgentService agentService;

    public NotifyAgentSerice(AgentService agentService) {;
        this.agentService = agentService;
    }

    @Override
    public void move(int[] direction, AgentEntity agent) {
        agentService.move(direction, agent);
    }

    @Override
    public float[] getPosition(AgentEntity agent) {
        return agentService.getPosition(agent);
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
