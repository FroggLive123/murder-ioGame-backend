package org.mainLogic.service.message;

public record KillMessage(
        String dataType,
        String user,
        int[] direction
) implements Message {
}
