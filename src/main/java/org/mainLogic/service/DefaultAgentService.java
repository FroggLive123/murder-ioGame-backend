package org.mainLogic.service;

import org.mainLogic.entity.AgentEntity;
import org.mainLogic.repository.AgentRepository;

import java.net.Socket;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class DefaultAgentService implements  AgentService {
    private final AgentRepository agentRepository;

    public DefaultAgentService(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    @Override
    public void move(int x, int y, UUID uuid) {
        //Create agentService to check if agent alive

        AgentEntity agent = agentRepository.getAgent(uuid);
        if(!agent.isAlive()){
            throw new IllegalStateException("Agent is not alive");
        }

        agent.setX(x);
        agent.setY(y);
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
    public Collection<AgentEntity> getAll() {
        return agentRepository.getAll();
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
            Collection<AgentEntity> agents = agentRepository.getAll();

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
