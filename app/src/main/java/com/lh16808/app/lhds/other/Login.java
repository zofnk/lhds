package com.lh16808.app.lhds.other;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.lh16808.app.lhds.model.User;
import com.lh16808.app.lhds.utils.ImageLoader;
import com.lh16808.app.lhds.utils.SharedPreUtils;
import com.lh16808.app.lhds.utils.ToastUtil;
import com.lh16808.app.lhds.utils.http.H;
import com.lh16808.app.lhds.utils.http.Util;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.cookie.Cookie;

/**
 * Created by Administrator on 2016/11/21.
 */

public class Login {
    public static void login(final Activity mActivity, final String name, final String pwd, final OnLoginSucceful onLoginSucceful) {
        RequestParams params2 = new RequestParams();
        params2.put("enews", "login");
        params2.put("username", name);
        params2.put("password", pwd);
        H.USER(params2, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                MyProgressDialog.dialogHide();
                String json = new String(responseBody);
                jieXi(json);
                PersistentCookieStore myCookieStore = new PersistentCookieStore(mActivity);
                List<Cookie> cookies = myCookieStore.getCookies();
                Util.setCookies(cookies);
                SharedPreUtils.putString("userName", name);
                SharedPreUtils.putString("userPwd", pwd);
                if (onLoginSucceful != null) {
                    onLoginSucceful.onSucess();
                }
            }

            private void jieXi(String arg0) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(arg0);
                    int zt = jsonObject.getInt("zt");
                    if (zt == 0) {
                        String ts = jsonObject.getString("ts");
                        Toast.makeText(mActivity, ts, Toast.LENGTH_SHORT).show();
                    } else if (zt == 1) {
                        User user = User.getUser();
                        String tx = jsonObject.getString("tx");
                        user.setTx(tx);
                        String hym = jsonObject.getString("hym");
                        user.setHym(hym);
                        String jf = jsonObject.getString("jf");
                        user.setJs(jf);
                        String dj = jsonObject.getString("dj");
                        user.setDj(dj);
                        String rnd = jsonObject.getString("rnd");
                        user.setRnd(rnd);
                        String userid = jsonObject.getString("uid");
                        user.setUserid(userid);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (onLoginSucceful != null) {
                    onLoginSucceful.onError();
                }
                Toast.makeText(mActivity, "登陆失败~", Toast.LENGTH_SHORT).show();
                MyProgressDialog.dialogHide();
            }
        });
    }


    public static void loadPic(final Activity mActivity, final OnLoginLoadPic onLoginSucceful) {
//        Map<String, String> params = new HashMap<>();
        RequestParams params = new RequestParams();
        params.put("enews", "Info");
        params.put("userid", User.getUser().getUserid());
        params.put("username", User.getUser().getHym());
        params.put("rnd", User.getUser().getRnd());
        H.USER(params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String arg0 = new String(responseBody);
                try {
                    JSONObject jsonObject = new JSONObject(arg0);
                    String zt = jsonObject.getString("zt");
                    int parseInt = Integer.parseInt(zt);
                    if (parseInt == 1) {
                        String truename = jsonObject.getString("truename");
                        User.getUser().setTruename(truename);
                        String phone = jsonObject.getString("phone");
                        User.getUser().setPhone(phone);
                        String weixin = jsonObject.getString("weixin");
                        User.getUser().setWeixin(weixin);
                        String oicq = jsonObject.getString("oicq");
                        User.getUser().setOicq(oicq);
                        String userpic = jsonObject.getString("userpic");
                        User.getUser().setUserpic(userpic);
                        if (!TextUtils.isEmpty(userpic)) {
                            if (onLoginSucceful != null) {
                                onLoginSucceful.onSucess(userpic);
                            }
                        }

                    } else {
                        String ts = jsonObject.getString("ts");
                        ToastUtil.toastShow(mActivity, ts);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                ToastUtil.toastShow(mActivity, "网络错误~");
                if (onLoginSucceful != null) {
                    onLoginSucceful.onError();
                }
            }
        });
    }

    public interface OnLoginSucceful {
        void onSucess();

        void onError();
    }

    public interface OnLoginLoadPic {
        void onSucess(String userpic);

        void onError();
    }
}
