package com.lh16808.app.lhds.other;

import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;

import com.lh16808.app.lhds.model.Lottery;
import com.lh16808.app.lhds.utils.dataUtils.DateFormatUtils;
import com.lh16808.app.lhds.utils.http.H;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;


import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016/11/14.
 */

public class KaiJianLoad {
    public Lottery lottery;
    OnNetData onNetData;

    public KaiJianLoad() {
        lottery = Lottery.getLottery();
    }

    public void setOnNetData(OnNetData onNetData) {
        this.onNetData = onNetData;
        initXmlData();
    }

    public interface OnNetData {
        void onScuss();

        void onError();

        void onTime();
    }

    public void initXmlData() {
        H.initXmlData(new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String s = new String(responseBody);
                int eventType;
                try {
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setInput(new StringReader(s));
                    eventType = parser.getEventType();
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        if (eventType == XmlPullParser.START_TAG) {
                            String name = parser.getName();
                            if (name.equals("kjbm")) {
                                lottery.bq = parser.getAttributeValue("", "bq");
                                lottery.zt = parser.getAttributeValue("", "zt");
                                lottery.z1m = parser.getAttributeValue("", "z1m");
                                lottery.z1sx = parser.getAttributeValue("", "z1sx");
                                lottery.z2m = parser.getAttributeValue("", "z2m");
                                lottery.z2sx = parser.getAttributeValue("", "z2sx");
                                lottery.z3m = parser.getAttributeValue("", "z3m");
                                lottery.z3sx = parser.getAttributeValue("", "z3sx");
                                lottery.z4m = parser.getAttributeValue("", "z4m");
                                lottery.z4sx = parser.getAttributeValue("", "z4sx");
                                lottery.z5m = parser.getAttributeValue("", "z5m");
                                lottery.z5sx = parser.getAttributeValue("", "z5sx");
                                lottery.z6m = parser.getAttributeValue("", "z6m");
                                lottery.z6sx = parser.getAttributeValue("", "z6sx");
                                lottery.tm = parser.getAttributeValue("", "tm");
                                lottery.tmsx = parser.getAttributeValue("", "tmsx");
                                lottery.sxsj = parser.getAttributeValue("", "sxsj");
                                lottery.xyqsx = parser.getAttributeValue("", "xyqsj");
                                lottery.xq = parser.getAttributeValue("", "xq");
                                lottery.xyq = parser.getAttributeValue("", "xyq");
                            }
                        }
                        eventType = parser.next();
                    }
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                time();
                onNetData.onScuss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                onNetData.onError();
            }
        });
    }

    public void time() {
        H.Time(new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    Lottery.RefreshTime = new JSONObject(new String(responseBody)).getLong("time");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onNetData.onTime();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                onNetData.onError();
            }
        });
    }
}
