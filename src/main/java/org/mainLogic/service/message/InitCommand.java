package org.mainLogic.service.message;


import java.util.UUID;

public record InitCommand(
        String dataType,
        String hash,
        UUID uuid
) {
}
