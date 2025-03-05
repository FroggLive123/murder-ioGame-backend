package org.mainLogic.service.serializer;

import org.util.LittleEndian;

import java.util.Map;

public class MoveCommandSerializer implements CommandSerializer {
    private static final String  DATA_TYPE = "move";
    private static final byte BYTE_SIZE = 4;
    @Override
    public byte[] apply(Map<String, Object> message, short userId) {
        if (!message.get("datatype").equals(DATA_TYPE)) {
            return null;
        }

        byte[] command = new byte[BYTE_SIZE];

        command[0] = 2;
        byte[] x = LittleEndian.toByteArray(userId);
        command[1] = x[0];
        command[2] = x[1];
        command[3] = (byte) message.get("direction");

        return command;
    }
}
