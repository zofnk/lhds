package com.lh16808.app.lhds.model;

/**
 * Created by Administrator on 2016/11/11.
 */

public class ForumDetailModel {
    private int on;
    private String id;
    private String newstime;
    private String newstext;

    public ForumDetailModel(String newstext, int on, String id, String newstime) {
        this.newstext = newstext;
        this.on = on;
        this.id = id;
        this.newstime = newstime;
    }

    public int getOn() {
        return on;
    }

    public void setOn(int on) {
        this.on = on;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNewstime() {
        return newstime;
    }

    public void setNewstime(String newstime) {
        this.newstime = newstime;
    }

    public String getNewstext() {
        return newstext;
    }

    public void setNewstext(String newstext) {
        this.newstext = newstext;
    }
}
