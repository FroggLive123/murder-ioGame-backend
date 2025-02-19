package org.mainLogic.gameLoop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mainLogic.dto.AgentDTO;
import org.mainLogic.entity.AgentEntity;
import org.mainLogic.service.AgentService;
import org.mainLogic.service.CommandFormer;
import org.mainLogic.service.CommandQueue;
import org.mainLogic.service.executors.Executor;
import org.mainLogic.service.Publisher;
import org.mainLogic.service.message.AgentStatusMessage;

import java.io.IOException;
import java.util.*;

public class GameLoop implements Runnable {

    private final Publisher publisher;
    private final CommandQueue commandQueue;
    private final AgentService agentService;
    private final List<Executor> executors;
    private final CommandFormer commandFormer;
    private final ObjectMapper objectMapper;

    ArrayList<AgentEntity> listOfBots = new ArrayList<AgentEntity>();
    int maxPlayers = 40;
    long rebornTime = 180000;


    public GameLoop (final AgentService agentService, final Publisher publisher, final CommandQueue commandQueue, final List<Executor> executors, final CommandFormer commandFormer,
                     final ObjectMapper objectMapper
    ) throws InterruptedException {
        this.agentService = agentService;
        this.publisher = publisher;
        this.commandQueue = commandQueue;
        this.executors = executors;
        this.commandFormer = commandFormer;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run() {
        while (true) {

            for(int i = 0; i < 100; i++){
                if(!commandQueue.hasNext()){
                    break;
                }

                process(commandQueue.next());
            }

            final byte[] agentStatusMessage = createAgentStatusMessage();
            try {
                publisher.broadcastAll(agentStatusMessage);
            } catch (IOException e) {
//                throw new RuntimeException(e);
            }

        }
    }

    private byte[] createAgentStatusMessage() {
        final Collection<AgentEntity> agents = agentService.getAll();
        List<AgentDTO> agentDTOS = new ArrayList<>();

        for(AgentEntity agent: agents){
            int[] possition = {agent.getX(), agent.getY()};
            AgentDTO agentDTO = new AgentDTO(agent.getUuid(), possition, agent.isAlive());

            agentDTOS.add(agentDTO);
        }

        AgentStatusMessage agentStatusMessage = new AgentStatusMessage("AgentStatus", agentDTOS);
        byte[] message = objectMapper.writeValueAsBytes()
    }

    private void process(byte[] next) {
        for(Executor executor : executors){
            if(executor.accept(next)) {
                break;
            }
        }
    }

    private void reborn(final Collection<AgentEntity> agents) {
        for(AgentEntity agent: agents) {
            if(agent.isAlive()){
                continue;
            }

            if(System.currentTimeMillis() - agent.getTimeOfDead() >= rebornTime) {
                agent.setAlive(true);
            }
        }
    }

    private void moveBots(final Collection<AgentEntity> agents) {
        for(AgentEntity agent: agents) {
            if(!agent.isBot() || !agent.isAlive()){
                continue;
            }
            //There is place for problem with instant bot appearing and someone instant killing him
            agentService.move(random(1, 8), random(1, 8), agent.getUuid());

        }
    }



    private int random(final int min ,final int max){
        return (int)Math.floor(Math.random() * (max - min) + min);
    }
}

