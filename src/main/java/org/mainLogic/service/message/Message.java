package org.mainLogic.service.message;

public class Message {

    private String dataType;
    //UseobjectMaper to create json
    //create different messages

    public Message(final String dataType) {
        this.dataType = dataType;
    }

    public String getDataType() {
        return dataType;
    }
}
