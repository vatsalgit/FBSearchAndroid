package com.example.vatsalshah.facebooksearchapp;

/**
 * Created by Vatsal Shah on 20-04-2017.
 */

public class PostItem
{
    private String date;
    private String picture;
    private String name;
    private String post;
    private String id;

    public void setId(String id)
    {
        this.id = id;
    }
    public String getId()
    {
        return id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void SetPost(String post)
    {
        this.post=post;
    }
    public String getPost()
    {
        return post;
    }


}









