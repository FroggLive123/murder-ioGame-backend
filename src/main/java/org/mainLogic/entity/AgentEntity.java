package org.mainLogic.entity;

import java.time.Instant;
import java.util.UUID;

public class AgentEntity {

    public UUID uuid;
    public float x;
    public float y;
    public boolean isBot = true;
    public boolean isAlive  = true;
    public long timeOfDead ;

    public AgentEntity(UUID uuid, float x, float y) throws InterruptedException {
        this.uuid = uuid;

        this.x = x;
        this.y = y;
    }

}
