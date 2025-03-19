package org.mainLogic.service;

import org.mainLogic.entity.AgentEntity;
import org.mainLogic.repository.AgentRepository;

import java.rmi.NoSuchObjectException;
import java.time.Instant;
import java.util.*;

public class DefaultAgentService implements  AgentService {
    private final AgentRepository agentRepository;

    public DefaultAgentService(final AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    @Override
    public void move(final short x, final short y, final int id) {
        AgentEntity agent = agentRepository.getAgent(id);
        if (!agent.isAlive()) {
            throw new IllegalStateException("Agent is not alive");
        }

        agent.setX(x);
        agent.setY(y);
    }

    @Override
    public float[] getPosition(int id) {
        AgentEntity agent = agentRepository.getAgent(id);

        float[] position = new float[2];
        position[0] = (float) agent.getX();
        position[1] = (float) agent.getY();
        return position;
    }

    @Override
    public void die(int id) {
        AgentEntity agent = agentRepository.getAgent(id);

        long timeOfDead = Instant.now().toEpochMilli();
        agent.setAlive(false);
        agent.setTimeOfDead(timeOfDead);
    }

    @Override
    public void rebornAll() {
        for(AgentEntity agent : agentRepository.getAll()) {
            if ((agent.getTimeOfDead() - Instant.now().toEpochMilli()) > 180000) {
                agent.setAlive(true);
            }
        }
    }

    @Override
    public Collection<AgentEntity> getAll() {
        return agentRepository.getAll();
    }

    @Override
    public AgentEntity randomAgent(final short userid) throws Exception {
//        boolean research = true;
        final Collection<AgentEntity> agents = agentRepository.getAll();

        for (AgentEntity agent : agents) {
            if(agent.getUserid() == null) {
                agent.setUserid(userid);
                return agent;
            }
        }

        throw new NoSuchObjectException("There is no free agents");
    }

    @Override
    public List<Integer> kill(final int direction,final short userid) {
        final AgentEntity user = agentRepository.getAgent(userid);

        if(direction > 8 || direction < 1) {
            throw new IllegalArgumentException("direction must be between 0 and 8");
        }

        final int[] agentHitBox = {10,10};
        final int[] slashHitBox = {15,15};

        final int[] userPosition = {user.getX(), user.getY()};
        int[] slashPosition;

        switch (direction) {
            case 1:
        }

        return null;
    }

    @Override
    public AgentEntity getAgent(final short userid) {
        final Collection<AgentEntity> agents = agentRepository.getAll();

        AgentEntity user = null;

        for(AgentEntity agent : agents) {
            if(agent.getUserid().equals(userid)) {
                user = agent;
                break;
            }
        }

        return user;
    }

    public List<Integer> getAllUUID() {
        final Collection<AgentEntity> agents = agentRepository.getAll();

        List<Integer> uuids = new ArrayList<>();

        for (AgentEntity agent : agents) {
            uuids.add(agent.getId());
        }

        return uuids;
    }

    private HashMap<Integer, AgentEntity> createAgentHashMap(Collection<AgentEntity> agents) {
        HashMap<Integer, AgentEntity> map = new HashMap<>();

        for (AgentEntity agent : agents) {
            map.put(agent.getId(), agent);
        }

        return map;
    }
}