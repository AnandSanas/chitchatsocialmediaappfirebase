package com.example.chitchat.datamodels;

public class UserProfile {


    private String usernameprofile, useremailprofile, userphoneprfile, userageprofile, userImagepath;

    public UserProfile() {
    }

    public UserProfile(String usernameprofile, String useremailprofile, String userphoneprfile, String userageprofile, String userImagepath) {
        this.usernameprofile = usernameprofile;
        this.useremailprofile = useremailprofile;
        this.userphoneprfile = userphoneprfile;
        this.userageprofile = userageprofile;
        this.userImagepath = userImagepath;
    }

    public String getUsernameprofile() {
        return usernameprofile;
    }

    public void setUsernameprofile(String usernameprofile) {
        this.usernameprofile = usernameprofile;
    }

    public String getUseremailprofile() {
        return useremailprofile;
    }

    public void setUseremailprofile(String useremailprofile) {
        this.useremailprofile = useremailprofile;
    }

    public String getUserphoneprfile() {
        return userphoneprfile;
    }

    public void setUserphoneprfile(String userphoneprfile) {
        this.userphoneprfile = userphoneprfile;
    }

    public String getUserageprofile() {
        return userageprofile;
    }

    public void setUserageprofile(String userageprofile) {
        this.userageprofile = userageprofile;
    }

    public String getUserImagepath() {
        return userImagepath;
    }

    public void setUserImagepath(String userImagepath) {
        this.userImagepath = userImagepath;
    }
}
