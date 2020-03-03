package com.mindgeek.samplecode.datatransferobject.request;

import com.mindgeek.samplecode.domainvalue.UserType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class JwtRequest implements Serializable {
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull(message="User Type is mandatory")
    private UserType userType;

    //need default constructor for JSON Parsing
    public JwtRequest() { }

    public JwtRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
