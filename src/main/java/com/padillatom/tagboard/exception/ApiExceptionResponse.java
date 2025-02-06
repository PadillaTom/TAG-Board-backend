package com.padillatom.tagboard.exception;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class ApiExceptionResponse {

    private final String message;
    private final int httpStatus;
    private final ZonedDateTime timestamp = ZonedDateTime.now();
}
