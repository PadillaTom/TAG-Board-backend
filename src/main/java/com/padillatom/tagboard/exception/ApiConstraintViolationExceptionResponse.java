package com.padillatom.tagboard.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class ApiConstraintViolationExceptionResponse {

    private String message;
    private int httpStatus;
    private ZonedDateTime timestamp;
    private List<String> errors;
}
