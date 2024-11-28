package org.mainLogic.service;

import org.mainLogic.entity.AgentEntity;

import java.util.HashMap;
import java.util.UUID;

public class AgentService {
    private HashMap<Server, AgentEntity> agentHashMap;

    public AgentService(HashMap<Server, AgentEntity> agentHashMap) {
        this.agentHashMap = agentHashMap;
    }
//
//    public UUID getUUID(){
//
////        agentHashMap.get()
////        return uuid;
//    }

    public void move() {

    }

    public int[][] getPosition() {
        int[][] position = new int[0][0];
        return position;
    }

    public void die(){

    }

    public void reborn() {

    }

//    public AgentEntity getRandomBot(Server server) {
//
//    }


}
