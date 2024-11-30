package org.mainLogic.service;

import org.mainLogic.entity.AgentEntity;
import org.mainLogic.repository.AgentRepository;

import java.net.Socket;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AgentService {
    private AgentRepository agentRepository;
    private List<AgentEntity> agents;
    private int amountOfPlayers;
    int xMax  = 1490;
    int yMax = 690;
    //one step for agent
    int step = 10;

    public AgentService(AgentRepository agentRepository, int amountOfPlayers) {
        this.agentRepository = agentRepository;
        agents = agentRepository.getAgentRepository();
        this.amountOfPlayers = amountOfPlayers;
    }
//
//    public UUID getUUID(){
//
////        agentHashMap.get()
////        return uuid;
//    }

    public void move(int x, int y, AgentEntity agent) {
        switch(x) {
            case 0:
                break;
            case 1:
                if( agent.x != xMax){
                    agent.x += step;
                }
                break;
            case -1:
                if( agent.x != 10){
                    agent.x -= step;
                }
        }
        switch(y) {
            case 0:
                break;
            case 1:
                if( agent.y != yMax){
                    agent.y += step;
                }
                break;
            case -1:
                if( agent.y != 10){
                    agent.y -= step;
                }
        }
    }

    public int[][] getPosition() {
        int[][] position = new int[0][0];
        return position;
    }


    public void die(AgentEntity agent) {
        long timeOfDead = Instant.now().toEpochMilli();
        agent.isAlive = false;
        agent.timeOfDead = timeOfDead;
    }

    public void reborn(AgentEntity agent) {
        if((agent.timeOfDead - Instant.now().toEpochMilli()) > 180000){
            agent.isAlive = true;
        }
    }

    public void addUser(String userSha1, Socket socket) throws InterruptedException {
        agentRepository.addUser(userSha1, socket);
    }

    public String randomAgent() {
//        boolean research = true;
        for(int i = 0; i < amountOfPlayers; i++){
            AgentEntity verifiableAgent = agents.get(i);
            if(verifiableAgent.isBot == true) {
                return String.valueOf(verifiableAgent.uuid);
            }
        }
        return null;
    }

}
