package com.mindgeek.samplecode.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Error while validating request parameters")
public class DataValidationException extends Exception
{

    static final long serialVersionUID = -3387516993224229948L;


    public DataValidationException(String message)
    {
        super(message);
    }

}
