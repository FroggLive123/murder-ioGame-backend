package org.mainLogic.dto;

import java.util.UUID;

public record AgentDTO(
        UUID uuid,
        int[] position,
        boolean alive
) {
}
