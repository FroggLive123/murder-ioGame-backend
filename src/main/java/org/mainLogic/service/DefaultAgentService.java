package org.mainLogic.service;

import org.mainLogic.entity.AgentEntity;
import org.mainLogic.repository.AgentRepository;

import java.net.Socket;
import java.rmi.NoSuchObjectException;
import java.time.Instant;
import java.util.*;

public class DefaultAgentService implements  AgentService {
    private final AgentRepository agentRepository;

    public DefaultAgentService(final AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    @Override
    public void move(final short x, final short y, final UUID uuid) {
        AgentEntity agent = agentRepository.getAgent(uuid);
        if (!agent.isAlive()) {
            throw new IllegalStateException("Agent is not alive");
        }

        agent.setX(x);
        agent.setY(y);
    }

    @Override
    public float[] getPosition(UUID uuid) {
        AgentEntity agent = agentRepository.getAgent(uuid);

        float[] position = new float[2];
        position[0] = (float) agent.getX();
        position[1] = (float) agent.getY();
        return position;
    }

    @Override
    public void die(UUID uuid) {
        AgentEntity agent = agentRepository.getAgent(uuid);

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
    public List<UUID> kill(final int direction,final UUID userUuid) {
        final AgentEntity user = agentRepository.getAgent(userUuid);

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

    public List<UUID> getAllUUID() {
        final Collection<AgentEntity> agents = agentRepository.getAll();

        List<UUID> uuids = new ArrayList<>();

        for (AgentEntity agent : agents) {
            uuids.add(agent.getUuid());
        }

        return uuids;
    }

    private HashMap<UUID, AgentEntity> createAgentHashMap(Collection<AgentEntity> agents) {
        HashMap<UUID, AgentEntity> map = new HashMap<>();

        for (AgentEntity agent : agents) {
            map.put(agent.getUuid(), agent);
        }

        return map;
    }
}