package org.mainLogic.service.message;

import java.util.UUID;

public record KillCommand(
        String type,
        String user,
        int[] direction
) {
}
