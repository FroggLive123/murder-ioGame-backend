package org.mainLogic.service.message;

import org.mainLogic.dto.AgentDTO;

import java.util.List;

public record AgentStatusMessage (
        String dateType,
        List<AgentDTO> agents
) implements Message {
}
