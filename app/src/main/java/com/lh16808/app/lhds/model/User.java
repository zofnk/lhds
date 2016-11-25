package com.lh16808.app.lhds.model;

public class User {

    private String tx;
    private String hym;
    private String js;
    private String dj;
    private String rnd;
    private static User user = null;
    private String userid;
    private String set_cookie;
    private String data_cookie;
    private String userpic;
    private String truename;
    private String phone;
    private String weixin;
    private String oicq;

    public static void setUser(User user) {
        User.user = user;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getOicq() {
        return oicq;
    }

    public void setOicq(String oicq) {
        this.oicq = oicq;
    }

    private User() {

    }

    public void setUser(String tx, String hym, String js, String dj, String rnd, String userid) {
        this.tx = tx;
        this.hym = hym;
        this.js = js;
        this.dj = dj;
        this.rnd = rnd;
        this.userid = userid;
    }

    public static User getUser() {
        if (user == null) {
            user = new User();
            return user;
        }
        return user;
    }

    public static void setUser() {
        if (user != null) {
            user = null;
        }
    }

    public String getTx() {
        return tx;
    }

    public void setTx(String tx) {
        this.tx = tx;
    }

    public String getHym() {
        return hym;
    }

    public void setHym(String hym) {
        this.hym = hym;
    }

    public String getJs() {
        return js;
    }

    public void setJs(String js) {
        this.js = js;
    }

    public String getDj() {
        return dj;
    }

    public void setDj(String dj) {
        this.dj = dj;
    }

    public String getRnd() {
        return rnd;
    }

    public void setRnd(String rnd) {
        this.rnd = rnd;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getSet_cookie() {
        return set_cookie;
    }

    public void setSet_cookie(String set_cookie) {
        this.set_cookie = set_cookie;
    }

    public String getData_cookie() {
        return data_cookie;
    }

    public void setData_cookie(String data_cookie) {
        this.data_cookie = data_cookie;
    }

    public String getUserpic() {
        return userpic;
    }

    public void setUserpic(String userpic) {
        this.userpic = userpic;
    }

}
