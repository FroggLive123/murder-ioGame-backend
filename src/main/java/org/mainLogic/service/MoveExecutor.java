package org.mainLogic.service;

public class MoveExecutor implements Executor {
    private static final byte CMD_TYPE = 1;
    @Override
    public boolean accept(byte[] cmd) {
        if(cmd[0] != CMD_TYPE) {
            return false;
        }

        //Change Agent Pos

        return true;
    }
}
