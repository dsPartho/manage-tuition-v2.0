package com.example.managetution;

public class GuardianUsersByPartho {
    private String id, picture_URL, role,firstname,lastname,email,pass,location,gender, phoneNo;

    public GuardianUsersByPartho(){

    }

    public GuardianUsersByPartho(String id, String picture_URL, String role,String firstname, String lastname, String email, String pass, String location, String gender, String phoneNo) {
        this.id = id;
        this.picture_URL = picture_URL;
        this.role = role;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.pass = pass;
        this.location = location;
        this.gender = gender;
        this.phoneNo = phoneNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicture_URL() {
        return picture_URL;
    }

    public void setPicture_URL(String picture_URL) {
        this.picture_URL = picture_URL;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}