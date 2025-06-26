package com.juaracoding.pcmspringboot4.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import org.springframework.stereotype.Service;

public class User {

    @Pattern(regexp = "^[a-z0-9\\.]{8,16}$")
    @JsonProperty("user-name")
    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
