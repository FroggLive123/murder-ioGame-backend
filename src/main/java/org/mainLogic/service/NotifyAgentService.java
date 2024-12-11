package org.mainLogic.service;

public class NotifyAgentService {
    // AgentService has to be privet class of NotifyAgentService
    private AgentService agentService;

    public NotifyAgentService(AgentService agentService) {
        this.agentService = agentService;
    }

}
