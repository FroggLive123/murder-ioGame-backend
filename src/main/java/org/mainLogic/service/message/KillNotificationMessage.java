package org.mainLogic.service.message;

import java.util.UUID;

public record KillNotificationMessage(
        String datatype,
        int id,
        int direction
) implements Message {
}
