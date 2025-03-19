package org.mainLogic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mainLogic.ai.BotAi;
import org.mainLogic.entity.AgentEntity;
import org.mainLogic.gameLoop.GameLoop;
import org.mainLogic.repository.AgentRepository;
import org.mainLogic.service.*;
import org.mainLogic.service.executor.*;
import org.mainLogic.service.serializer.CommandSerializer;
import org.mainLogic.service.serializer.KillCommandSerializer;
import org.mainLogic.service.serializer.MoveCommandSerializer;


import javax.smartcardio.CommandAPDU;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Application {
    private final long rebornTime;
    private final short playersAmount;
    private final short xMax;
    private final short yMax;
    private final byte step;
    private boolean isRunning = false;

    public Application(final long rebornTime, final short playersAmount, final short xMax, final short yMax, final byte step) {
        this.rebornTime = rebornTime;
        this.playersAmount = playersAmount;
        this.xMax = xMax;
        this.yMax = yMax;
        this.step = step;
    }

    public void run(short playersAmount) throws Exception {

        if(isRunning){
            try {
                throw new Exception("Application is running!");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        isRunning = true;

        //init of agent agentRepository with UUID hashMap
        ArrayList<AgentEntity> agentList = new ArrayList<>();

        for(int i = 0; i < playersAmount; i++) {

            short x = (short) (Math.random() * xMax);
            short y = (short) (Math.random() * yMax);

            AgentEntity agent = new AgentEntity(i, x, y);
            agentList.add(agent);
        }

        final ObjectMapper objectMapper = new ObjectMapper();
        final SocketManager socketManager = new SocketManager(playersAmount);
        final Publisher publisher = new Publisher(socketManager, objectMapper);
        final CommandQueue commandQueue = new CommandQueue(100);
        final AgentRepository agentRepository = new AgentRepository(agentList);
        final DefaultAgentService defaultAgentService = new DefaultAgentService(agentRepository);
        final NotifyAgentService notifyAgentService = new NotifyAgentService(defaultAgentService, objectMapper, publisher);
        final BotAi botAi = new BotAi(defaultAgentService);

        List<Executor> executorList = new ArrayList<>();

        BotMoveExecutor botMoveExecutor = new BotMoveExecutor(defaultAgentService,botAi , step, xMax, yMax);
        KillExecutor killExecutor = new KillExecutor(notifyAgentService, socketManager);
        RebornExecutor rebornExecutor = new RebornExecutor(defaultAgentService);
        UserMoveExecutor userMoveExecutor = new UserMoveExecutor(socketManager, defaultAgentService, xMax, yMax, step);

        List<CommandSerializer> serializerList = new ArrayList<>();

        KillCommandSerializer killCommandSerializer = new KillCommandSerializer();
        MoveCommandSerializer moveCommandSerializer = new MoveCommandSerializer();

        executorList.add(botMoveExecutor);
        executorList.add(killExecutor);
        executorList.add(rebornExecutor);
        executorList.add(userMoveExecutor);

        //GameLoop start
        GameLoop gameLoop = new GameLoop(playersAmount, rebornTime ,defaultAgentService, publisher, commandQueue , executorList, objectMapper);
        new Thread(gameLoop).start();

        //Start server
        Server server = new Server(notifyAgentService, socketManager, publisher, objectMapper, serializerList,  commandQueue);
        server.run();

        //start game loop
    }
}

