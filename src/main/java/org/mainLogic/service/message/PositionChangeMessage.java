package org.mainLogic.service.message;

import java.util.UUID;

public record PositionChangeMessage(
        int x,
        int y,
        UUID uuid
) implements Message {
}
