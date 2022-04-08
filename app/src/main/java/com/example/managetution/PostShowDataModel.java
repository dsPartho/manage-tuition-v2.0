package com.example.managetution;

public class PostShowDataModel {
   // int image ;
    String date, postDetails,time, userId,username;

    public PostShowDataModel() {

    }

    public PostShowDataModel(String date, String postDetails, String time, String userId, String username) {
        this.date = date;
        this.postDetails = postDetails;
        this.time = time;
        this.userId = userId;
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPostDetails() {
        return postDetails;
    }

    public void setPostDetails(String postDetails) {
        this.postDetails = postDetails;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
