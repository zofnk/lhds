package com.lh16808.app.lhds.model;

/**
 * Created by Administrator on 2016/7/29 0029.
 */
public class XuanJi {
    private String qishu;
    private String title;
    private String xianji;
    private String jieda;

    public XuanJi(String qishu, String title, String xianji, String jieda) {
        this.qishu = qishu;
        this.title = title;
        this.xianji = xianji;
        this.jieda = jieda;
    }

    public String getQishu() {
        return qishu;
    }

    public String getTitle() {
        return title;
    }

    public String getXianji() {
        return xianji;
    }

    public String getJieda() {
        return jieda;
    }

    public void setQishu(String qishu) {
        this.qishu = qishu;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setXianji(String xianji) {
        this.xianji = xianji;
    }

    public void setJieda(String jieda) {
        this.jieda = jieda;
    }
}
