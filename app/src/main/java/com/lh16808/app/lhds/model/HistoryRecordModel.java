package com.lh16808.app.lhds.model;

import java.io.Serializable;

/**
 * Created by admin on 2016/10/14.
 */
public class HistoryRecordModel implements Serializable {
    public String bq;
    /**
     * 特碼
     */
    public String tm;
    public String tmsx;
    /**
     * 平碼
     */
    public String z1m;
    public String z1sx;
    public String z2m;
    public String z2sx;
    public String z3m;
    public String z3sx;
    public String z4m;
    public String z4sx;
    public String z5m;
    public String z5sx;
    public String z6m;
    public String z6sx;

    public String getNewstime() {
        return newstime;
    }

    public void setNewstime(String newstime) {
        this.newstime = newstime;
    }

    public String newstime;

    public HistoryRecordModel(String bq, String tm, String tmsx, String z1m, String z1sx, String z2m, String z2sx, String z3m, String z3sx, String z4m, String z4sx, String z5m, String z5sx, String z6m, String z6sx, String newstime) {
        this.bq = bq;
        this.tm = tm;
        this.tmsx = tmsx;
        this.z1m = z1m;
        this.z1sx = z1sx;
        this.z2m = z2m;
        this.z2sx = z2sx;
        this.z3m = z3m;
        this.z3sx = z3sx;
        this.z4m = z4m;
        this.z4sx = z4sx;
        this.z5m = z5m;
        this.z5sx = z5sx;
        this.z6m = z6m;
        this.z6sx = z6sx;
        this.newstime = newstime;
    }

    public String getBq() {
        return bq;
    }

    public void setBq(String bq) {
        this.bq = bq;
    }

    public String getTm() {
        return tm;
    }

    public void setTm(String tm) {
        this.tm = tm;
    }

    public String getTmsx() {
        return tmsx;
    }

    public void setTmsx(String tmsx) {
        this.tmsx = tmsx;
    }

    public String getZ1m() {
        return z1m;
    }

    public void setZ1m(String z1m) {
        this.z1m = z1m;
    }

    public String getZ1sx() {
        return z1sx;
    }

    public void setZ1sx(String z1sx) {
        this.z1sx = z1sx;
    }

    public String getZ2m() {
        return z2m;
    }

    public void setZ2m(String z2m) {
        this.z2m = z2m;
    }

    public String getZ2sx() {
        return z2sx;
    }

    public void setZ2sx(String z2sx) {
        this.z2sx = z2sx;
    }

    public String getZ3m() {
        return z3m;
    }

    public void setZ3m(String z3m) {
        this.z3m = z3m;
    }

    public String getZ3sx() {
        return z3sx;
    }

    public void setZ3sx(String z3sx) {
        this.z3sx = z3sx;
    }

    public String getZ4m() {
        return z4m;
    }

    public void setZ4m(String z4m) {
        this.z4m = z4m;
    }

    public String getZ4sx() {
        return z4sx;
    }

    public void setZ4sx(String z4sx) {
        this.z4sx = z4sx;
    }

    public String getZ5m() {
        return z5m;
    }

    public void setZ5m(String z5m) {
        this.z5m = z5m;
    }

    public String getZ5sx() {
        return z5sx;
    }

    public void setZ5sx(String z5sx) {
        this.z5sx = z5sx;
    }

    public String getZ6m() {
        return z6m;
    }

    public void setZ6m(String z6m) {
        this.z6m = z6m;
    }

    public String getZ6sx() {
        return z6sx;
    }

    public void setZ6sx(String z6sx) {
        this.z6sx = z6sx;
    }

    @Override
    public String toString() {
        return "HistoryRecordModel{" +
                "bq='" + bq + '\'' +
                ", tm='" + tm + '\'' +
                ", tmsx='" + tmsx + '\'' +
                ", z1m='" + z1m + '\'' +
                ", z1sx='" + z1sx + '\'' +
                ", z2m='" + z2m + '\'' +
                ", z2sx='" + z2sx + '\'' +
                ", z3m='" + z3m + '\'' +
                ", z3sx='" + z3sx + '\'' +
                ", z4m='" + z4m + '\'' +
                ", z4sx='" + z4sx + '\'' +
                ", z5m='" + z5m + '\'' +
                ", z5sx='" + z5sx + '\'' +
                ", z6m='" + z6m + '\'' +
                ", z6sx='" + z6sx + '\'' +
                '}';
    }
}
