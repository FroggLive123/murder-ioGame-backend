package org.mainLogic.service;

import org.mainLogic.entity.AgentEntity;
import org.mainLogic.repository.AgentRepository;

import java.net.Socket;
import java.rmi.NoSuchObjectException;
import java.time.Instant;
import java.util.*;

public class DefaultAgentService implements  AgentService {
    private final AgentRepository agentRepository;
    private final Map<UUID, AgentEntity> agentMap;
    private final AgentService agentService;

    public DefaultAgentService(final AgentRepository agentRepository, final AgentService agentService) {
        this.agentRepository = agentRepository;
        this.agentMap = createAgentHashMap(agentRepository.getAll());
        this.agentService = agentService;
    }

    @Override
    public void move(int x, int y, UUID uuid) {
        //Create agentService to check if agent alive

        AgentEntity agent = agentMap.get(uuid);
        if (!agent.isAlive()) {
            throw new IllegalStateException("Agent is not alive");
        }

        agent.setX(x);
        agent.setY(y);
    }

    @Override
    public float[] getPosition(UUID uuid) {
        AgentEntity agent = agentMap.get(uuid);

        float[] position = new float[2];
        position[0] = (float) agent.getX();
        position[1] = (float) agent.getY();
        return position;
    }

    @Override
    public void die(UUID uuid) {
        AgentEntity agent = agentMap.get(uuid);

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
    public void addUser(String userSha1, Socket socket) throws Exception {

        int amountOfPlayers = 40;

        //overflow system
        if (amountOfPlayers > agentRepository.getUserHashMap().size()) {
            agentRepository.addUser(userSha1, socket);
        } else {
            throw new Exception("overflow");
        }
    }

    @Override
    public AgentEntity randomAgent(String hash) throws Exception {
//        boolean research = true;
        List<AgentEntity> agents = (List<AgentEntity>) agentMap.values();

        for (int i = 0; i < agents.size(); i++) {
            AgentEntity agent = agents.get(i);
            if (agent.getUserid().isEmpty()) {
                agent.setUserid(hash);
                return agent;
            }
        }
        throw new NoSuchObjectException("There is no free agents");
    }

    @Override
    public List<UUID> kill(final int direction,final UUID userUuid) {
        final AgentEntity user = agentMap.get(userUuid);

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
    public AgentEntity getAgent(Socket socket) {

    }

    public List<UUID> getAllUUID() {
        List<UUID> uuidArray = new ArrayList<>();
        for(AgentEntity agent: agentMap.values()) {
            uuidArray.add(agent.getUuid());
        }
        return uuidArray;
    }

    private HashMap<UUID, AgentEntity> createAgentHashMap(Collection<AgentEntity> agents) {
        HashMap<UUID, AgentEntity> map = new HashMap<>();

        for (AgentEntity agent : agents) {
            map.put(agent.getUuid(), agent);
        }

        return map;
    }
}