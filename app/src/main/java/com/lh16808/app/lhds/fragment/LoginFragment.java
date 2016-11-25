package com.lh16808.app.lhds.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lh16808.app.lhds.R;
import com.lh16808.app.lhds.base.BaseFragment;
import com.lh16808.app.lhds.base.listener.OnLoginLinstener;
import com.lh16808.app.lhds.engine.OnTouchAnim;
import com.lh16808.app.lhds.model.User;
import com.lh16808.app.lhds.other.Login;
import com.lh16808.app.lhds.other.MessageEvent;
import com.lh16808.app.lhds.other.MyProgressDialog;
import com.lh16808.app.lhds.utils.MyUtils;
import com.lh16808.app.lhds.utils.RegexValidateUtil;
import com.lh16808.app.lhds.utils.SharedPreUtils;
import com.lh16808.app.lhds.utils.ToastUtil;
import com.lh16808.app.lhds.utils.http.H;
import com.lh16808.app.lhds.utils.http.Util;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.cookie.Cookie;

public class LoginFragment extends BaseFragment implements View.OnClickListener {

    private EditText etUserId;
    private EditText etPassword;

    private ImageView ivClearUserId;
    private ImageView ivEye;
    private ImageView ivLogo;
    private View vInput;

    private boolean isTopping = false;
    private FragmentActivity mActivity;
    private User user;

    public LoginFragment() {
    }


    @Override
    protected int setLayout() {
        return R.layout.fragment_login;
    }

    @Override
    protected void init() {
        mActivity = getActivity();
        etUserId = (EditText) mRootview.findViewById(R.id.et_uerid);
        etPassword = (EditText) mRootview.findViewById(R.id.et_password);
        ivLogo = (ImageView) mRootview.findViewById(R.id.iv_logo);
        TextView tvRegister = (TextView) mRootview.findViewById(R.id.tv_register);
        tvRegister.setOnClickListener(this);
        TextView tvFindPassword = (TextView) mRootview.findViewById(R.id.tv_find_password);
        tvFindPassword.setOnClickListener(this);
        Button btnLogin = (Button) mRootview.findViewById(R.id.btn_login);
        btnLogin.setOnTouchListener(new OnTouchAnim());
        btnLogin.setOnClickListener(this);

        vInput = mRootview.findViewById(R.id.layout_input);

        etUserId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (isTopping) {
                        return;
                    }
                    MyUtils.upToOutofWindow(ivLogo);
                    MyUtils.upToWindowTop(vInput, ivLogo);
                    isTopping = true;
                } else {
                    if (!etPassword.isFocused()) {
                        MyUtils.backToOriginal(ivLogo);
                        MyUtils.backToOriginal(vInput);
                        isTopping = false;
                        hideSoftInput();
                    }
                }
                setClearImage();
            }
        });

        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (isTopping) {
                        return;
                    }
                    MyUtils.upToOutofWindow(ivLogo);
                    MyUtils.upToWindowTop(vInput, ivLogo);
                    isTopping = true;
                } else {
                    if (!etUserId.isFocused()) {
                        MyUtils.backToOriginal(ivLogo);
                        MyUtils.backToOriginal(vInput);
                        isTopping = false;
                        hideSoftInput();
                    }
                }
                setClearImage();
            }
        });

        mRootview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.requestFocus();
                return false;
            }
        });

        ivClearUserId = (ImageView) mRootview.findViewById(R.id.iv_clear_userid);
        ivEye = (ImageView) mRootview.findViewById(R.id.iv_eye);

        ivClearUserId.setOnClickListener(this);
        ivEye.setOnClickListener(this);
    }

    @Override
    protected void load() {

    }

    private void setClearImage() {
        ivClearUserId.setVisibility(isTopping ? View.VISIBLE : View.INVISIBLE);
    }

    private void hideSoftInput() {
        View view = mActivity.getWindow().peekDecorView();
        if (view == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_login:
                String name = etUserId.getText().toString().trim();
                String pwd = etPassword.getText().toString().trim();
                if (!setVali(name, pwd)) {
                    return;
                }
                login(name, pwd);
                break;
            case R.id.tv_register:
                if (listener != null) {
                    ((OnLoginLinstener) listener).setCurrentUI(0);
                }
                break;
            case R.id.tv_find_password:
                if (listener != null) {
                    ((OnLoginLinstener) listener).setCurrentUI(2);
                }
                break;
            case R.id.iv_clear_userid:
                etUserId.setText("");
                break;
            case R.id.iv_eye:
                int type = etPassword.getInputType();
                if (type == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ivEye.setImageResource(R.drawable.eyes_close);
                } else {
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ivEye.setImageResource(R.drawable.eyes_open);
                }
                break;
            default:
                break;
        }
    }

    private void login(final String name, final String pwd) {
        MyProgressDialog.dialogShow(mActivity);
        RequestParams params2 = new RequestParams();
        params2.put("enews", "login");
        params2.put("username", name);
        params2.put("password", pwd);
        H.USER(params2, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                MyProgressDialog.dialogHide();
                String json = new String(responseBody);
                PersistentCookieStore myCookieStore = new PersistentCookieStore(mActivity);
                List<Cookie> cookies = myCookieStore.getCookies();
                Util.setCookies(cookies);
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(json);
                    int zt = jsonObject.getInt("zt");
                    if (zt == 0) {
                        String ts = jsonObject.getString("ts");
                        Toast.makeText(mActivity, ts, Toast.LENGTH_SHORT).show();
                    } else if (zt == 1) {
                        User user = User.getUser();
                        SharedPreUtils.putString("userName", name);
                        SharedPreUtils.putString("userPwd", pwd);
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
                        EventBus.getDefault().post(new MessageEvent(2));
                        mActivity.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(mActivity, "登陆失败~", Toast.LENGTH_SHORT).show();
                MyProgressDialog.dialogHide();
            }
        });
        Login.login(mActivity, name, pwd, new Login.OnLoginSucceful() {
            @Override
            public void onSucess() {
            }

            @Override
            public void onError() {

            }
        });
    }

    private boolean setVali(String userName, String userPWS) {
        if (TextUtils.isEmpty(userName)) {
            ToastUtil.toastShow(mActivity, "请输入正确的号码！");
            return false;
        }
        if (TextUtils.isEmpty(userPWS)) {
            ToastUtil.toastShow(mActivity, "请输入正确的密码！");
            return false;
        }
        if (!RegexValidateUtil.checkPassWork(userPWS)) {
            ToastUtil.toastShow(mActivity, "请输入正确的密码！");
            return false;
        }
        return true;
    }
}
