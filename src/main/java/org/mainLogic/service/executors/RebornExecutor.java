package org.mainLogic.service.executors;

import org.mainLogic.service.AgentService;

public class RebornExecutor implements Executor {
    private static final byte CMD_TYPE = 4;
    private final AgentService agentService;

    public RebornExecutor(AgentService agentService) {
        this.agentService = agentService;
    }

    @Override
    public final boolean accept(final byte[] cmd) {
        if(cmd[0] != CMD_TYPE) {
            return false;
        }

        agentService.rebornAll();

        return true;
    }
}
