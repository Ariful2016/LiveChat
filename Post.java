package com.creativeitinstitute.mychat.Model;

public class Post {

    String status,post_img,user_id,post_id;

    public Post(String status, String post_img, String user_id, String post_id) {
        this.status = status;
        this.post_img = post_img;
        this.user_id = user_id;
        this.post_id = post_id;
    }

    public Post() {
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPost_img() {
        return post_img;
    }

    public void setPost_img(String post_img) {
        this.post_img = post_img;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }
}
