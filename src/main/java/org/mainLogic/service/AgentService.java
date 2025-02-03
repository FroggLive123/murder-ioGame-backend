package org.mainLogic.service;

import org.mainLogic.dto.AgentDTO;
import org.mainLogic.entity.AgentEntity;
import org.mainLogic.repository.AgentRepository;

import java.net.Socket;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface AgentService {

    static AgentRepository agentRepository = new AgentRepository();
    //make it interface

    default void move(int[] direction, AgentEntity agent) {
        int xMax = 1490;
        int yMax = 1490;
        int step = 10;

        int x = direction[0];
        int y = direction[1];

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

    default float[] getPosition(AgentEntity agent) {
        float[] position = new float[2];
        position[0] = (float) agent.x;
        position[1] = (float) agent.y;
        return position;
    }


    default void die(AgentEntity agent) {
        long timeOfDead = Instant.now().toEpochMilli();
        agent.isAlive = false;
        agent.timeOfDead = timeOfDead;
    }

    default void reborn(AgentEntity agent) {
        if((agent.timeOfDead - Instant.now().toEpochMilli()) > 180000){
            agent.isAlive = true;
        }
    }

    default void addUser(String userSha1, Socket socket) throws Exception {
        int amountOfPlayers = 40;

        //overflow system
        if(amountOfPlayers > agentRepository.getUserHashMap().size()){
            agentRepository.addUser(userSha1, socket);
        }else{
            throw new Exception("overflow");
        }
    }

    default AgentEntity randomAgent(String hash) throws Exception {
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
