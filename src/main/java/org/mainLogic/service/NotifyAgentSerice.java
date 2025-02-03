package org.mainLogic.service;

import org.mainLogic.entity.AgentEntity;
import org.mainLogic.repository.AgentRepository;

import java.util.List;

public class NotifyAgentSerice implements AgentService {
    private AgentRepository agentRepository;

    @Override
    public AgentEntity randomAgent(String hash) throws Exception {
//        boolean research = true;
        List<AgentEntity> agents = agentRepository.getAgentRepository();

        for(int i = 0; i < agents.size(); i++){
            AgentEntity agent = agents.get(i);
            if(agent.userSocket.isEmpty()) {
                agent.userSocket = hash;
                return agent;
            }
        }
        return null;
    }

}
