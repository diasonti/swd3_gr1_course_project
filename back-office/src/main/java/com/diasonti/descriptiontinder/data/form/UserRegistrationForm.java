package com.diasonti.descriptiontinder.data.form;

import com.diasonti.descriptiontinder.config.validation.password.StrongPassword;
import com.diasonti.descriptiontinder.config.validation.username.UsernameAvailable;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;

public class UserRegistrationForm extends BaseForm {

    @Size(min = 1, max = 255, message = "username.length.error")
    @UsernameAvailable
    private String username;

    @StrongPassword
    private String password;

    @Size(min = 1, max = 255)
    private String passwordVerification;

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

    public String getPasswordVerification() {
        return passwordVerification;
    }

    public void setPasswordVerification(String passwordVerification) {
        this.passwordVerification = passwordVerification;
    }

    @AssertTrue(message = "passwords.not.match")
    public boolean isEqualPasswords() {
        return password.equals(passwordVerification);
    }
}
