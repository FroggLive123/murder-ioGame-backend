package org.mainLogic.service.message;


import java.util.UUID;

public record InitMessage(
        String dataType,
        int id
) implements Message {
}
