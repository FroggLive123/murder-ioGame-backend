package org.mainLogic.service;

import org.mainLogic.entity.AgentEntity;
import org.mainLogic.repository.AgentRepository;

import java.net.Socket;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class AgentService {
    private AgentRepository agentRepository;
    private List<AgentEntity> agents;
    private int amountOfPlayers;
    int xMax  = 1490;
    int yMax = 690;
    //one step for agent
    int step = 10;

    //make it interface
    public AgentService(AgentRepository agentRepository, int amountOfPlayers) {
        this.agentRepository = agentRepository;
        agents = agentRepository.getAgentRepository();
        this.amountOfPlayers = amountOfPlayers;
    }

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

    public float[] getPosition(UUID uuid) {
        AgentEntity agent = agentRepository.getAgent(uuid);
        float[] position = new float[2];
        position[0] = (float) agent.x;
        position[1] = (float) agent.y;
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

    public void addUser(String userSha1, Socket socket) throws Exception {

        //overflow system
        if(amountOfPlayers > agentRepository.userHashMap.size()){
            agentRepository.addUser(userSha1, socket);
        }else{
            throw new Exception("overflow");
        }
    }

    public AgentEntity randomAgent() {
//        boolean research = true;
        for(int i = 0; i < amountOfPlayers; i++){
            AgentEntity agent = agents.get(i);
            if(agent.isBot) {
                return agent;
            }
        }
        return null;
    }

}
