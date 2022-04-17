package com.example.managetution;

public class ChatReference {
    private String firstName,lastName,tutorUserId,guardianUserName,tutorUserName;

    public ChatReference() {

    }

    public ChatReference(String firstName, String lastName, String tutorUserId, String guardianUserName, String tutorUserName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.tutorUserId = tutorUserId;
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

    public String getTutorUserId() {
        return tutorUserId;
    }

    public void setTutorUserId(String tutorUserId) {
        this.tutorUserId = tutorUserId;
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
