package org.mainLogic.gameLoop;

import org.mainLogic.entity.AgentEntity;
import org.mainLogic.service.AgentService;
import org.mainLogic.service.CommandQueue;
import org.mainLogic.service.executors.Executor;
import org.mainLogic.service.Publisher;

import java.io.IOException;
import java.util.*;

public class GameLoop implements Runnable {

    private final Publisher publisher;
    private final CommandQueue commandQueue;
    private final AgentService agentService;
    private final List<Executor> executors;

    ArrayList<AgentEntity> listOfBots = new ArrayList<AgentEntity>();
    int maxPlayers = 40;
    long rebornTime = 180000;


    public GameLoop (final AgentService agentService, final Publisher publisher, final CommandQueue commandQueue, List<Executor> executors) throws InterruptedException {
        this.agentService = agentService;
        this.publisher = publisher;
        this.commandQueue = commandQueue;
        this.executors = executors;
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

            final byte[] agentStatusMessage = formAgentStatusMessage();
            try {
                publisher.broadcastAll(agentStatusMessage);
            } catch (IOException e) {
//                throw new RuntimeException(e);
            }

        }
    }

    private void process(byte[] next) {
        for(Executor executor : executors){
            if(executor.accept(next)) {
                break;
            }
        }
    }

    private byte[] formAgentStatusMessage() {

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

