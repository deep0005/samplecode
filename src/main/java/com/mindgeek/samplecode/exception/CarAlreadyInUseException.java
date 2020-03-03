package com.mindgeek.samplecode.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Conflict while modifying car as it is still in use")
public class CarAlreadyInUseException extends Exception
{

    static final long serialVersionUID = -3387516993224229948L;


    public CarAlreadyInUseException(String message)
    {
        super(message);
    }

}
