package org.mainLogic.service.executor;

import org.mainLogic.ai.BotAi;
import org.mainLogic.entity.AgentEntity;
import org.mainLogic.service.AgentService;
import org.mainLogic.service.NotifyAgentService;

import java.util.Collection;

public class BotMoveExecutor implements Executor {
    private static final byte CMD_TYPE = 1;
    private final AgentService agentService;
    private final BotAi botAi;
    private final byte step;
    private final short xMax;
    private final short yMax;

    public BotMoveExecutor(final AgentService agentService, final BotAi botAi, final byte step, final short xMax, final short yMax) {
        this.agentService = agentService;
        this.botAi = botAi;
        this.step = step;
        this.xMax = xMax;
        this.yMax = yMax;
    }

    @Override
    public boolean accept(byte[] cmd) {
        if(cmd[0] != CMD_TYPE) {
            return false;
        }

        final Collection<AgentEntity> agents = agentService.getAll();

        for(AgentEntity agent: agents ) {
            //Canvas max size 10,000 x 10,000,datatype short -32,768 to 32,767
            if(agent.isBot()) {
                botAi.moveBot(agent, step, xMax, yMax);
            }
        }

        return true;
    }
}
