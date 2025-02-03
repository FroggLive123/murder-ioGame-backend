package org.mainLogic.service.message;

import org.mainLogic.dto.AgentDTO;

import java.lang.reflect.Array;
import java.util.List;
import java.util.UUID;

public record AgentStatus(
        String dateType,
        List<AgentDTO> agents
) {
}
