package com.mindgeek.samplecode.datatransferobject.request;


import com.mindgeek.samplecode.domainobject.AppUserPrincipal;

import java.io.Serializable;

public class JwtResponse implements Serializable {
    private final String jwttoken;
    private final AppUserPrincipal user;

    public JwtResponse(String jwttoken, AppUserPrincipal user) {
        this.jwttoken = jwttoken;
        this.user = user;
    }

    public String getToken() {
        return this.jwttoken;
    }

    public AppUserPrincipal getUser() {
        return user;
    }
}
