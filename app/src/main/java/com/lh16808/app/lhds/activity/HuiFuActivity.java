package com.lh16808.app.lhds.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.lh16808.app.lhds.R;
import com.lh16808.app.lhds.base.BaseActivity;
import com.lh16808.app.lhds.widget.EmoticonsEditText;

import java.util.Timer;
import java.util.TimerTask;

public class HuiFuActivity extends BaseActivity {

    @Override
    protected void initVariables() {

    }

    EmoticonsEditText etContent;
    Button btnSend;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_hui_fu);
        etContent = (EmoticonsEditText) findViewById(R.id.et_chat);
        btnSend = (Button) findViewById(R.id.btn_send);
        etContent.setFocusable(true);
        etContent.setFocusableInTouchMode(true);
        etContent.requestFocus();
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    btnSend.setBackgroundResource(R.drawable.btn_send_bg);
                } else {
                    btnSend.setBackgroundResource(R.drawable.btn_send_bg_disable);
                }
            }
        });
        upkeyborke();
    }

    @Override
    protected void loadData() {
    }

    private void upkeyborke() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

                           public void run() {
                               InputMethodManager inputManager =
                                       (InputMethodManager) etContent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                               inputManager.showSoftInput(etContent, 0);
                           }

                       },
                998);
    }


}
