package org.mainLogic.gameLoop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mainLogic.entity.AgentEntity;
import org.mainLogic.service.AgentService;
import org.mainLogic.service.CommandQueue;
import org.mainLogic.service.executor.Executor;
import org.mainLogic.service.Publisher;
import org.mainLogic.service.serializer.BotMoveCommandSerializer;
import org.util.CustomOutputStream;

import java.io.IOException;
import java.io.OutputStream;
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
        int delta = 0;

        byte[] buffer = new byte[1000];

        while (true) {
            delta++;

            if(delta == 10_000) {
                moveBots();
                delta = 0;
            }

            for(int i = 0; i < 100; i++){
                if(!commandQueue.hasNext()){
                    break;
                }

                process(commandQueue.next());
            }

            if(delta == 10_000) {
            }

            if(delta == 1_000) {

                try {
                    sendAgentStatus(buffer);
                } catch (IOException e) {
//                throw new RuntimeException(e);
                }
            }
        }
    }

    private void moveBots() {
        final byte[] command = BotMoveCommandSerializer.apply("botMove");
        commandQueue.put(command);
    }

    private void sendAgentStatus(byte[] buff) throws IOException {
        CustomOutputStream writer = new CustomOutputStream(buff);

        final List<AgentEntity> agentList = List.copyOf(agentService.getAll());

        //Todo need to be checked
        //Splits all agents into smaller arrays of 10 and sends their status until it sends an updated status of all agents
        final int messageId = (int) System.currentTimeMillis() % 100_000;

        int currentAgent = 0;
        final int lastIndex = agentList.size() - 1;

        while (currentAgent < lastIndex) {
            writer.writeByte((byte) '{');
            writer.write("\"id\":" + messageId + ",\"datatype\":\"state\",\"data\":\"");

            for(int i = 0; i < 10; i++) {
                if(currentAgent > lastIndex) {
                    break;
                }

                AgentEntity agent = agentList.get(currentAgent);

                writer.write(String.valueOf(agent.getId()));
                writer.write(":");
                writer.write(String.valueOf(agent.isAlive()));
                writer.write(":");
                writer.write(String.valueOf(agent.getX()));
                writer.write(",");
                writer.write(String.valueOf(agent.getY()));
                writer.write(";");

                currentAgent += 1;
            }

            writer.writeByte((byte) '"');
            writer.writeByte((byte) '}');

            publisher.broadcast(buff);

            writer.reset();
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

