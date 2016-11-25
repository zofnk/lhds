package com.lh16808.app.lhds.fragment;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.InputType;
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
import com.lh16808.app.lhds.widget.MyNumberPicker;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindPasswordFragment extends BaseFragment implements View.OnClickListener {


    private EditText etPassword;
    private EditText etName;
    private EditText etPhoneOrEmail;
    private ImageView ivEye;
    private boolean isScrolling;

    public FindPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_find_password;
    }

    @Override
    protected void init() {
        View layoutBack = mRootview.findViewById(R.id.layout_back_to_login);
        layoutBack.setOnClickListener(this);


        etPassword = (EditText) mRootview.findViewById(R.id.et_password);
        etPhoneOrEmail = (EditText) mRootview.findViewById(R.id.et_phone);
        etName = (EditText) mRootview.findViewById(R.id.et_name);


//        tvArea = (TextView) mRootview.findViewById(R.id.tv_area);
//        tvArea.setOnClickListener(this);


        ivEye = (ImageView) mRootview.findViewById(R.id.iv_eye);
        ivEye.setOnClickListener(this);

        Button btnCommit = (Button) mRootview.findViewById(R.id.btn_commit);
        btnCommit.setOnClickListener(this);

        mRootview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftInput();
                return false;
            }
        });
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
                break;
//            case R.id.tv_area:
//                hideSoftInput();
//                showExpressPop();
//                break;
            case R.id.btn_commit:
//                String area = tvArea.getText().toString();
                String phone = etPhoneOrEmail.getText().toString();
                String idName = etName.getText().toString();
                String password = etPassword.getText().toString();
                resetPassword("", phone, idName, password);
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
        }
    }

    private void resetPassword(String area, String phone, String idName, String password) {

    }
//    private PopupWindow expressPop;
//    private MyNumberPicker np_pop_express;
//
//    private void showExpressPop() {
//        Resources resources = getResources();
//        if (expressPop == null) {
//            View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_pickerview, null);
//            np_pop_express = (MyNumberPicker) contentView.findViewById(R.id.np_edit_province);
//            Button cancel = (Button) contentView.findViewById(R.id.btnCancel);
//            cancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    expressPop.dismiss();
//                }
//            });
//            Button Submit = (Button) contentView.findViewById(R.id.btnSubmit);
//            Submit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int value = np_pop_express.getValue();
//                    tvArea.setText(provinceList[value]);
//                    if (value == 0) {
//                        etPhoneOrEmail.setHint("请输入您的邮箱");
//                    } else {
//                        etPhoneOrEmail.setHint("请输入您的手机");
//                    }
//                    expressPop.dismiss();
//                }
//            });
//            expressPop = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            expressPop.setAnimationStyle(R.style.popwin_anim_style);
////            np_pop_express.setNumberPickerDividerColor(Color.WHITE);
//            np_pop_express.setNumberPickerDividerColor(resources.getColor(R.color.color_ball_bg_tv));
//            np_pop_express.setDescendantFocusability(MyNumberPicker.FOCUS_BLOCK_DESCENDANTS);
//            np_pop_express.setOnValueChangedListener(this);
//            showProvincePicker();
//        }
//        ColorDrawable cd = new ColorDrawable(0x000000);
//        expressPop.setBackgroundDrawable(cd);
//        //产生背景变暗效果
//        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
//        lp.alpha = 0.4f;
//        getActivity().getWindow().setAttributes(lp);
//
//        expressPop.setOutsideTouchable(true);
//        expressPop.setFocusable(true);
//        expressPop.update();
//        expressPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            //在dismiss中恢复透明度
//            public void onDismiss() {
//                Handler h = new Handler();
//                h.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
//                        lp.alpha = 1f;
//                        getActivity().getWindow().setAttributes(lp);
//                    }
//                }, 300);
//            }
//        });
//        expressPop.showAtLocation(tvArea, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 150, 0);
//    }
//
//    String[] provinceList = new String[]{"邮箱(email)", "手机(phone)"};
//
//    private void showProvincePicker() {
//        np_pop_express.setMinValue(0);
//        np_pop_express.setMaxValue(0);
//        np_pop_express.setDisplayedValues(provinceList);
//        np_pop_express.setMaxValue(provinceList.length - 1);
//    }
//
//    @Override
//    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//
//    }
}
