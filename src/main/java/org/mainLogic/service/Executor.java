package org.mainLogic.service;

public interface Executor {
    //Have to create executors for all commands like die,kill,reborn
    boolean accept(byte[] cmd);
}
