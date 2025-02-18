package org.mainLogic.service.executors;

import org.mainLogic.service.SocketManager;

public interface Executor {
    //Have to create executors for all commands like die,kill,reborn
    boolean accept(byte[] cmd);
}
