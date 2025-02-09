package org.mainLogic.service.message;

public record MoveMesssage(
        String dataType,
        String user,
        int[] direction
) {
}