package com.example.chitchat.datamodels;

public class Postcommentimage {

    private String UserComment, Userpostimage;

    public Postcommentimage() {
    }


    public Postcommentimage(String userComment, String userpostimage) {
        UserComment = userComment;
        Userpostimage = userpostimage;
    }


    public String getUserComment() {
        return UserComment;
    }

    public void setUserComment(String userComment) {
        UserComment = userComment;
    }

    public String getUserpostimage() {
        return Userpostimage;
    }

    public void setUserpostimage(String userpostimage) {
        Userpostimage = userpostimage;
    }
}
