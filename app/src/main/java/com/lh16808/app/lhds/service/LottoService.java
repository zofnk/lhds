package com.lh16808.app.lhds.service;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;

import com.lh16808.app.lhds.model.Lottery;
import com.lh16808.app.lhds.utils.AppLog;
import com.lh16808.app.lhds.utils.MyUtils;
import com.lh16808.app.lhds.utils.http.H;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;

import cz.msebera.android.httpclient.Header;

public class LottoService extends Service {
    private static final String TAG = LottoService.class.getName();
    private Lottery lottery;
    private boolean oldZT;
    LottoBinder mBinder;
    OnVideoPlayPosition onVideoPlayPosition;
    private String[] listZM = new String[7];
    private String[] listSX = new String[7];
    private String[] listAll = new String[7];
    private boolean isStop;

    public LottoService() {
        if (mBinder == null) {
            mBinder = new LottoBinder();
        }
        if (lottery == null) {
            lottery = Lottery.getLottery();
        }
    }

    int i;

    @Override
    public IBinder onBind(Intent intent) {
        mBinder.startRun();
        return mBinder;
    }

    public void kj() {
        H.LOADKJ(new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    lottery.bq = jsonObject.getString("bq");
                    lottery.zt = jsonObject.getString("zt");
                    lottery.z1m = jsonObject.getString("z1m");
                    lottery.z1sx = jsonObject.getString("z1sx");
                    lottery.z2m = jsonObject.getString("z2m");
                    lottery.z2sx = jsonObject.getString("z2sx");
                    lottery.z3m = jsonObject.getString("z3m");
                    lottery.z3sx = jsonObject.getString("z3sx");
                    lottery.z4m = jsonObject.getString("z4m");
                    lottery.z4sx = jsonObject.getString("z4sx");
                    lottery.z5m = jsonObject.getString("z5m");
                    lottery.z5sx = jsonObject.getString("z5sx");
                    lottery.z6m = jsonObject.getString("z6m");
                    lottery.z6sx = jsonObject.getString("z6sx");
                    lottery.tm = jsonObject.getString("tm");
                    lottery.tmsx = jsonObject.getString("tmsx");
                    lottery.sxsj = jsonObject.getString("sxsj");
                    lottery.xyqsx = jsonObject.getString("xyqsj");
                    lottery.xq = jsonObject.getString("xq");
                    lottery.xyq = jsonObject.getString("xyq");
//                    lottery.setData(bq, zt, z1m, z1sx, z2m, z2sx, z3m, z3sx, z4m, z4sx, z5m, z5sx, z6m, z6sx, tm, tmsx, sxsj, xyqsj, xq, xyq);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AppLog.redLog("KJ", "" + lottery.toString());
                chang();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public interface OnVideoPlayPosition {
        void sendPosition(int a, String zm, String sx);

        void sendZT(int zt);
    }

    private void initStrData() {
        if (!TextUtils.isEmpty(listZM[0])) {
            for (int i = 0; i < listZM.length; i++) {
                listZM[i] = "";
                listSX[i] = "";
            }
        }
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
                chang();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });
    }

    void initD() {
        listAll[0] = lottery.z1sx;
        listAll[1] = lottery.z2sx;
        listAll[2] = lottery.z3sx;
        listAll[3] = lottery.z4sx;
        listAll[4] = lottery.z5sx;
        listAll[5] = lottery.z6sx;
        listAll[6] = lottery.tmsx;
        listZM[0] = lottery.z1m;
        listZM[1] = lottery.z2m;
        listZM[2] = lottery.z3m;
        listZM[3] = lottery.z4m;
        listZM[4] = lottery.z5m;
        listZM[5] = lottery.z6m;
        listZM[6] = lottery.tm;
    }

    void onSendPosition() {
        for (int i = 0; i < listAll.length; i++) {
            if (!TextUtils.isEmpty(listAll[i]) && TextUtils.isEmpty(listSX[i])) {
                AppLog.redLog("KJ", "onSendPosition:" + i);
                listSX[i] = listAll[i];
                onVideoPlayPosition.sendPosition(i, listZM[i], listAll[i]);
            }
        }
    }

    private void isMusicData() {
        AppLog.redLog("KJ", "isMusicData");
        onSendPosition();
//        if (!TextUtils.isEmpty(lottery.z1sx) && TextUtils.isEmpty(listSX[0])) {
//            listSX[0] = lottery.z1sx;
//            listZM[0] = lottery.z1m;
//            onVideoPlayPosition.sendPosition(0, lottery.z1m, lottery.z1sx);
//        }
//        if (!TextUtils.isEmpty(lottery.z2sx) && TextUtils.isEmpty(listSX[1])) {
//            listSX[1] = lottery.z2sx;
//            listZM[1] = lottery.z2m;
//            onVideoPlayPosition.sendPosition(1, lottery.z2m, lottery.z2sx);
//        }
//        if (!TextUtils.isEmpty(lottery.z3sx) && TextUtils.isEmpty(listSX[2])) {
//            listSX[2] = lottery.z3sx;
//            listZM[2] = lottery.z3m;
//            onVideoPlayPosition.sendPosition(2, lottery.z3m, lottery.z3sx);
//        }
//        if (!TextUtils.isEmpty(lottery.z4sx) && TextUtils.isEmpty(listSX[3])) {
//            listSX[3] = lottery.z4sx;
//            listZM[3] = lottery.z4m;
//            onVideoPlayPosition.sendPosition(3, lottery.z4m, lottery.z4sx);
//        }
//        if (!TextUtils.isEmpty(lottery.z5sx) && TextUtils.isEmpty(listSX[4])) {
//            listSX[4] = lottery.z5sx;
//            listZM[4] = lottery.z5m;
//            onVideoPlayPosition.sendPosition(4, lottery.z5m, lottery.z5sx);
//
//        }
//        if (!TextUtils.isEmpty(lottery.z6sx) && TextUtils.isEmpty(listSX[5])) {
//            listSX[5] = lottery.z6sx;
//            listZM[5] = lottery.z6m;
//            onVideoPlayPosition.sendPosition(5, lottery.z6m, lottery.z6sx);
//        }
//        if (!TextUtils.isEmpty(lottery.tmsx) && TextUtils.isEmpty(listSX[6])) {
//            listSX[6] = lottery.tmsx;
//            listZM[6] = lottery.tm;
//            onVideoPlayPosition.sendPosition(6, lottery.tm, lottery.tmsx);
//        }
    }

    private void toChange(int zt, int i) {
        if (onVideoPlayPosition != null && theZT[i] == 0) {
            onVideoPlayPosition.sendZT(zt);
            initStrData();
            initZT(i);
        }
    }

    private void chang() {
        Lottery.Time = MyUtils.SXSJ();
        initD();
        switch (lottery.zt) {
            case "1"://开奖完成
                if (onVideoPlayPosition != null) {
                    initZT(-1);
                    onVideoPlayPosition.sendZT(1);
                }
                if (oldZT) {
                    isMusicData();
                    oldZT = false;
                }
                break;
            case "2"://準備
                toChange(2, 0);
                break;
            case "3"://正在開獎
                if (onVideoPlayPosition != null) {
                    isMusicData();
                    oldZT = true;
                    if (theZT[3] == 0) {
                        onVideoPlayPosition.sendZT(3);
                        initZT(3);
                    }
                }
                break;
            case "4"://廣告中
                toChange(4, 1);
                break;
            case "5"://主持人講話中
                toChange(5, 2);
                break;
        }
    }

    boolean isRemove;
    final Handler handler = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {
            if (!isStop) {
                initXmlData();
                isRemove = true;
                AppLog.redLog(TAG, Lottery.Time + "");
                handler.postDelayed(this, Lottery.Time);
            } else {
                isRemove = false;
            }
        }
    };

    public class LottoBinder extends Binder {
        public void lunXun() {
            if (!isRemove) {
                AppLog.redLog(TAG, i + "-----lunXun-----");
                i++;
                handler.post(r);
            }
        }

        public String[] getListZM() {
            return listZM;
        }

        public void removeRun() {
            isStop = true;
        }

        public void startRun() {
            isStop = false;
        }

        public boolean isStop() {
            return isStop;
        }

        public String[] getListSX() {
            return listSX;
        }

        public String[] getListAll() {
            return listAll;
        }

        public void setOnVideoPlayPosition(OnVideoPlayPosition l) {
            onVideoPlayPosition = l;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(r);
        isRemove = false;
        mBinder.removeRun();
    }

    int[] theZT = new int[4];

    void initZT(int i) {
        if ((theZT[0] != 0) || (theZT[1] != 0) || (theZT[2] != 0) || (theZT[3] != 0)) {
            theZT[0] = 0;
            theZT[1] = 0;
            theZT[2] = 0;
            theZT[3] = 0;
        }
        if (i != -1)
            theZT[i] = i + 1;
    }
}
