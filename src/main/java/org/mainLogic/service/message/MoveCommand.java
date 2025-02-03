package org.mainLogic.service.message;

public record MoveCommand(
        String dataType,
        String user,
        int[] direction
) {
}