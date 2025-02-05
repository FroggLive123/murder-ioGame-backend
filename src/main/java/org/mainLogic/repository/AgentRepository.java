package org.mainLogic.repository;

import org.mainLogic.entity.AgentEntity;

import java.net.Socket;
import java.util.*;

public class AgentRepository {
    private Map<UUID, AgentEntity> agentHashMap = new HashMap<>();
    private Map<String, Socket> userHashMap = new HashMap<>();

    //Have to have two hasMaps, first will hase UUID + agentEntity, second will hase Hash + agentEntity

    public AgentRepository(final List<AgentEntity> agentList) {
        for (AgentEntity agentEntity : agentList) {
            agentHashMap.put(agentEntity.uuid, agentEntity);
        }
    }


    public void addUser(String userSha1, Socket socket ) throws InterruptedException {
        userHashMap.put(userSha1, socket);
    }

    public void deleteUser(String userSha1) throws InterruptedException {
        userHashMap.remove(userSha1);
    }

    public Collection<AgentEntity> getAll() {
        return agentHashMap.values();
    }

    public Socket getUser(String userSha1) throws InterruptedException {
        return userHashMap.get(userSha1);
    }

    public AgentEntity getAgent(UUID uuid) {

        return agentHashMap.get(uuid);
    }

    public Map<String, Socket> getUserHashMap() {
        return userHashMap;
    }

}
