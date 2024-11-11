package com.padillatom.TAG_Board.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class GenericExceptionResponse extends RuntimeException {

    private final String message;
    private final HttpStatus httpStatus;
}
