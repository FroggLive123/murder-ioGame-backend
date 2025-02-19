package org.mainLogic.service.executors;

import com.fasterxml.jackson.databind.ser.AnyGetterWriter;
import jdk.dynalink.linker.LinkerServices;
import org.mainLogic.entity.AgentEntity;
import org.mainLogic.service.AgentService;

import java.util.Collection;
import java.util.List;

public class BotMoveExecutor implements Executor {
    private static final byte CMD_TYPE = 1;
    private final AgentService agentService;
    private final byte step;
    private final short xMax;
    private final short yMax;

    public BotMoveExecutor(final AgentService agentService, final byte step, final short xMax, final short yMax) {
        this.agentService = agentService;
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
            short x = (short) agent.getX();
            short y = (short) agent.getY();

            final byte direction = (byte) (1 + (byte)(Math. random() * 8));

            switch (direction) {
                case 1:
                    y -= step;
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


            agentService.move(x, y, agent.getUuid());
        }

        return true;
    }
}
