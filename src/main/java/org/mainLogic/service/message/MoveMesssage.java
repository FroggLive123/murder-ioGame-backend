package org.mainLogic.service.message;

public record MoveMesssage(
        String dataType,
        int direction
) implements Message {
}