package org.mainLogic.gameLoop;

import org.mainLogic.entity.AgentEntity;
import org.mainLogic.service.AgentService;

import java.util.ArrayList;

public class GameLoop implements Runnable {

    ArrayList<AgentEntity> listOfBots = new ArrayList<AgentEntity>();
    int maxPlayers = 40;

    public GameLoop (AgentService agentService) throws InterruptedException {
    }

    @Override
    public void run() {
        while (true) {

            //Bot logic
//            for(int i = 0; i < maxPlayers; i++) {
//                if (Math.random() >= 0.3) {
//                    //check if alive
//                    listOfBots.get(i).changePosition((Math.random() * 1490) > 745, (Math.random() * 690) > 345);
//                }
//                if (Math.random() >= 0.9) {
//                    listOfBots.get(i).kill();
//                }
//            }


            //send to front

        }
    }
}
