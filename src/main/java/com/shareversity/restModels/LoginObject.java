package com.shareversity.restModels;

public class LoginObject {

	 private String userEmail;
	 private String password;
	/**
	 * 
	 */
	public LoginObject() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param username
	 * @param password
	 */
	public LoginObject(String username, String password) {
		super();
		this.userEmail = username;
		this.password = password;
	}
	/**
	 * @return the username
	 */
	public String getUserEmail() {
		return userEmail;
	}
	/**
	 * @param userEmail the username to set
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	 
}
