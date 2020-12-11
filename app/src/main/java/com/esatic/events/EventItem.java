package com.esatic.events;


public class EventItem {

    private String title;
    private String picture;
    private String time;

    public EventItem(String title, String picture, String time) {
        this.title= title;
        this.picture= picture;
        this.time= time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicture() {
        return this.picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString()  {
        return this.title+" (Date : "+ this.time+")";
    }
}
