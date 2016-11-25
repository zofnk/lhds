package com.lh16808.app.lhds.activity;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.lh16808.app.lhds.R;
import com.lh16808.app.lhds.adapter.ZiliaoAdapter;
import com.lh16808.app.lhds.base.BaseActivity;
import com.lh16808.app.lhds.model.CateDetailModel;
import com.lh16808.app.lhds.other.MyProgressDialog;
import com.lh16808.app.lhds.utils.ToastUtil;
import com.lh16808.app.lhds.utils.http.H;
import com.lh16808.app.lhds.widget.MyLinearLayoutManager;
import com.lh16808.app.lhds.widget.RecyclerViewDecoration;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class NatureActivity extends BaseActivity {

    private View view;
    private int arg0;
    private RecyclerView mRecyclerView;
    private int star;
    private ArrayList<CateDetailModel> list = new ArrayList<>();
    private ArrayList<CateDetailModel> morelist = new ArrayList<>();
    private ZiliaoAdapter mAdapter;
    private MyLinearLayoutManager mLayoutManager;
    private FragmentActivity mActivity;
    private boolean isLoad;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_nature);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_nature_list);
        mActivity = this;
        initAdapter();
        getData();
    }


    private void initAdapter() {
        mLayoutManager = new MyLinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new RecyclerViewDecoration(mActivity, LinearLayoutManager.VERTICAL));
        mAdapter = new ZiliaoAdapter(NatureActivity.this, list, new ZiliaoAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(mActivity, ZiliaoDetailActivity.class);
                intent.putExtra("CateDetailModel", list.get(position));
                mActivity.startActivity(intent);
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(-1)) {
                    Log.e("-------onScrolled", "onScrolledToTop()");
                } else if (!recyclerView.canScrollVertically(1)) {
                    Log.e("-------onScrolled", "onScrolledToBottom()");
//                    ToastUtil.toastShow(mActivity, "上拉可加载更多...");
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);

    }

    private void getData() {
        MyProgressDialog.dialogShow(this);
        H.ZILIAO(7, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                MyProgressDialog.dialogHide();
                String json = new String(responseBody);
                try {
                    JSONArray jsonArray = new JSONArray(json);
                    int length = jsonArray.length();
                    if (length > 0) {
                        for (int i = 0; i < length; i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String url = jsonObject.getString("sid");
                            String title = jsonObject.getString("title");
                            String newstime = jsonObject.getString("newstime");
                            CateDetailModel cateModel = new CateDetailModel(title, url, newstime);
                            list.add(cateModel);
                        }
                    } else {
                        ToastUtil.toastShow(NatureActivity.this, "数据已结束");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ToastUtil.toastShow(NatureActivity.this, "" + morelist.size());
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyProgressDialog.dialogHide();
                ToastUtil.toastShow(NatureActivity.this, "网络错误~");
            }
        });
    }

    @Override
    protected void loadData() {

    }
}
