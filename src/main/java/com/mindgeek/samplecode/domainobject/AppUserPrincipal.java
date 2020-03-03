package com.mindgeek.samplecode.domainobject;

import com.mindgeek.samplecode.domainvalue.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AppUserPrincipal implements UserDetails {

    private final User user;

    //

    public AppUserPrincipal(User user) {
        this.user = user;
    }

    //

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    public UserType getType() { return user.getType(); }

    public Long getId() { return user.getId(); }

    public String getFirstName(){
        return this.user.getFirstName();
    }

    public String getLastName(){
        return this.user.getLastName();
    }

    public String getProfilePicturePath() { return this.user.getProfilePicturePath(); }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("User"));
        return authorities;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    //
    @JsonIgnore
    public User getAppUser() {
        return user;
    }
}
