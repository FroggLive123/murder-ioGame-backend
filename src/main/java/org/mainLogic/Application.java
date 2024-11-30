package org.mainLogic;

import org.mainLogic.entity.AgentEntity;
import org.mainLogic.repository.AgentRepository;
import org.mainLogic.service.AgentService;
import org.mainLogic.service.Server;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Application {
    public Application() {

    }

    public void run(int amountOfPlayers) throws InterruptedException {

        ArrayList<AgentEntity> agentList = new ArrayList<>();
        ArrayList<Server> agentUUIDRepository = new ArrayList<>();
        //Initialization of agent array with UUID
        AgentRepository agentRepository = new AgentRepository();
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

        AgentService agentService = new AgentService(agentRepository, amountOfPlayers);

//        how it have to be
//        List = new ArrayList<>()

        //ask about PROBLEM
        Map<String, AgentEntity> agentHashMap = new HashMap<String,AgentEntity> ();

//        agentHashMap = agentRepository.createUUIDRepository(agentList);


        agentRepository.getAgentRepository();

        //Start server
//        Server server = new Server();
//        server.start();
    }
}

