package org.mainLogic.service.message;

public record ErrorMessage(
        String datatype,
        String errorReport
) {
}
