package com.mindgeek.samplecode.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "No records were found that matched given parameters")
public class ContentNotFoundException extends Exception
{

    static final long serialVersionUID = -3387516993224229948L;


    public ContentNotFoundException(String message)
    {
        super(message);
    }

}
