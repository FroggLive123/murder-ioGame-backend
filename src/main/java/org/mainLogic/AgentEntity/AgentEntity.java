package org.mainLogic.AgentEntity;

public class AgentEntity {

    private int id;
    private float x;
    private float y;
    private boolean isBot = true;
    public int xMax  = 1490;
    public int yMax = 690;


    public AgentEntity(int id) throws InterruptedException {

        this.id = id;

        x = (float) Math.random() * xMax;
        y = (float) Math.random() * yMax;

    }

    public int[][] getCoordinates() {

        return new int[(int) x][(int) y];
    }

    public void changePosition(boolean rightX, boolean upY){
        if((rightX == true) || x != xMax){
            x += 10;
        }else if(x >= 10) {
            x -= 10;
        }

        if((upY == true) || y != yMax){
            y += 10;
        }else if(y >= 10) {
            y -= 10;
        }
    }
}
