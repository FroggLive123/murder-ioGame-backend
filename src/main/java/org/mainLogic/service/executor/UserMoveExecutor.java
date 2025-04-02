package org.mainLogic.service.executor;

import org.mainLogic.entity.AgentEntity;
import org.mainLogic.service.AgentService;
import org.mainLogic.service.SocketManager;

public class UserMoveExecutor implements Executor {
    private final byte step;
    private static final byte CMD_TYPE = 2;
    private final AgentService agentService;
    private final short xMax;
    private final short yMax;

    public UserMoveExecutor(final AgentService agentService,final short xMax,final short yMax, final byte step) {
        this.agentService = agentService;
        this.xMax = xMax;
        this.yMax = yMax;
        this.step = step;
    }

    @Override
    public final boolean accept(final byte[] cmd) {
        if(cmd[0] != CMD_TYPE) {
            return false;
        }

        final short userId = byteToInt(cmd[1], cmd[2]);
        final AgentEntity agent = agentService.getAgent(userId);

        if(agent != null) {
            final byte direction = cmd[3];
            //Canvas max size 10,000 x 10,000,datatype short -32,768 to 32,767
            short x = (short) agent.getX();
            short y = (short) agent.getY();

            switch (direction) {
                case 1:
                    y = (short) (y - step);
                    break;
                case 2:
                    y -= (short) (0.3 * step);
                    x += (short) (0.3 * step);
                    break;
                case 3:
                    x += step;
                    break;
                case 4:
                    y += (short) (0.3 * step);
                    x += (short) (0.3 * step);
                    break;
                case 5:
                    y += step;
                    break;
                case 6:
                    y += (short) (0.3 * step);
                    x -= (short) (0.3 * step);
                    break;
                case 7:
                    x -= step;
                    break;
                case 8:
                    y -= (short) (0.3 * step);
                    x -= (short) (0.3 * step);
            }

            x = (short) Math.max(0, Math.min(x, xMax));
            y = (short) Math.max(0, Math.min(y, yMax));


            agentService.move(x, y, agent.getId());
        }


        return true;
    }

    public final short byteToInt(byte firstPart, byte secondPart) {
        return (short) (firstPart << 8  | (secondPart & 0xff));
    }
}
