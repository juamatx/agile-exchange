package com.shareversity.restModels;

public class UserRegistrationDetails {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String secretCode;
    private boolean isCodeVerified;

    public UserRegistrationDetails(int id, String firstName, String lastName, String password
            , String email, String secretCode, boolean codeVerified) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.secretCode = secretCode;
        this.isCodeVerified = codeVerified;
    }

    public UserRegistrationDetails(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecretCode() {
        return secretCode;
    }

    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }

    public boolean isIsCodeVerified() {
        return isCodeVerified;
    }

    public void setIsCodeVerified(boolean codeVerified) {
        this.isCodeVerified = codeVerified;
    }
}

