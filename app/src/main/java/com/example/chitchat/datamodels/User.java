package com.example.chitchat.datamodels;

public class User {

    public String fullname,email,localid;

    public User() {
    }

    public User(String fullname, String email, String localid) {
        this.fullname = fullname;
        this.email = email;
        this.localid = localid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocalid() {
        return localid;
    }

    public void setLocalid(String localid) {
        this.localid = localid;
    }
}
