package com.mindgeek.samplecode.datatransferobject;

import com.mindgeek.samplecode.validator.ValidateLicensePlate;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


public class SelectCarRequestDTO implements Serializable {

    @NotNull(message = "User name cannot be null")
    private String username;

    @ValidateLicensePlate
    private String licensePlate;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
}
