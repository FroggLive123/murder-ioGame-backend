package org.mainLogic.service.message;


import java.util.UUID;

public record InitMessage(
        String datatype,
        int id
) implements Message {
}
