package com.example.managetution;

public class ChatReference {
    public  String firstName,lastName,userId,guardianUserName,tutorUserName;

    public ChatReference() {

    }

    public ChatReference(String firstName, String lastName, String userId,String guardianUserName,String tutorUserName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userId = userId;
        this.guardianUserName = guardianUserName;
        this.tutorUserName = tutorUserName;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGuardianUserName() {
        return guardianUserName;
    }

    public void setGuardianUserName(String guardianUserName) {
        this.guardianUserName = guardianUserName;
    }

    public String getTutorUserName() {
        return tutorUserName;
    }

    public void setTutorUserName(String tutorUserName) {
        this.tutorUserName = tutorUserName;
    }
}
