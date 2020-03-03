package com.mindgeek.samplecode.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_REQUIRED, reason = "Driver has to be online in order to perform operation")
public class DriverOfflineException extends Exception
{

    static final long serialVersionUID = -3387516993224229948L;


    public DriverOfflineException(String message)
    {
        super(message);
    }

}
