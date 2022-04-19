package com.example.managetution;

public class PostSaveDetails {
    private String userId, date,time,postDetails,username,dateTime,location,postId,postStatus;

    public PostSaveDetails() {

    }

    public PostSaveDetails(String userId, String date, String time, String postDetails, String username,String dateTime,String location,String postId,String postStatus) {
        this.userId = userId;
        this.date = date;
        this.time = time;
        this.postDetails = postDetails;
        this.username = username;
        this.dateTime = dateTime;
        this.location = location;
        this.postId = postId;
        this.postStatus = postStatus;
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(String postStatus) {
        this.postStatus = postStatus;
    }
}
