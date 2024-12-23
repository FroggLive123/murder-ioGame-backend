package org.mainLogic.service;

import java.util.UUID;

public class NotifyAgentService {

//  Have to be proxy of AgentService

    private AgentService agentService;

    public NotifyAgentService(AgentService agentService) {
        this.agentService = agentService;
    }

    public void  kill(UUID uuid, int direction) {
    }


}
