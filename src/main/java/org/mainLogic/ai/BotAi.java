package org.mainLogic.ai;

import org.mainLogic.entity.AgentEntity;
import org.mainLogic.service.AgentService;

public class BotAi {
    private final AgentService agentService;


    public BotAi(AgentService agentService) {
        this.agentService = agentService;
    }

    public void moveBot(final AgentEntity agent, final byte step, final short xMax, final short yMax) {
        byte aiValue = (byte) (Math.random() * 10);
        byte movementOpportunity = (byte) (Math.random() * 10);

        if(aiValue >= movementOpportunity) {
            short x = (short) agent.getX();
            short y = (short) agent.getY();

            final byte direction = (byte) (1 + (byte) (Math.random() * 8));

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


            agentService.move(x, y, agent.getId());
        }
    }
}
