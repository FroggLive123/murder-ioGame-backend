package org.mainLogic.repository;

import org.mainLogic.entity.AgentEntity;

import java.util.*;

public class AgentRepository {
    private final Map<Integer, AgentEntity> agentHashMap = new HashMap<>();

    //Have to have two hasMaps, first will hase UUID + agentEntity, second will hase Hash + agentEntity

    public AgentRepository(final List<AgentEntity> agentList) {
        for (AgentEntity agentEntity : agentList) {
            agentHashMap.put(agentEntity.getId(), agentEntity);
        }
    }

    public Collection<AgentEntity> getAll() {
        return agentHashMap.values();
    }

    public AgentEntity getAgent(final int id) {
        return agentHashMap.get(id);
    }
}
