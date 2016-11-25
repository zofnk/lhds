package com.lh16808.app.lhds.marco;

import android.content.Context;


import com.lh16808.app.lhds.R;

import java.util.Random;


/**
 * 接口地址
 */
public class ApiConfig {
    private static final String TAG = "ApiConfig";
    private static final Random RANDOM = new Random();

    public static int getRandomCheeseDrawable() {
        switch (RANDOM.nextInt(5)) {
            default:
            case 0:
                return R.drawable.cheese_1;
            case 1:
                return R.drawable.cheese_2;
            case 2:
                return R.drawable.cheese_3;
            case 3:
                return R.drawable.cheese_4;
            case 4:
                return R.drawable.cheese_5;
        }
    }

    /**
     * 基础地址
     */
    public static String ALL; // ALL
    public static String BASE_URL; // 服务器
    public static String BASE_URL_IMG; // 服务器
    public static String BASE_URL_KJ; //历史开奖记录
    public static String BASE_URL_AD;//广告基地址
    public static String BASE_URL_ZL;// 文字资料url
    public static Boolean IS_LOG = true; // 是否开启Log打印

    public static void init(Context context) {
        ALL = context.getString(R.string.all_url);
        BASE_URL = context.getString(R.string.base_url);
        BASE_URL_AD = context.getString(R.string.base_url_ad);
        BASE_URL_IMG = context.getString(R.string.base_url_img);
        BASE_URL_KJ = context.getString(R.string.base_url_kj);
        BASE_URL_ZL = context.getString(R.string.base_url_zl);
    }

    /**
     * @param url 需要拼接的相对地址
     * @return 返回完整链接
     */
    public static String getBaseUrl(String url) {
        if (url == null || url.length() == 0) {
            return null;
        }

        if ((url.contains("http://") || url.contains("https://"))) {
            return url;
        }
        if (!url.startsWith("/")) {
            return BASE_URL + "/" + url;
        }
        return BASE_URL + url;
    }


    /**
     * @param url 需要拼接的相对地址
     * @return 返回完整链接
     */
    public static String getBaseUrlKj(String url) {
        if (url == null || url.length() == 0) {
            return null;
        }

        if ((url.contains("http://") || url.contains("https://"))) {
            return url;
        }
        if (!url.startsWith("/")) {
            return BASE_URL_KJ + "/" + url;
        }
        return BASE_URL_KJ + url;
    }

    /**
     * 广告地址拼接
     *
     * @param url 需要拼接的相对地址
     * @return 返回完整链接
     */
    public static String getBaseUrlAD(String url) {
        if (url == null || url.length() == 0) {
            return null;
        }

        if ((url.contains("http://") || url.contains("https://"))) {
            return url;
        }
        if (!url.startsWith("/")) {
            return BASE_URL_AD + "/" + url;
        }
        return BASE_URL_AD + url;
    }

    /**
     * 图片
     *
     * @param url 需要拼接的相对地址
     * @return 返回完整链接
     */
    public static String getImageUrl(String url) {
//        if (url == null || url.length() == 0) {
//            return null;
//        }
//
//        if (url.contains("http://") || url.contains("https://")) {
//            return url;
//        }
//        if (!url.startsWith("/")) {
//            return IMAGE_URL + "/" + url;
//        }
//        IMAGE_URL +
        return url;
    }

    /**
     * kj
     * 開獎的數據
     * "http://120.76.44.201:8090/ceshi.xml";
     */
    public static final String KJXML = "http://120.76.44.201:8090/ceshi.xml";
    public static final String XmlKJBM = "kjbm.xml";
    /**
     * ad
     * banner廣告
     */
    public static final String AD_SPKJ = "appad/spkjad/index.json";
    // 主页轮播：
    public static String AD_URL = "appad/cpzqad/index.json";
    /**
     * ad
     * 六合寻宝通栏:
     */
    public static final String AdKey = "appad/lhxb/index.json";
    /**
     * wap
     * 历史数据
     */
    public static final String HisData = "json/";
    /**
     * wap
     * 意见反馈
     */
    public static final String FeedBackURL = "e/enews/index.php";
    /**
     * wap
     */
    public static final String JSONURL = "e/extend/json/kjjson.php";
    /**
     * wap
     * 更多应用
     */
    public static final String APK_DOWDLAN = "json/moreapp.json";
    /**
     * wap
     * 贴子
     */
    public static final String JSON = "e/extend/json/json.php";
    //      论坛
    public static String BBS = "json/list/bbs.json";
    //      心水资料
    public static String XINSHUI = "json/list/xinshui.json";
    //    文字资料
    public static String WENZI = "json/list/wenzi.json";
    //     高手资料
    public static String GAOSHUO = "json/list/gaoshuo.json";
    //     公式资料
    public static String GONGSHI = "json/list/gongshi.json";
    //     精品杀项
    public static String SHAXIANG = "json/list/shaxiang.json";
    //      香港挂牌
    public static String XIANGGANG = "json/list/xianggang.json";
    //     全年资料
    public static String QUANNIAN = "json/list/quannian.json";
    //      六合属性
    public static String SHUXING = "json/list/shuxing.json";


    /**
     * wap
     * 开奖2
     */
    public static final String IosKJ = "e/extend/json/json.php?enews=IosKj";
    public static final String JsonXML = "http://120.76.44.201:8090/ceshi.json";

    /**
     * wap
     * 图库
     * 六合图库,,,
     * 66,彩色图库,,
     * 89,玄机彩图,,
     * 92,黑白图库,,
     * 65,全年图库,,
     */
    /**
     * 曾道人
     * 白小姐
     */
    public static final String XianJi = "e/extend/json/xjjson.php";

    /**
     * wab
     * 开奖下方的web
     */
    public static String KJURL = "kaijiang.php";
    // 开奖日期：
    public static String KaiJianRQURL = "appdate.php";
    public static String KaiJianTime = "e/extend/json/json.php?enews=Kjtime";
    public static String KJTime = "http://7xpt79.com1.z0.glb.clouddn.com/time.json";
    // 走势分析：
    public static String KaiJianFXURL = "e/extend/trend/";
    /**
     * wap
     * 用户
     */
    public static String USER = "e/member/doaction.php";
    /**
     * wap
     */
    public static String[] MYFAVA1 = {"Myfava", "Mybbs", "Myrep"};
    public static String[] MYFAVA2 = {"", "Mybbs1", "Myrep1"};
}
