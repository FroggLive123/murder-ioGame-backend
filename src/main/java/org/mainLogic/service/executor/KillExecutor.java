package org.mainLogic.service.executor;

import org.mainLogic.entity.AgentEntity;
import org.mainLogic.service.AgentService;
import org.mainLogic.service.SocketManager;

public class KillExecutor implements Executor {
    private static final byte CMD_TYPE = 3;
    private final AgentService agentService;
    private final SocketManager socketManager;

    public KillExecutor(final AgentService agentService, final SocketManager socketManager) {
        this.agentService = agentService;
        this.socketManager = socketManager;
    }

    @Override
    public boolean accept(byte[] cmd) {
        if(cmd[0] != CMD_TYPE) {
            return false;
        }

        final short userId = byteToInt(cmd[1], cmd[2]);
        final AgentEntity agent = agentService.getAgent(userId);
        final byte direction = cmd[3];

        agentService.kill(direction, agent.getUuid());

        return true;
    }


    public final short byteToInt(byte firstPart, byte secondPart) {
        return (short) (firstPart << 8  | (secondPart & 0xff));
    }
}
