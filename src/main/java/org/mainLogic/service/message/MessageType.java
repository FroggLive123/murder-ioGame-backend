package org.mainLogic.service.message;

public record MessageType(
        //MessageType record is used for finding datatype of message
    String dataType
) implements Message {
}
