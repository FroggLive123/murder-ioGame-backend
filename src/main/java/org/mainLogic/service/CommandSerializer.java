package org.mainLogic.service;

import java.util.Map;

public interface CommandSerializer {

    byte[] apply (Map<String, Object> message, short userId);

}
