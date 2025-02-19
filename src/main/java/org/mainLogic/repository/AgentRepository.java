package org.mainLogic.repository;

import org.mainLogic.entity.AgentEntity;

import java.net.Socket;
import java.util.*;

public class AgentRepository {
    private final Map<UUID, AgentEntity> agentHashMap = new HashMap<>();
    private Map<String, Socket> userHashMap = new HashMap<>();
    private Map<Socket, AgentEntity> agentSokcetMap = new HashMap<>();

    //Have to have two hasMaps, first will hase UUID + agentEntity, second will hase Hash + agentEntity

    public AgentRepository(final List<AgentEntity> agentList) {
        for (AgentEntity agentEntity : agentList) {
            agentHashMap.put(agentEntity.getUuid(), agentEntity);
        }
    }


    public void addUser(final String userSha1,final Socket socket ) throws InterruptedException {
        userHashMap.put(userSha1, socket);
    }

    public void addUser(final Socket socket,final AgentEntity agent) throws InterruptedException {

    }

    public void deleteUser(final String userSha1) throws InterruptedException {
        userHashMap.remove(userSha1);
    }

    public Collection<AgentEntity> getAll() {
        return agentHashMap.values();
    }

    public Socket getUser(final String userSha1) throws InterruptedException {
        return userHashMap.get(userSha1);
    }

    public AgentEntity getAgent(final Socket socket) throws InterruptedException {
        return agentSokcetMap.get(socket);
    }

    public AgentEntity getAgent(final UUID uuid) {
        return agentHashMap.get(uuid);
    }

    public Map<String, Socket> getUserHashMap() {
        return userHashMap;
    }



}
