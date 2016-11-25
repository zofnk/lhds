package com.lh16808.app.lhds.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lh16808.app.lhds.MainActivity;
import com.lh16808.app.lhds.R;
import com.lh16808.app.lhds.activity.LoginActivity;
import com.lh16808.app.lhds.activity.MyCollectActivity;
import com.lh16808.app.lhds.activity.PeopleDataActivity;
import com.lh16808.app.lhds.activity.SetActivity;
import com.lh16808.app.lhds.model.User;
import com.lh16808.app.lhds.other.Login;
import com.lh16808.app.lhds.other.MessageEvent;
import com.lh16808.app.lhds.other.MyProgressDialog;
import com.lh16808.app.lhds.utils.ImageLoader;
import com.lh16808.app.lhds.utils.SharedPreUtils;
import com.lh16808.app.lhds.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment implements View.OnClickListener {

    static final String TAG = MineFragment.class.getName();
    MainActivity mActivity;
    private ImageView ivCode;
    private View view;
    private ImageView ivPhoto;
    private TextView tvName;
    private TextView tvLv;
    private View vLgoin;

    public MineFragment() {
        // Required empty public constructor
    }

    private TextView tvJiFen;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_mine, container, false);
            mActivity = MainActivity.getInstance();
            vLgoin = view.findViewById(R.id.ll_login);
            vLgoin.setOnClickListener(this);
            ivCode = (ImageView) view.findViewById(R.id.inviteTwoCode);
            ivPhoto = (ImageView) view.findViewById(R.id.iv_mine_photo);
            tvName = (TextView) view.findViewById(R.id.tv_fragment_mine_nickname);
            tvJiFen = (TextView) view.findViewById(R.id.tv_fragment_mine_jiFen);
            tvLv = (TextView) view.findViewById(R.id.tv_fragment_mine_lv);
            view.findViewById(R.id.rl_mine_bbs).setOnClickListener(this);
            view.findViewById(R.id.rl_mine_collect).setOnClickListener(this);
            view.findViewById(R.id.rl_mine_huiFu).setOnClickListener(this);
            view.findViewById(R.id.rl_mine_sheZhi).setOnClickListener(this);
            view.findViewById(R.id.rl_mine_ziLiao).setOnClickListener(this);
        }
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MessageEvent event) {
        ToastUtil.toastShow(mActivity, "requestCode:" + event.getType());
        if (event.getType() == 1) {
            Login.OnLoginLoadPic onLoginSucceful = new Login.OnLoginLoadPic() {
                @Override
                public void onSucess(String userpic) {
                    ImageLoader.LoaderNetHead(mActivity, userpic, ivPhoto);
                }

                @Override
                public void onError() {

                }
            };
            Login.loadPic(mActivity, onLoginSucceful);
        } else if (event.getType() == 2) {
            if (!TextUtils.isEmpty(User.getUser().getHym())) {
                if (tvJiFen.getVisibility() != View.VISIBLE) {
                    setUserInfo();
                }
            }
        }
    }

    @Override
    public void onStart() {
        if (TextUtils.isEmpty(User.getUser().getHym())) {
            String userName = SharedPreUtils.getString("userName");
            String userPwd = SharedPreUtils.getString("userPwd");
            if (!TextUtils.isEmpty(userPwd)) {
                MyProgressDialog.dialogShow(mActivity);
                Login.login(mActivity, userName, userPwd, new Login.OnLoginSucceful() {
                    @Override
                    public void onSucess() {
                        setUserInfo();
                    }

                    @Override
                    public void onError() {
                    }
                });
            } else {
                if (tvJiFen.getVisibility() != View.GONE) {
                    tvName.setText("请点击登录");
                    tvJiFen.setVisibility(View.GONE);
                    tvLv.setVisibility(View.GONE);
                }
            }
        }
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void setUserInfo() {
        tvName.setText("昵称:" + User.getUser().getHym());
        tvJiFen.setText("积分:" + User.getUser().getJs());
        tvJiFen.setVisibility(View.VISIBLE);
        tvLv.setText("等级:" + User.getUser().getDj());
        tvLv.setVisibility(View.VISIBLE);
        Login.OnLoginLoadPic onLoginSucceful = new Login.OnLoginLoadPic() {
            @Override
            public void onSucess(String userpic) {
                ImageLoader.LoaderNetHead(mActivity, userpic, ivPhoto);
            }

            @Override
            public void onError() {

            }
        };
        Login.loadPic(mActivity, onLoginSucceful);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.ll_login:
                if (TextUtils.isEmpty(User.getUser().getHym()))
                    mActivity.startActivityForResult(new Intent(mActivity, LoginActivity.class), 1);
                else {
                    intent = new Intent(mActivity, PeopleDataActivity.class);
                    startLogin(intent);
                }
                break;
            case R.id.rl_mine_bbs:
                intent = new Intent(mActivity, MyCollectActivity.class);
                intent.putExtra("title", 1);
                startLogin(intent);
                break;
            case R.id.rl_mine_collect:
                intent = new Intent(mActivity, MyCollectActivity.class);
                intent.putExtra("title", 0);
                startLogin(intent);
                break;
            case R.id.rl_mine_huiFu:
                intent = new Intent(mActivity, MyCollectActivity.class);
                intent.putExtra("title", 2);
                startLogin(intent);
                break;
            case R.id.rl_mine_ziLiao:
                intent = new Intent(mActivity, PeopleDataActivity.class);
                startLogin(intent);
                break;
            case R.id.rl_mine_sheZhi:
                mActivity.startActivityForResult(new Intent(mActivity, SetActivity.class), 1);
                break;
        }
    }

    private void startLogin(Intent intent) {
        if (!TextUtils.isEmpty(User.getUser().getHym())) {
            startActivity(intent);
        } else {
            ToastUtil.toastShow(mActivity, "你还未登录~");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
