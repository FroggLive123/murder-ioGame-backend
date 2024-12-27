package org.mainLogic.service.message;

public class MoveCommand {

    private String dataType;
    private String UUID;
    private int direction; // what type has direction?????

    public MoveCommand(final String dataType, final String UUID,final int direction) {
        this.dataType = dataType;
        this.UUID = UUID;
        this.direction = direction;
    }

    public String getDataType() {
        return dataType;
    }

    public String getUUID() {
        return UUID;
    }

    public int getDirection() {
        return direction;
    }
}
