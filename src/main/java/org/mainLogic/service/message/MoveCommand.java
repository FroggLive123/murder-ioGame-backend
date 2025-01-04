package org.mainLogic.service.message;

public record MoveCommand(
        String type,
        String user,
        int[] direction
) {
}