package com.mindgeek.samplecode.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Already Exists")
public class DuplicateRecordException extends Exception {

    public DuplicateRecordException(String message)
    {
        super(message);
    }

}

