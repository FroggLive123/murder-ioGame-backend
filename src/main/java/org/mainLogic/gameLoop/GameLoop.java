package org.mainLogic.gameLoop;

import org.mainLogic.AgentEntity.AgentEntity;

import java.util.ArrayList;

public class GameLoop {

    ArrayList<AgentEntity> listOfUnits = new ArrayList<AgentEntity>();
    int maxPlayers = 100;

    public GameLoop () throws InterruptedException {

        for(int i = 0; i < maxPlayers; i++){

            AgentEntity agent = new AgentEntity(i);

            listOfUnits.add(agent);
        }



    }

    public void StartGameLoop() {

        while(true) {
            for(int i = 0; i < maxPlayers; i++) {
                if (Math.random() >= 0.3) {
                    listOfUnits.get(i).changePosition((Math.random() * 1490) > 745, (Math.random() * 690) > 345);
                }
            }

            //send to front


        }

    }

}
