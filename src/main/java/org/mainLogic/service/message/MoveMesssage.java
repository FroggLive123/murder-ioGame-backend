package org.mainLogic.service.message;

public record MoveMesssage(
        String datatype,
        int direction
) implements Message {
}