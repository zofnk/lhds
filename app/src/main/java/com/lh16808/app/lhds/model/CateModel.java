package com.lh16808.app.lhds.model;

/**
 * Created by Administrator on 2016/11/16.
 */

public class CateModel {

    String qishu;
    String url;
    String title;
    String type;
    String newstime;

    public CateModel(String qishu, String url, String title, String type, String newstime) {
        this.qishu = qishu;
        this.url = url;
        this.title = title;
        this.type = type;
        this.newstime = newstime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQishu() {
        return qishu;
    }

    public void setQishu(String qishu) {
        this.qishu = qishu;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNewstime() {
        return newstime;
    }

    public void setNewstime(String newstime) {
        this.newstime = newstime;
    }
}
