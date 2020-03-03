package com.mindgeek.samplecode.datatransferobject.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiError {

    private HttpStatus statusType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;
    private Integer status;

    public ApiError() {
        timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus status) {
        this();
        this.status = status.value();
        this.statusType = status;
    }

    public ApiError(HttpStatus status, Throwable ex) {
        this();
        this.status = status.value();
        this.statusType = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status.value();
        this.statusType = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }



    public HttpStatus getStatusType() {
        return statusType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatusType(HttpStatus statusType) {
        this.statusType = statusType;
    }



    public void setStatus(HttpStatus status) {
        this.status = status.value();
        this.statusType = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }
}
