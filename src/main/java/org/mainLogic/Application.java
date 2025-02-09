package org.mainLogic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mainLogic.entity.AgentEntity;
import org.mainLogic.gameLoop.GameLoop;
import org.mainLogic.repository.AgentRepository;
import org.mainLogic.service.*;


import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

public class Application {

    private boolean isRunning = false;

    public Application() {
    }

    public void run(int amountOfPlayers) throws Exception {

        if(isRunning){
            try {
                throw new Exception("Application is running!");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        isRunning = true;

        //init of agent agentRepository with UUID hashMap
        ArrayList<AgentEntity> agentList = new ArrayList<>();

        for(int i = 0; i < amountOfPlayers; i++) {
            UUID uuid = UUID.randomUUID();

            int xMax  = 1490;
            int yMax = 690;

            float x = (float) Math.random() * xMax;
            float y = (float) Math.random() * yMax;

            AgentEntity agent = new AgentEntity(uuid, x, y);
            agentList.add(agent);
        }

        final AgentRepository agentRepository = new AgentRepository(agentList);
        final ObjectMapper objectMapper = new ObjectMapper();
        final SocketManager socketManager = new SocketManager();
        final Publisher publisher = new Publisher(socketManager, objectMapper);
        //init agent services

        DefaultAgentService defaultAgentService = new DefaultAgentService(agentRepository);
        NotifyAgentSerice notifyAgentSerice = new NotifyAgentSerice(defaultAgentService);

        //GameLoop start
        GameLoop gameLoop = new GameLoop(notifyAgentSerice);
        new Thread(gameLoop).start();

        //Start server
        Server server = new Server(notifyAgentSerice, socketManager, publisher, objectMapper);
        server.run();

        //start game loop
    }
}

