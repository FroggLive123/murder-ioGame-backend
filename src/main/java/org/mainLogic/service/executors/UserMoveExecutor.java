package org.mainLogic.service.executors;

public class UserMoveExecutor implements Executor {
    private static final byte CMD_TYPE = 2;
    @Override
    public boolean accept(byte[] cmd) {
        if(cmd[0] != CMD_TYPE) {
            return false;
        }

        int userId ;

        return true;
    }
}
