package org.mainLogic.service.message;

import java.util.UUID;

public record KillCommand(
        String dataType,
        String user,
        int[] direction
) {
}
