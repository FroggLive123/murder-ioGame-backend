package org.mainLogic.service;

import org.mainLogic.entity.AgentEntity;
import org.mainLogic.repository.AgentRepository;

import java.net.Socket;
import java.time.Instant;
import java.util.List;

public class DefaultAgentService implements  AgentService {
    private final AgentRepository agentRepository;

    public DefaultAgentService(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    @Override
    public void move(int[] direction, AgentEntity agent) {

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

    @Override
    public float[] getPosition(AgentEntity agent) {
        float[] position = new float[2];
        position[0] = (float) agent.x;
        position[1] = (float) agent.y;
        return position;
    }

    @Override
    public void die(AgentEntity agent) {
        long timeOfDead = Instant.now().toEpochMilli();
        agent.isAlive = false;
        agent.timeOfDead = timeOfDead;
    }

    @Override
    public void reborn(AgentEntity agent) {
        if((agent.timeOfDead - Instant.now().toEpochMilli()) > 180000){
            agent.isAlive = true;
        }
    }

    @Override
    public void addUser(String userSha1, Socket socket) throws Exception {

        int amountOfPlayers = 40;

        //overflow system
        if(amountOfPlayers > agentRepository.getUserHashMap().size()){
            agentRepository.addUser(userSha1, socket);
        }else{
            throw new Exception("overflow");
        }
    }

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
