package com.shareversity.restModels;

import java.sql.Timestamp;

public class UserLogin {
    private String email;
    private String studentPassword;
    private String registrationKey;
    private Timestamp loginTime;
    private Timestamp keyExpiration;
    private boolean confirmed;

    public UserLogin(String email, String studentPassword, String registrationKey, Timestamp loginTime,
                         Timestamp keyExpiration, boolean confirmed) {
        this.email = email;
        this.studentPassword = studentPassword;
        this.registrationKey = registrationKey;
        this.loginTime = loginTime;
        this.keyExpiration = keyExpiration;
        this.confirmed = confirmed;
    }

    public UserLogin() { }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStudentPassword() {
        return studentPassword;
    }

    public void setStudentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }

    public String getRegistrationKey() {
        return registrationKey;
    }

    public void setRegistrationKey(String registrationKey) {
        this.registrationKey = registrationKey;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    public Timestamp getKeyExpiration() {
        return keyExpiration;
    }

    public void setKeyExpiration(Timestamp keyExpiration) {
        this.keyExpiration = keyExpiration;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
}