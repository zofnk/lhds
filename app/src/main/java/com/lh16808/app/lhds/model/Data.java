package com.lh16808.app.lhds.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/18.
 */

public class Data {
    private static Data data;
    private String BBS;
    private String XINSHUI;
    private String WENZI;
    private String GAOSHUO;
    private String GONGSHI;
    private String SHAXIANG;
    private String XIANGGANG;
    private String QUANNIAN;
    private String SHUXING;

    private Data() {
    }

    public static Data getInstance() {
        if (data == null) {
            data = new Data();
        }
        return data;
    }

    public String getBBS() {
        return BBS;
    }

    public void setBBS(String BBS) {
        this.BBS = BBS;
    }

    public String getXINSHUI() {
        return XINSHUI;
    }

    public void setXINSHUI(String XINSHUI) {
        this.XINSHUI = XINSHUI;
    }

    public String getWENZI() {
        return WENZI;
    }

    public void setWENZI(String WENZI) {
        this.WENZI = WENZI;
    }

    public String getGAOSHUO() {
        return GAOSHUO;
    }

    public void setGAOSHUO(String GAOSHUO) {
        this.GAOSHUO = GAOSHUO;
    }

    public String getGONGSHI() {
        return GONGSHI;
    }

    public void setGONGSHI(String GONGSHI) {
        this.GONGSHI = GONGSHI;
    }

    public String getSHAXIANG() {
        return SHAXIANG;
    }

    public void setSHAXIANG(String SHAXIANG) {
        this.SHAXIANG = SHAXIANG;
    }

    public String getXIANGGANG() {
        return XIANGGANG;
    }

    public void setXIANGGANG(String XIANGGANG) {
        this.XIANGGANG = XIANGGANG;
    }

    public String getQUANNIAN() {
        return QUANNIAN;
    }

    public void setQUANNIAN(String QUANNIAN) {
        this.QUANNIAN = QUANNIAN;
    }

    public String getSHUXING() {
        return SHUXING;
    }

    public void setSHUXING(String SHUXING) {
        this.SHUXING = SHUXING;
    }

    public ArrayList<ForumModel> jxBBS() {
        ArrayList<ForumModel> listx = new ArrayList<>();
        try {
            JSONArray list = new JSONArray(BBS);
            int length = list.length();
            for (int i = 0; i < length; i++) {
                JSONObject jsonObject1 = list.getJSONObject(i);
                String id = jsonObject1.getString("sid");
                String title = jsonObject1.getString("title");
                String userpic = jsonObject1.getString("userpic");
                String groupname = jsonObject1.getString("groupname");
                String username = jsonObject1.getString("username");
                String userfen = jsonObject1.getString("userfen");
                String newstime = jsonObject1.getString("newstime");
                String onclick = jsonObject1.getString("onclick");
                int rnum = jsonObject1.getInt("rnum");
                ForumModel forumModel = new ForumModel(id, title, userpic, groupname, username, userfen, newstime, onclick, rnum);
                listx.add(forumModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        List<ForumModel> forumModels = listx.subList(20, 40);
        return listx;
    }
}
