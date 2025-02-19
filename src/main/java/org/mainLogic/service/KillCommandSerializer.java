package org.mainLogic.service;

import org.mainLogic.service.message.KillMessage;

public class KillCommandSerializer implements CommandSerializer<KillMessage> {
    @Override
    public byte[] apply(KillMessage command) {
        return new byte[0];
    }


}
