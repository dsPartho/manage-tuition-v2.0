package com.example.managetution;

public class PostSaveDetails {
    private String userId, date,time,postDetails,username;

    public PostSaveDetails() {

    }

    public PostSaveDetails(String userId, String date, String time, String postDetails, String username) {
        this.userId = userId;
        this.date = date;
        this.time = time;
        this.postDetails = postDetails;
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPostDetails() {
        return postDetails;
    }

    public void setPostDetails(String postDetails) {
        this.postDetails = postDetails;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
