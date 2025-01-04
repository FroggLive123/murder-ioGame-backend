package org.mainLogic.dto;

import java.util.UUID;

public record AgentDTO(
        UUID uuid,
        float[] position,
        boolean alive
) {
}
