package com.example.managetution;

public class NotificationColabartion {
    public String guardianUserName,guardianUserId,type;

    public NotificationColabartion(String guardianUserName, String guardianUserId, String type) {
        this.guardianUserName = guardianUserName;
        this.guardianUserId = guardianUserId;
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

    public String getGuardianUserId() {
        return guardianUserId;
    }

    public void setGuardianUserId(String guardianUserId) {
        this.guardianUserId = guardianUserId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
