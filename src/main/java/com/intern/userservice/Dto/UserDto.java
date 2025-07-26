package com.intern.userservice.Dto;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@Data
public class UserDto {

    int c_id;

    public String getPassword() {
        return password;
    }

    public UserDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public int getC_id() {
        return c_id;
    }

    public UserDto setC_id(int c_id) {
        this.c_id = c_id;
        return this;
    }

    private String email;
    private String password;
}
