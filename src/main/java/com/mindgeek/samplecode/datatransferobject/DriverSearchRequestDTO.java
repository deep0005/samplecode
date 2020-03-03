package com.mindgeek.samplecode.datatransferobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mindgeek.samplecode.domainvalue.OnlineStatus;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DriverSearchRequestDTO implements Serializable{

    @JsonProperty("carAttributes")
    private CarDTO carDTO;

    private String username;

    private OnlineStatus onlineStatus;

    public CarDTO getCarDTO() {
        return carDTO;
    }

    public void setCarDTO(CarDTO carDTO) {
        this.carDTO = carDTO;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public OnlineStatus getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(OnlineStatus onlineStatus) {
        this.onlineStatus = onlineStatus;
    }
}
