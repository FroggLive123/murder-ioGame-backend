package org.mainLogic;

import org.mainLogic.entity.AgentEntity;
import org.mainLogic.repository.AgentRepository;
import org.mainLogic.service.NotifyAgentSerice;
import org.mainLogic.service.Server;


import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class Application {

    private boolean isRunning = false;

    public Application() {
    }

    public void run(int amountOfPlayers) throws InterruptedException, IOException {

        if(isRunning){
            try {
                throw new Exception("Application is running!");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        isRunning = true;

        //init of agent agentRepository with UUID hashMap
        AgentRepository agentRepository = new AgentRepository();
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

        agentRepository.createUUIDRepository(agentList);

        //init agent services
        NotifyAgentSerice notifyAgentService = new NotifyAgentSerice();

        System.out.println(agentRepository.getAgentRepository());
//        //Start server
//        Server server = new Server(notifyAgentService);
//        server.start();
//
//        //start game loop
    }
}

