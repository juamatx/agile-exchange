package com.shareversity.restModels;

import java.sql.Timestamp;

public class UserLogin {
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String code;

    public UserLogin(String email, String code) {
        this.email = email;
        this.code = code;
    }





}