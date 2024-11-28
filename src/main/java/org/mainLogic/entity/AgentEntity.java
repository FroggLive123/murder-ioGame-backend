package org.mainLogic.entity;

import java.util.UUID;

public class AgentEntity {

    public UUID uuid;
    private float x;
    private float y;
    private boolean isBot = true;
    public boolean isAlive  = true;


    public AgentEntity(UUID uuid, float x, float y) throws InterruptedException {



        //separate to AgentEntity (  AgentEntity is just object with no logic )  and AgentService ( all functions ), AgentRepository ( array with all agents )

        this.uuid = uuid;

        this.x = x;
        this.y = y;
    }

    //move to Service
//    public int[][] getCoordinates() {
//
//        return new int[(int) x][(int) y];
//    }

//    move to Service
//    public void changePosition(boolean rightX, boolean upY){
//        if((rightX == true) || x != xMax){
//            x += 10;
//        }else if(x >= 10) {
//            x -= 10;
//        }
//
//        if((upY == true) || y != yMax){
//            y += 10;
//        }else if(y >= 10) {
//            y -= 10;
//        }
//    }
//
//    public void kill() {
//    }
//
//    public void die() {
//        //getTime time
//        isAlive = false;
//    }
//
//    public void reborn(){
//        //check time in gameLoop
//    }

}
