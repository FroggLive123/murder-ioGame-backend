package org.mainLogic.gameLoop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mainLogic.entity.AgentEntity;
import org.mainLogic.service.AgentService;
import org.mainLogic.service.CommandQueue;
import org.mainLogic.service.executor.Executor;
import org.mainLogic.service.Publisher;
import org.mainLogic.service.serializer.BotMoveCommandSerializer;
import org.springframework.cache.interceptor.CacheAspectSupport;
import org.util.CustomOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
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

        byte[] buffer = new byte[4000];

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

            if(delta == 900) {
                Arrays.fill(buffer, (byte) 0);
            }

            if(delta == 10_000) {
            }

            if(delta == 1_000) {

                try {
                    createAgentStatusMessage(buffer);
                    publisher.broadcast(buffer);
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

    private void createAgentStatusMessage(byte[] buff) throws IOException {
        CustomOutputStream writer = new CustomOutputStream(buff);

        final Collection<AgentEntity> agents = agentService.getAll();

        writer.writeByte((byte) '{');
        writer.write("\"datatype\":\"state\",\"data\":\"");

        for(AgentEntity agent: agents){
            writer.write(String.valueOf(agent.getId()));
            writer.write(":");
            writer.write(String.valueOf(agent.isAlive()));
            writer.write(":");
            writer.write(String.valueOf(agent.getX()));
            writer.write(",");
            writer.write(String.valueOf(agent.getY()));
            writer.write(";");
        }

        writer.writeByte((byte) '"');
        writer.writeByte((byte) '}');
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

