package com.shareversity.restModels;

public class EmailVerification {
    private String email;
    private String securityCode;

    public EmailVerification(String email, String securityCode) {
        this.email = email;
        this.securityCode = securityCode;
    }

    public EmailVerification(){

    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }
}
