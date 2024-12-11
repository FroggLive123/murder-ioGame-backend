package org.mainLogic.repository;

import org.apache.commons.codec.digest.DigestUtils;
import org.mainLogic.entity.AgentEntity;

import java.net.Socket;
import java.util.*;

public class AgentRepository {
    Map<String, AgentEntity> agentHashMap = new HashMap<String, AgentEntity>();
    public Map<String, Socket> users = new HashMap<>();
    //Have to have two hasMaps, first will hase UUID + agentEntity, second will hase Hash + agentEntity

    public AgentRepository() {

    }

    public Map<String, AgentEntity> createUUIDRepository(ArrayList<AgentEntity> agentList) throws InterruptedException {
        for(int i = 0; i < agentList.size(); i++) {
            String uuidHash = DigestUtils.sha1Hex(((agentList.get(i)).uuid).toString());
            agentHashMap.put(uuidHash, agentList.get(i));
        }
        return agentHashMap;
    }

    public void addUser(String userSha1, Socket socket ) throws InterruptedException {
        users.put(userSha1, socket);
    }



    public List<AgentEntity> getAgentRepository() {
        for (Map.Entry<String, AgentEntity> entry : agentHashMap.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
            List<AgentEntity> agents = new ArrayList<>((Collection) entry.getValue());
        }

        return new ArrayList<>();
    }

    public Socket getUser(String userSha1) throws InterruptedException {
        return users.get(userSha1);
    }

}
