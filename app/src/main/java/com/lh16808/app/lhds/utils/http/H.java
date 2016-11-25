package com.lh16808.app.lhds.utils.http;

import com.lh16808.app.lhds.marco.ApiConfig;
import com.lh16808.app.lhds.marco.Constants;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by Administrator on 2016/10/19.
 */

public class H {
    public static void upAPP(AsyncHttpResponseHandler a) {
        RequestParams params = new RequestParams();
        params.add(Constants.URL_KEY, "Sz");

        AsyncHttpClientUtils.getInstance().post(ApiConfig.getBaseUrl(ApiConfig.JSONURL), params, a);
    }

    public static void initXmlData(AsyncHttpResponseHandler a) {
        AsyncHttpClientUtils.getInstance().get(ApiConfig.getBaseUrlKj(ApiConfig.XmlKJBM), a);
//        AsyncHttpClientUtils.getInstance().get(ApiConfig.KJXML, a);
    }

    public static void HisData(String year, AsyncHttpResponseHandler a) {
        AsyncHttpClientUtils.getInstance().get(ApiConfig.getBaseUrl(ApiConfig.HisData) + year + ".json", a);
    }

    public static void ADURL(AsyncHttpResponseHandler a) {
        AsyncHttpClientUtils.getInstance().get(ApiConfig.getBaseUrlAD(ApiConfig.AdKey), a);
    }

    public static void downloadAPP(AsyncHttpResponseHandler a) {
        AsyncHttpClientUtils.getInstance().get(ApiConfig.getBaseUrl(ApiConfig.APK_DOWDLAN), a);
    }

    public static void feedBack(RequestParams p, AsyncHttpResponseHandler a) {
        AsyncHttpClientUtils.getInstance().post(ApiConfig.getBaseUrl(ApiConfig.FeedBackURL), p, a);
    }

    public static void adBanner(int a, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        switch (a) {
            default:
            case 0:
                AsyncHttpClientUtils.getInstance().get(ApiConfig.getBaseUrlAD(ApiConfig.AD_SPKJ), asyncHttpResponseHandler);
                break;
            case 1:
                AsyncHttpClientUtils.getInstance().get(ApiConfig.getBaseUrlAD(ApiConfig.AD_URL), asyncHttpResponseHandler);
                break;

        }
    }

    public static void ForumData(RequestParams params, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        AsyncHttpClientUtils.getInstance().post(ApiConfig.getBaseUrl(ApiConfig.JSON), params, asyncHttpResponseHandler);
    }

    public static void LOADKJ(AsyncHttpResponseHandler a) {
//        AsyncHttpClientUtils.getInstance().get(ApiConfig.getBaseUrl(ApiConfig.IosKJ), a);
        AsyncHttpClientUtils.getInstance().get(ApiConfig.JsonXML, a);
    }

    public static void TuKu(RequestParams params, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        AsyncHttpClientUtils.getInstance().post(ApiConfig.getBaseUrl(ApiConfig.JSON), params, asyncHttpResponseHandler);
    }

    public static void XianJi(RequestParams params, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        AsyncHttpClientUtils.getInstance().post(ApiConfig.getBaseUrl(ApiConfig.XianJi), params, asyncHttpResponseHandler);
    }

    public static void Time(AsyncHttpResponseHandler asyncHttpResponseHandler) {
        AsyncHttpClientUtils.getInstance().get(ApiConfig.getBaseUrl(ApiConfig.KaiJianTime), asyncHttpResponseHandler);
//        AsyncHttpClientUtils.getInstance().get(ApiConfig.getBaseUrl(ApiConfig.KJTime), asyncHttpResponseHandler);
    }

    public static void BBS(AsyncHttpResponseHandler asyncHttpResponseHandler) {
        AsyncHttpClientUtils.getInstance().get(ApiConfig.getBaseUrl(ApiConfig.BBS), asyncHttpResponseHandler);
    }

    public static void ZILIAO(int a, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        switch (a) {
            default:
            case 0:
                AsyncHttpClientUtils.getInstance().get(ApiConfig.getBaseUrl(ApiConfig.XINSHUI), asyncHttpResponseHandler);
                break;
            case 1:
                AsyncHttpClientUtils.getInstance().get(ApiConfig.getBaseUrl(ApiConfig.WENZI), asyncHttpResponseHandler);
                break;
            case 2:
                AsyncHttpClientUtils.getInstance().get(ApiConfig.getBaseUrl(ApiConfig.GAOSHUO), asyncHttpResponseHandler);
                break;
            case 3:
                AsyncHttpClientUtils.getInstance().get(ApiConfig.getBaseUrl(ApiConfig.GONGSHI), asyncHttpResponseHandler);
                break;
            case 4:
                AsyncHttpClientUtils.getInstance().get(ApiConfig.getBaseUrl(ApiConfig.SHAXIANG), asyncHttpResponseHandler);
                break;
            case 5:
                AsyncHttpClientUtils.getInstance().get(ApiConfig.getBaseUrl(ApiConfig.XIANGGANG), asyncHttpResponseHandler);
                break;
            case 6:
                AsyncHttpClientUtils.getInstance().get(ApiConfig.getBaseUrl(ApiConfig.QUANNIAN), asyncHttpResponseHandler);
                break;
            case 7:
                AsyncHttpClientUtils.getInstance().get(ApiConfig.getBaseUrl(ApiConfig.SHUXING), asyncHttpResponseHandler);
                break;
        }

    }

    public static void USER(RequestParams params, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        AsyncHttpClientUtils.getInstance().post(ApiConfig.getBaseUrl(ApiConfig.USER), params, asyncHttpResponseHandler);
    }

}
