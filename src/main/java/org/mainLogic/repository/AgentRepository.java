package org.mainLogic.repository;

import org.apache.commons.codec.digest.DigestUtils;
import org.mainLogic.entity.AgentEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgentRepository {
    Map<String, AgentEntity> agentHashMap = new HashMap<String, AgentEntity>();
    //Have to have two hasMaps, first will hase UUID + agentEntity, second will hase Hash + agentEntity

    public AgentRepository() {

    }

    public void createUUIDRepository(ArrayList<AgentEntity> agentList) throws InterruptedException {
        for(int i = 0; i < agentList.size(); i++) {
            String uuidHash = DigestUtils.sha1Hex(((agentList.get(i)).uuid).toString());
            agentHashMap.put(uuidHash, agentList.get(i));
        }
    }

    public ArrayList<AgentEntity> getAgentRepository() {
        for (Map.Entry<String, AgentEntity> entry : agentHashMap.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
            List<AgentEntity> agents = new ArrayList<>(entry.getasd Value());
        }

        return new ArrayList<>();
    }


}
