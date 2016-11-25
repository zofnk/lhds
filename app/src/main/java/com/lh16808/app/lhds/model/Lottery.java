package com.lh16808.app.lhds.model;

public class Lottery {
    public String bq;
    public String zt;
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
    public String tm;
    public String tmsx;
    public String sxsj;
    public String xyqsx;
    public String xq;


    public boolean isOpen1 = true;
    public boolean isOpen2 = true;
    public boolean isOpen3 = true;
    public boolean isOpen4 = true;
    public boolean isOpen5 = true;
    public String title;
    public String xyq;
    public boolean isKJ;
    public static boolean GBJINRU;
    public static long RefreshTime;
    public static int Time = 5000;
    private static Lottery lottery = null;

    private Lottery() {
    }

    public static Lottery getLottery() {
        if (lottery == null) {
            lottery = new Lottery();
        }
        return lottery;
    }

    public void setData(String bq2, String zt2, String z1m, String z1sx2, String z2m2, String z2sx2, String z3m2,
                        String z3sx2, String z4m2, String z4sx2, String z5m2, String z5sx2, String z6m2, String z6sx2, String tm2,
                        String tmsx2, String sxsj2, String xyqsj, String xq2, String xyq2) {
        bq = bq2;
        zt = zt2;
        this.z1m = z1m;
        z1sx = z2sx2;
        z2m = z2m2;
        z2sx = z2sx2;
        z3m = z3m2;
        z3sx = z3sx2;
        z4m = z4m2;
        z4sx = z4sx2;
        z5m = z5m2;
        z5sx = z5sx2;
        z6m = z6m2;
        z6sx = z6sx2;
        tm = tm2;
        tmsx = tmsx2;
        sxsj = sxsj2;
        this.xyqsx = xyqsj;
        xq = xq2;
        xyq = xyq2;
    }

    @Override
    public String toString() {
        return "Lottery [bq=" + bq + ", zt=" + zt + ", z1m=" + z1m + ", z1sx=" + z1sx + ", z2m=" + z2m + ", z2sx="
                + z2sx + ", z3m=" + z3m + ", z3sx=" + z3sx + ", z4m=" + z4m + ", z4sx=" + z4sx + ", z5m=" + z5m
                + ", z5sx=" + z5sx + ", z6m=" + z6m + ", z6sx=" + z6sx + ", tm=" + tm + ", tmsx=" + tmsx + ", sxsj="
                + sxsj + ", xyqsx=" + xyqsx + ", xq=" + xq + ", isOpen1=" + isOpen1 + ", isOpen2=" + isOpen2
                + ", isOpen3=" + isOpen3 + ", isOpen4=" + isOpen4 + ", isOpen5=" + isOpen5 + ", title=" + title
                + ", xyq=" + xyq + "]";
    }

}
