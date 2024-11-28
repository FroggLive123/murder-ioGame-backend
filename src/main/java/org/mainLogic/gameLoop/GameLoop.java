package org.mainLogic.gameLoop;

import org.mainLogic.entity.AgentEntity;

import java.util.ArrayList;
import java.util.UUID;

public class GameLoop {

    ArrayList<AgentEntity> listOfBots = new ArrayList<AgentEntity>();
    int maxPlayers = 40;

    public GameLoop () throws InterruptedException {


    }

    public void StartGameLoop() {

        while(true) {

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
