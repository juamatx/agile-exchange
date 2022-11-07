package com.shareversity.restModels;

import java.util.Date;

public class Students {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date createDate;
    private String secretCode;
    private boolean isCodeVerified;

    public Students(int id, String firstName, String lastName,
                    String email, String password, Date createDate, String secretCode, boolean isCodeVerified) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.createDate = createDate;
        this.secretCode = secretCode;
        this.isCodeVerified = isCodeVerified;
    }

    public Students() {

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getSecretCode() {
        return secretCode;
    }

    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }

    public boolean getIsCodeVerified() {
        return isCodeVerified;
    }

    public void setIsCodeVerified(boolean codeVerified) {
        isCodeVerified = codeVerified;
    }
}

