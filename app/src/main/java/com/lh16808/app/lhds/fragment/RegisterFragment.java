package com.lh16808.app.lhds.fragment;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lh16808.app.lhds.R;
import com.lh16808.app.lhds.base.BaseFragment;
import com.lh16808.app.lhds.base.listener.OnLoginLinstener;
import com.lh16808.app.lhds.engine.OnTouchAnim;
import com.lh16808.app.lhds.other.MyProgressDialog;
import com.lh16808.app.lhds.utils.RegexValidateUtil;
import com.lh16808.app.lhds.utils.ToastUtil;
import com.lh16808.app.lhds.utils.http.H;
import com.lh16808.app.lhds.widget.MyNumberPicker;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends BaseFragment implements View.OnClickListener {


    private boolean isScrolling;


    //    private TextView tvArea;
    private EditText etName;
    private EditText etPassword;
    private PopupWindow expressPop;
    private MyNumberPicker np_pop_express;
    private EditText etPhone;
    private EditText etEmail;
    private ImageView ivEye;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_register;
    }

    @Override
    protected void init() {
        mRootview.findViewById(R.id.layout_back_to_login).setOnClickListener(this);

        TextView tvToProtocol = (TextView) mRootview.findViewById(R.id.tv_to_protocol);
        tvToProtocol.setOnClickListener(this);

        etEmail = (EditText) mRootview.findViewById(R.id.et_email);
        etPhone = (EditText) mRootview.findViewById(R.id.et_phone);
        etName = (EditText) mRootview.findViewById(R.id.et_name);
        etPassword = (EditText) mRootview.findViewById(R.id.et_password);

        Button btnRegister = (Button) mRootview.findViewById(R.id.btn_register);
        btnRegister.setOnTouchListener(new OnTouchAnim());
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String phone = etPhone.getText().toString();
                String idCode = etName.getText().toString();
                String password = etPassword.getText().toString();
                RegisterFragment.this.register(email, phone, idCode, password);
            }
        });

        mRootview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftInput();
                return false;
            }
        });
        ivEye = (ImageView) mRootview.findViewById(R.id.iv_eye);
        ivEye.setOnClickListener(this);
    }

    private void register(String email, String phone, String idCode, String password) {
        boolean setVali = setVali(email, phone, idCode, password);
        if (!setVali) {
            return;
        }
        MyProgressDialog.dialogShow(getActivity());
        RequestParams params = new RequestParams();
        params.put("enews", "register");
        params.put("username", idCode);
        params.put("password", password);
        params.put("repassword", password);
        if (!TextUtils.isEmpty(phone))
            params.put("phone", phone);
        params.put("email", email);
        H.USER(params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                MyProgressDialog.dialogHide();
                String arg0 = new String(responseBody);
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(arg0);
                    int zt = jsonObject.getInt("zt");
                    String ts = jsonObject.getString("ts");
                    ToastUtil.toastShow(getActivity(), ts + "");
                    if (zt == 1) {
                        ToastUtil.toastShow(getActivity(), "注册成功！返回登录");
                        fanhui();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyProgressDialog.dialogHide();
                ToastUtil.toastShow(getActivity(), "網路錯誤~");
            }
        });
    }

    private boolean setVali(String email, String phone, String idCode, String password) {
        if (TextUtils.isEmpty(email)) {
            ToastUtil.toastShow(getActivity(), "邮箱不能为空！");
            return false;
        }
        if (TextUtils.isEmpty(idCode)) {
            ToastUtil.toastShow(getActivity(), "帐号不能为空！");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.toastShow(getActivity(), "密码不能为空！");
            return false;
        }
        if (!RegexValidateUtil.checkPassWork(password)) {
            ToastUtil.toastShow(getActivity(), "请输入正确的密码！");
            return false;
        }
        if (!TextUtils.isEmpty(phone)) {
            if (!RegexValidateUtil.checkMobileNumber(phone)) {
                ToastUtil.toastShow(getActivity(), "请输入正确的手机号码！");
                return false;
            }
        }
        if (!RegexValidateUtil.checkEmail(email)) {
            ToastUtil.toastShow(getActivity(), "请输入正确的邮箱格式！");
            return false;
        }
        return true;

    }

    private void hideSoftInput() {
        View view = getActivity().getWindow().peekDecorView();
        if (view == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void load() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_back_to_login:
                fanhui();
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
        }
    }

    private void fanhui() {
        if (!isScrolling) {
            ((OnLoginLinstener) listener).setCurrentUI(1);
            isScrolling = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isScrolling = false;
                }
            }, 1000);
        }
    }
}
