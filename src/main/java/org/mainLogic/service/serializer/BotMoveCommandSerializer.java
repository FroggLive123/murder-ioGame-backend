package org.mainLogic.service.serializer;

import org.util.LittleEndian;

import java.util.Map;

public class BotMoveCommandSerializer {
    private static final String  DATA_TYPE = "botMove";
    private static final byte BYTE_SIZE = 1;

    public static byte[] apply(String message) {

        byte[] command = new byte[BYTE_SIZE];

        command[0] = 1;

        return command;
    }
}
