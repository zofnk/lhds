package com.lh16808.app.lhds.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/11/8.
 */

public class ForumModel implements Serializable {
    private static final long serialVersionUID = 789545117151403546L;
    private String id;
    private String title;
    private String userpic;
    private String groupname;
    private String username;
    private String userfen;
    private String newstime;
    private String oncliclk;
    private int rnum;

    public int getRnum() {
        return rnum;
    }

    public void setRnum(int rnum) {
        this.rnum = rnum;
    }

    public String getUserpic() {
        return userpic;
    }

    public void setUserpic(String userpic) {
        this.userpic = userpic;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getUserfen() {
        return userfen+"分";
    }

    public void setUserfen(String userfen) {
        this.userfen = userfen;
    }

    public ForumModel(String id, String title, String userpic, String groupname, String username, String userfen, String newstime, String oncliclk, int rnum) {
        this.id = id;
        this.title = title;
        this.userpic = userpic;
        this.groupname = groupname;
        this.username = username;
        this.userfen = userfen;
        this.newstime = newstime;
        this.oncliclk = oncliclk;
        this.rnum = rnum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNewstime() {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
        Long aLong = System.currentTimeMillis();
        try {
            aLong = Long.valueOf(newstime) * 1000;
        } catch (NumberFormatException e00) {
        }
        long time = aLong;
        Date d1 = new Date(time);
        String format1 = format.format(d1);
        return format1;
    }

    public void setNewstime(String newstime) {
        this.newstime = newstime;
    }

    public String getOncliclk() {
        return oncliclk;
    }

    public void setOncliclk(String oncliclk) {
        this.oncliclk = oncliclk;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
