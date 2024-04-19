package com.materiales.bascula.model;

import java.util.Collection;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;

public class CustomUserDetails extends User {

    private String fullName;
    private String initials;
    private String color;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
}

