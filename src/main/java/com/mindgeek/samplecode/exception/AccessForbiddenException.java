package com.mindgeek.samplecode.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Forbidden")
public class AccessForbiddenException extends Exception{
    public AccessForbiddenException(String message)
    {
        super(message);
    }
}

