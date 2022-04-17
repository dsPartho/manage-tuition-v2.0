package com.example.managetution;

public class NotificationColabartion {
    public String guardianUserName,tutorUserName,type;

    public NotificationColabartion(String guardianUserName, String tutorUserName, String type) {
        this.guardianUserName = guardianUserName;
        this.tutorUserName = tutorUserName;
        this.type = type;
    }

    public NotificationColabartion() {

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
