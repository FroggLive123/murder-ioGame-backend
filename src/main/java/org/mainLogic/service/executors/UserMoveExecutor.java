package org.mainLogic.service.executors;

import org.mainLogic.entity.AgentEntity;
import org.mainLogic.service.AgentService;
import org.mainLogic.service.SocketManager;

import java.net.Socket;
import java.util.Optional;

public class UserMoveExecutor implements Executor {
    private static final byte step = 10;
    private static final byte CMD_TYPE = 2;
    private final SocketManager socketManager;
    private final AgentService agentService;
    private final short xMax;
    private final short yMax;

    public UserMoveExecutor(final SocketManager socketManager, final AgentService agentService,final short xMax,final short yMax) {
        this.socketManager = socketManager;
        this.agentService = agentService;
        this.xMax = xMax;
        this.yMax = yMax;
    }

    @Override
    public final boolean accept(final byte[] cmd) {
        if(cmd[0] != CMD_TYPE) {
            return false;
        }

        final short userId = byteToInt(cmd[1], cmd[2]);
        final Optional<Socket> socket = socketManager.get(userId);

        if(socket.isPresent()) {
            final byte direction = cmd[3];
            final AgentEntity agent = agentService.getAgent(socket);
            //Canvas max size 10,000 x 10,000,datatype short -32,768 to 32,767
            short x = (short) agent.getX();
            short y = (short) agent.getY();

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

    public final short byteToInt(byte firstPart, byte secondPart) {
        return (short) (firstPart << 8  | (secondPart & 0xff));
    }
}
