package com.materiales.bascula.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class Usuario {

    @Size(min = 4, max = 15,message = "size for firstname min 4 letters")
    private String firstName;
    private String lastName;
    @Email(message = "no valid email")
    private String email;

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}
