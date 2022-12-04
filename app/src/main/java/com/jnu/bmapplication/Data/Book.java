package com.jnu.bmapplication.Data;

public class Book {

    private String title;
    private String ap;
    private String time;
    private String publi;
    private int resourceId;
    private String translator;

    public Book(String title, String ap, String publi, String time, int resourceId, String translator){
        this.title=title;
        this.ap=ap;
        this.publi=publi;
        this.time=time;
        this.resourceId=resourceId;
        this.translator=translator;
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

    public String getPubli() {
        return publi;
    }

    public void setPubli(String publi) {
        this.publi = publi;
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
    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

}
