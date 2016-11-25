package com.lh16808.app.lhds.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.lh16808.app.lhds.R;
import com.lh16808.app.lhds.base.BaseActivity;
import com.lh16808.app.lhds.engine.OnTouchAnim;
import com.lh16808.app.lhds.fragment.MineFragment;
import com.lh16808.app.lhds.model.User;
import com.lh16808.app.lhds.other.MessageEvent;
import com.lh16808.app.lhds.other.MyProgressDialog;
import com.lh16808.app.lhds.utils.AppLog;
import com.lh16808.app.lhds.utils.ImageLoader;
import com.lh16808.app.lhds.utils.MyUtils;
import com.lh16808.app.lhds.utils.PathInfo;
import com.lh16808.app.lhds.utils.ToastUtil;
import com.lh16808.app.lhds.utils.http.H;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yalantis.ucrop.UCrop;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import cz.msebera.android.httpclient.Header;

public class PeopleDataActivity extends BaseActivity implements View.OnClickListener {

    private User user;
    private ImageView ivHead;
    private EditText etName;
    private EditText etPhone;
    private EditText etQQ;
    private EditText etWeixin;
    private Button btnSubmit;
    private Bitmap bitmap;
    private PopupWindow uploadPicPop;
    private View mRootView;
    private String headCameraPath;
    private String headFile;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_people_data);
        user = User.getUser();
        initUI();
        initData();
    }

    private void initData() {
        MyProgressDialog.dialogShow(this);
        RequestParams params = new RequestParams();
        params.put("enews", "Info");
        params.put("userid", user.getUserid());
        params.put("username", user.getHym());
        params.put("rnd", user.getRnd());
        H.USER(params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                MyProgressDialog.dialogHide();
                String arg0 = new String(responseBody);
                // Log.e("", "" + arg0);
                try {
                    JSONObject jsonObject = new JSONObject(arg0);
                    String zt = jsonObject.getString("zt");
                    int parseInt = Integer.parseInt(zt);
                    if (parseInt == 1) {
                        String truename = jsonObject.getString("truename");
                        user.setTruename(truename);
                        String phone = jsonObject.getString("phone");
                        user.setPhone(phone);
                        String weixin = jsonObject.getString("weixin");
                        user.setWeixin(weixin);
                        String oicq = jsonObject.getString("oicq");
                        user.setOicq(oicq);
                        String userpic = jsonObject.getString("userpic");
                        user.setUserpic(userpic);
//                        MyProgressDialog.dialogHide();
//                        ProPOPUtils.cancel();
                        if (!TextUtils.isEmpty(truename)) {
                            etName.setText(truename);
                        }
                        if (!TextUtils.isEmpty(phone)) {
                            etPhone.setText(phone);
                        }
                        if (!TextUtils.isEmpty(oicq)) {
                            etQQ.setText(oicq);
                        }
                        if (!TextUtils.isEmpty(weixin)) {
                            etWeixin.setText(weixin);
                        }
                        if (!TextUtils.isEmpty(userpic)) {
                            if (!PeopleDataActivity.this.isFinishing())
                                ImageLoader.LoaderNetHead(PeopleDataActivity.this, userpic, ivHead);
                        }

                    } else {
                        String ts = jsonObject.getString("ts");
                        Toast.makeText(PeopleDataActivity.this, ts, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyProgressDialog.dialogHide();
                Toast.makeText(PeopleDataActivity.this, "獲取失敗~", Toast.LENGTH_SHORT).show();
                boolean destroyed = PeopleDataActivity.this.isFinishing();
            }
        });
    }

    private void initUI() {
        mRootView = findViewById(R.id.ll_people_data);
        mRootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftInput();
                return false;
            }
        });
        ivHead = (ImageView) findViewById(R.id.iv_fragment_mine_head);
        ivHead.setOnClickListener(this);
        etName = (EditText) findViewById(R.id.et_forget_data_name);
        etPhone = (EditText) findViewById(R.id.et_forget_data_phone);
        etQQ = (EditText) findViewById(R.id.et_forget_data_qq);
        etWeixin = (EditText) findViewById(R.id.et_forget_data_weixin);
        btnSubmit = (Button) findViewById(R.id.btn_Submit);
        btnSubmit.setOnTouchListener(new OnTouchAnim());
        btnSubmit.setOnClickListener(this);
    }

    private void hideSoftInput() {
        View view = getWindow().peekDecorView();
        if (view == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void loadData() {

    }

    public static final String IMAGE_UNSPECIFIED = "image/*";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Submit:
                if (MyUtils.isFastDoubleClick()) {
                    return;
                }
                if (!TextUtils.isEmpty(user.getHym())) {
                    String name = etName.getText().toString().trim();
                    String phone = etPhone.getText().toString().trim();
                    String qq = etQQ.getText().toString().trim();
                    String weixin = etWeixin.getText().toString().trim();
                    if (bitmap == null) {
                        sendData(name, phone, qq, weixin);
                    } else {
                        File file = formatHeadBitmap(bitmap);
                        uploadPic(file, name, phone, qq, weixin);
                    }
                }
                break;
            case R.id.iv_fragment_mine_head:
                // 更换头像点击事件
                showUploadPicPop();
                break;
            case R.id.tv_pop_upload_picture_photo: // 拍照
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File mpath = new File(PathInfo.SDPATH + PathInfo.getImageSubPath());
                if (!mpath.exists()) {
                    mpath.mkdirs();
                }
                headCameraPath = PathInfo.SDPATH + PathInfo.getImageSubPath() + "photo1" + ".png";
                File file = new File(headCameraPath);
                Uri mOutPutFileUri = Uri.fromFile(file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mOutPutFileUri);
                startActivityForResult(intent, 1);
                uploadPicPop.dismiss();
                break;
            case R.id.tv_pop_upload_picture_album: // 相册
                intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
                startActivityForResult(intent, 2);
                uploadPicPop.dismiss();
                break;
        }
    }

    /**
     * 上传修改头像图片
     *
     * @param weixin
     * @param qq
     * @param phone
     * @param name
     * @param bm
     */
    private void uploadPic(File bm, String name, String phone, String qq, String weixin) {
        String[] info = new String[4];
        info[0] = name;
        info[1] = phone;
        info[2] = qq;
        info[3] = weixin;
        sendImage(bm, info);
    }

    //添加一个新的方法用来发送图像数据
    //将Bitmap的数据发送到指定的服务器
    public void sendImage(File bm, String[] info) {
        //转换为byte数组类型
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.PNG, 60, stream);
//        byte[] bytes = stream.toByteArray();
//
        //将这个数组编码成Base64编码的数据把他发送给服务器
//        String img = new String(Base64.encodeToString(bytes, Base64.DEFAULT));
        //将string类型的数据发送到服务器在服务器端通过Base64解码得到图像的原始数据
        RequestParams params = new RequestParams();//该对象用于保存要传输的参数
//        params.add("userpicfile", img);
        try {
            params.put("userpicfile", bm);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        params.put("truename", info[0]);
        params.put("phone", info[1]);
        params.put("oicq", info[2]);
        params.put("weixin", info[3]);
        params.put("enews", "EditInfo");
        params.put("rnd", user.getRnd());
        params.put("username", user.getHym());
        params.put("userid", user.getUserid());
        MyProgressDialog.dialogShow(this);
        H.USER(params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                MyProgressDialog.dialogHide();
                String json = new String(responseBody);
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(json);
                    int zt = jsonObject.getInt("zt");
                    String ts = jsonObject.getString("ts");
                    ToastUtil.toastShow(PeopleDataActivity.this, ts);
                    if (zt == 1) {
                        EventBus.getDefault().post(new MessageEvent(1));
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyProgressDialog.dialogHide();
            }
        });
    }

    /**
     * 对头像bitmap进行剪裁压缩和存储
     *
     * @param headBitmap
     * @return
     */
    private File formatHeadBitmap(Bitmap headBitmap) {
        try {
//            int height = headBitmap.getHeight();
//            int width = headBitmap.getWidth();
//            int length = height < width ? height : width;
//            int x = (width - length) / 2;
//            int y = (height - length) / 2;
//            Bitmap cutBitmap = Bitmap.createBitmap(headBitmap, x, y, length, length);
//            Bitmap compressBitmap = Bitmap.createScaledBitmap(cutBitmap, 320, 320, true);
            File directory = new File(PathInfo.SDPATH + "/" + PathInfo.getImageSubPath());
            File picture = File.createTempFile("image", ".png", directory);
            OutputStream outputStream = new FileOutputStream(picture);
            headBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
//            headBitmap.recycle();
//            cutBitmap.recycle();
//            compressBitmap.recycle();
            return picture;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void showUploadPicPop() {
        if (uploadPicPop == null) {
            View contentView = getLayoutInflater().inflate(R.layout.pop_upload_picture, null);
            contentView.findViewById(R.id.tv_pop_upload_picture_photo).setOnClickListener(this);
            contentView.findViewById(R.id.tv_pop_upload_picture_album).setOnClickListener(this);
            contentView.findViewById(R.id.tv_pop_upload_picture_cancel).setOnClickListener(this);
            uploadPicPop = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            uploadPicPop.setAnimationStyle(R.style.popwin_anim_style);
        }
        ColorDrawable cd = new ColorDrawable(0x000000);
        uploadPicPop.setBackgroundDrawable(cd);
        // 产生背景变暗效果
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.4f;
        getWindow().setAttributes(lp);

        uploadPicPop.setOutsideTouchable(true);
        uploadPicPop.setFocusable(true);
        uploadPicPop.showAtLocation(mRootView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 150, 0);

        uploadPicPop.update();
        uploadPicPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            // 在dismiss中恢复透明度
            public void onDismiss() {
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.alpha = 1f;
                        getWindow().setAttributes(lp);
                    }
                }, 300);
            }
        });
    }

    private void sendData(String name, String phone, String qq, String weixin) {
        String[] info = new String[4];
        info[0] = name;
        info[1] = phone;
        info[2] = qq;
        info[3] = weixin;
        RequestParams params = new RequestParams();
        params.put("enews", "EditInfo");
//        params.put("truename", name);
//        params.put("phone", phone);
//        params.put("oicq", qq);
//        params.put("weixin", weixin);
        params.put("truename", info[0]);
        params.put("phone", info[1]);
        params.put("oicq", info[2]);
        params.put("weixin", info[3]);
        params.put("userid", user.getUserid());
        params.put("username", user.getHym());
        params.put("rnd", user.getRnd());
        MyProgressDialog.dialogShow(this);
        H.USER(params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                MyProgressDialog.dialogHide();
                String s = new String(responseBody);
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(s);
                    int zt = jsonObject.getInt("zt");
                    String ts = jsonObject.getString("ts");
                    ToastUtil.toastShow(PeopleDataActivity.this, ts + "");
                    if (zt == 1) {
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyProgressDialog.dialogHide();
                Toast.makeText(PeopleDataActivity.this, "上傳失敗~", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1: // 拍照--头像
                    File fileimg = new File(headCameraPath);
                    if (fileimg.exists()) {
                        Uri uri = Uri.fromFile(fileimg);
                        // 裁剪
                        crop(uri);
                    } else {
                        ToastUtil.toastShow(this, "拍照失败");
                    }
                    break;
                case 2: // 本地相册--头像
                    if (data != null) {
                        Uri originalUri = data.getData();
                        crop(originalUri);
                    }
                    break;
                case UCrop.REQUEST_CROP:
                    try {
                        final Uri resultUri = UCrop.getOutput(data);
                        bitmap = getBitmapFromUri(resultUri);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] bytes = baos.toByteArray();
                        ImageLoader.LoaderNetHead(this, bytes, ivHead);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case UCrop.RESULT_ERROR:
                    final Throwable cropError = UCrop.getError(data);
                    AppLog.redLog("TAGGGGG", cropError.getMessage() + "");
                    break;
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void crop(Uri sourceUri) {
        Uri mDestinationUri = buildUri();
        UCrop.of(sourceUri, mDestinationUri)
                .withAspectRatio(1, 1)
                .withMaxResultSize(100, 100)
                .start(this);
    }

    private Uri buildUri() {
        return Uri.fromFile(new File(getCacheDir(), "photo.png"));
    }

    private Bitmap getBitmapFromUri(Uri uri) {
        try {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            return bitmap;
        } catch (Exception e) {
            AppLog.redLog("[Android]", e.getMessage());
            AppLog.redLog("[Android]", "目录为：" + uri);
            e.printStackTrace();
            return null;
        }
    }
}
