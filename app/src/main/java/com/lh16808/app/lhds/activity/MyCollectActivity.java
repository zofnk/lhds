package com.lh16808.app.lhds.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.lh16808.app.lhds.R;
import com.lh16808.app.lhds.base.BaseActivity;
import com.lh16808.app.lhds.marco.ApiConfig;
import com.lh16808.app.lhds.marco.Constants;
import com.lh16808.app.lhds.model.CollectModel;
import com.lh16808.app.lhds.model.User;
import com.lh16808.app.lhds.other.MyProgressDialog;
import com.lh16808.app.lhds.utils.AppLog;
import com.lh16808.app.lhds.utils.ToastUtil;
import com.lh16808.app.lhds.utils.http.H;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MyCollectActivity extends BaseActivity {

    private View mLLCollect;
    private RadioGroup mRPCollect;
    private RadioButton mRBCollect1;
    private RadioButton mRBCollect2;
    private SwipeMenuListView mSwipeMenuListView;
    private View mNotData;
    private User mUser;
    private int showType;
    private CollectAdapter mAdapter;
    int checkedId;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_collect);
        mUser = User.getUser();
        mNotData = findViewById(R.id.tv_myCollect_neirong);
        mLLCollect = findViewById(R.id.ll_collect_list);
        mRPCollect = (RadioGroup) findViewById(R.id.rp_collect_se);
        mRBCollect1 = (RadioButton) findViewById(R.id.rb_collect_se1);
        mRBCollect2 = (RadioButton) findViewById(R.id.rb_collect_se2);
        mSwipeMenuListView = (SwipeMenuListView) findViewById(R.id.list_myCollect);
        Intent intent = getIntent();
        showType = intent.getIntExtra("title", -1);
        if (showType != -1) {
            checkedId = R.id.rb_collect_se1;
            initData(showType);
            if (showType == 0) {
                mRPCollect.setVisibility(View.GONE);
            } else {
                mRPCollect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        listCollect.clear();
                        mAdapter.notifyDataSetChanged();
                        MyCollectActivity.this.checkedId = checkedId;
                        initData(showType);
                    }
                });
            }
        }
        mAdapter = new CollectAdapter();
        mSwipeMenuListView.setAdapter(mAdapter);
        mSwipeMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (showType == 1 && checkedId == R.id.rb_collect_se1) {
                    CollectModel collectModel = listCollect.get(position);
//                    Intent intent = new Intent(MyCollectActivity.this, WebActivity.class);
//                    intent.putExtra(Constants.WebInKey, collectModel.getLj());
//                    intent.putExtra(Constants.WebInTiTle, "詳細內容");
//                    intent.putExtra(Constants.AdKey, Constants.AD_LHDQ);
//                    intent.putExtra("collect", "collect");
//                    startActivity(intent);
                }
            }
        });
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @SuppressWarnings("deprecation")
            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                if (showType == 1) {
                    deleteItem.setIcon(R.drawable.ic_delete);
                } else {
                    deleteItem.setTitle("修改");
                    deleteItem.setTitleSize(15);
                    deleteItem.setTitleColor(getResources().getColor(R.color.white));
                }
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        mSwipeMenuListView.setMenuCreator(creator);
        // step 2. listener item click event
        mSwipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                if (index == 0) {
                    if (showType == 0) {
                        delete(position);
                        listCollect.remove(position);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        String type;
                        String title = "编辑";
                        if (checkedId == R.id.rb_collect_se1) {
                            type = ApiConfig.MYFAVA2[showType];
                        } else {
                            type = ApiConfig.MYFAVA1[showType];
                        }
                        bianji(position, type, title, showType);
                    }
                }
                return false;
            }
        });
    }

    String urlValue = "";

    private void initData(int i) {
        if (i == 0) {
            urlValue = ApiConfig.MYFAVA1[0];
        } else {
            if (checkedId == R.id.rb_collect_se1) {
                //weishenhe
                urlValue = ApiConfig.MYFAVA2[i];
            } else {
                urlValue = ApiConfig.MYFAVA1[i];
            }
        }
        if (TextUtils.isEmpty(urlValue)) {
            ToastUtil.toastShow(this, "出错~");
            return;
        }
        if (showType != 0) {
            mRBCollect1.setEnabled(false);
            mRBCollect2.setEnabled(false);
        }
        MyProgressDialog.dialogShow(this);
        RequestParams params = new RequestParams();
        params.put("enews", urlValue);
        params.put("uid", mUser.getUserid());
        params.put("uname", mUser.getHym());
        params.put("rnd", mUser.getRnd());
        H.ForumData(params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (showType != 0) {
                    mRBCollect1.setEnabled(true);
                    mRBCollect2.setEnabled(true);
                }
                MyProgressDialog.dialogHide();
                String s = new String(responseBody);
                if (s.length() > 10) {
                    try {
                        JSONArray jsonArray = new JSONArray(s);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String sid = jsonObject.getString("sid");
                            String bt = jsonObject.getString("bt");
                            String lj = jsonObject.getString("lj");
                            CollectModel collectModel = new CollectModel(sid, bt, lj);
                            listCollect.add(collectModel);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (listCollect.size() == 0) {
                    if (mNotData.getVisibility() != View.VISIBLE) {
                        mNotData.setVisibility(View.VISIBLE);
                        mSwipeMenuListView.setVisibility(View.GONE);
                    }
                } else if (mAdapter != null) {
                    if (mSwipeMenuListView.getVisibility() != View.VISIBLE) {
                        mNotData.setVisibility(View.GONE);
                        mSwipeMenuListView.setVisibility(View.VISIBLE);
                    }
                    mAdapter.notifyDataSetChanged();
                }
//                if (pro_loading != null)
//                    pro_loading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (showType != 0) {
                    mRBCollect1.setEnabled(true);
                    mRBCollect2.setEnabled(true);
                }
                MyProgressDialog.dialogHide();
                boolean destroyed = MyCollectActivity.this.isFinishing();
                Toast.makeText(MyCollectActivity.this, "網絡錯誤~", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void loadData() {

    }

    ArrayList<CollectModel> listCollect = new ArrayList<>();

    class CollectAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return listCollect.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolader vHolader = null;
            if (convertView == null) {
                vHolader = new ViewHolader();
                convertView = getLayoutInflater().from(MyCollectActivity.this).inflate(R.layout.item_collect, parent, false);
                vHolader.tv = (TextView) convertView.findViewById(R.id.tv_item_title);
                convertView.setTag(vHolader);
            } else {
                vHolader = (ViewHolader) convertView.getTag();
            }
            vHolader.tv.setText(listCollect.get(position).getBt());
            return convertView;
        }

        class ViewHolader {
            TextView tv;
        }
    }


    private void bianji(int position, String type, String title, int showtype) {
        CollectModel collectModel = listCollect.get(position);
//        Intent intent = new Intent(MyCollectActivity.this, FaTeiActivity.class);
//        intent.putExtra("title", title);
//        intent.putExtra("CollectModel", collectModel);
//        intent.putExtra("type", type);
//        intent.putExtra("type1", showtype);
//        startActivityForResult(intent, 0);
    }

    private void delete(int position) {
        CollectModel collectModel = listCollect.get(position);
        RequestParams params = new RequestParams();
        params.put("enews", "DelFava");
        params.put("userid", mUser.getUserid());
        params.put("username", mUser.getHym());
        params.put("rnd", mUser.getRnd());
        params.put("favaid", collectModel.getSid());
        H.USER(params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    String ts = jsonObject.getString("ts");
//                            String zt = jsonObject.getString("zt");
                    if (!TextUtils.isEmpty(ts)) {

                    }
//                        MyUtitls.showToast(MyCollectActivity.this, ts);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("AsyncHttpClientUtils:", "" + json);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
