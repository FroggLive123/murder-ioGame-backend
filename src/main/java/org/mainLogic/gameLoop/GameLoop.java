package org.mainLogic.gameLoop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mainLogic.dto.AgentDTO;
import org.mainLogic.entity.AgentEntity;
import org.mainLogic.service.AgentService;
import org.mainLogic.service.CommandQueue;
import org.mainLogic.service.executor.Executor;
import org.mainLogic.service.Publisher;
import org.mainLogic.service.message.AgentStatusMessage;
import org.mainLogic.service.message.Message;

import java.io.IOException;
import java.util.*;

public class GameLoop implements Runnable {

    private final Publisher publisher;
    private final CommandQueue commandQueue;
    private final AgentService agentService;
    private final List<Executor> executorList;
    private final ObjectMapper objectMapper;
    private final short playersAmount;
    private final long rebornTime;


    public GameLoop (final short playersAmount,
                     final long rebornTime,
                     final AgentService agentService,
                     final Publisher publisher,
                     final CommandQueue commandQueue,
                     final List<Executor> executorList,
                     final ObjectMapper objectMapper
    ) throws InterruptedException {
        this.playersAmount = playersAmount;
        this.rebornTime = rebornTime;
        this.agentService = agentService;
        this.publisher = publisher;
        this.commandQueue = commandQueue;
        this.executorList = executorList;
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
        try {
            return objectMapper.writeValueAsBytes(agentStatusMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void process(byte[] next) {
        for(Executor executor : executorList){
            if(executor.accept(next)) {
                break;
            }
        }
    }


    private int random(final int min ,final int max){
        return (int)Math.floor(Math.random() * (max - min) + min);
    }
}

