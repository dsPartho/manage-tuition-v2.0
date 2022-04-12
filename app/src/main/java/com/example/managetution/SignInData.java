package com.example.managetution;

public class SignInData {
    public String email, pass,role;

    public SignInData() {

    }

    public SignInData(String email, String pass, String role) {
        this.email = email;
        this.pass = pass;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
