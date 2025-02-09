package org.mainLogic.gameLoop;

import org.mainLogic.entity.AgentEntity;
import org.mainLogic.service.AgentService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class GameLoop implements Runnable {

    AgentService agentService;

    ArrayList<AgentEntity> listOfBots = new ArrayList<AgentEntity>();
    int maxPlayers = 40;
    long rebornTime = 180000;


    public GameLoop (AgentService agentService) throws InterruptedException {
    }

    @Override
    public void run() {
        while (true) {

            Collection<AgentEntity> agents = agentService.getAll();

            for(AgentEntity agent: agents) {
                if(agent.isAlive()){
                    continue;
                }

                if(System.currentTimeMillis() - agent.getTimeOfDead() >= rebornTime) {
                    agent.setAlive(true);
                }
            }

            for(AgentEntity agent: agents) {
                if(!agent.isBot() || !agent.isAlive()){
                    continue;
                }
                //There is place for problem with instant bot appearing and someone instant killing him
                agentService.move(random(1, 8), random(1, 8), agent.getUuid());

            }



            //send to front

        }
    }

    private int random(int min , int max){
        return (int)Math.floor(Math.random() * (max - min) + min);
    }
}
