package com.jnu.bmapplication.Data;

public class Book {

    private String title;
    private String ap;
    private String time;
    private int resourceId;

    public Book(String title,String ap,String time,int resourceId){
        this.title=title;
        this.ap=ap;
        this.time=time;
        this.resourceId=resourceId;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAp(){return ap;}

    public void setAp(String ap) {
        this.ap = ap;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCoverResourceId() {
        return resourceId;
    }
    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

}
