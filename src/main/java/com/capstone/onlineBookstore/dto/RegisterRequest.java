package com.capstone.onlineBookstore.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * The type Register request.
 */
public class RegisterRequest {
    @NotBlank @Size(min = 2, max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Email @NotBlank
    private String email;

    @NotBlank @Size(min = 8, max = 100)
    private String password;

    /**
     * Gets first name.
     *
     * @return the first name
     */
// getters & setters
    public String getFirstName() {return firstName;}

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {this.firstName = firstName;}

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {return lastName;}

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {this.lastName = lastName;}

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {return email;}

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {this.email = email;}

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {return password;}

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {this.password = password;}
}
