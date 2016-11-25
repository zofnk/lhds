package com.lh16808.app.lhds.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lh16808.app.lhds.R;
import com.lh16808.app.lhds.adapter.HistoryRecordAdapter;
import com.lh16808.app.lhds.base.BaseActivity;
import com.lh16808.app.lhds.model.HistoryRecordModel;
import com.lh16808.app.lhds.other.MyProgressDialog;
import com.lh16808.app.lhds.utils.MyUtils;
import com.lh16808.app.lhds.utils.http.H;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by admin on 2016/10/14.
 */
public class HistoryRecordActivity extends BaseActivity {

    public RecyclerView mRecyclerView;
    public HistoryRecordAdapter mAdapter;
    public List<HistoryRecordModel> mDatas;
    public View view;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_history_record);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        mDatas = new ArrayList<>();
        mAdapter = new HistoryRecordAdapter(this, new HistoryRecordAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Intent intent1 = new Intent();
                intent1.setClass(HistoryRecordActivity.this, HistoryInfoActivity.class);
                intent1.putExtra("INFO", mDatas.get(postion));
                startActivity(intent1);
            }
        });
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(lm);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void loadData() {
        MyProgressDialog.dialogShow(this);
        int getYear = MyUtils.GetYear();
        String year = String.valueOf(getYear);
        H.HisData(year, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                MyProgressDialog.dialogHide();
                ArrayList<HistoryRecordModel> hisList = new ArrayList<>();
                String arg0 = new String(responseBody);
                try {
                    JSONArray jsonArray = new JSONArray(arg0);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String bq = jsonObject.getString("bq");
                        String z1m = jsonObject.getString("z1m");
                        String z1sx = jsonObject.getString("z1sx");
                        String z2m = jsonObject.getString("z2m");
                        String z2sx = jsonObject.getString("z2sx");
                        String z3m = jsonObject.getString("z3m");
                        String z3sx = jsonObject.getString("z3sx");
                        String z4m = jsonObject.getString("z4m");
                        String z4sx = jsonObject.getString("z4sx");
                        String z5m = jsonObject.getString("z5m");
                        String z5sx = jsonObject.getString("z5sx");
                        String z6m = jsonObject.getString("z6m");
                        String z6sx = jsonObject.getString("z6sx");
                        String tm = jsonObject.getString("tm");
                        String tmsx = jsonObject.getString("tmsx");
                        String newstime = jsonObject.getString("newstime");
                        HistoryRecordModel hisData = new HistoryRecordModel(bq, tm, tmsx, z1m, z1sx, z2m, z2sx, z3m, z3sx, z4m, z4sx, z5m, z5sx,
                                z6m, z6sx, newstime);
                        hisList.add(hisData);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (hisList.size() > 0) {
                    mDatas.clear();
                    mDatas.addAll(hisList);
                    mAdapter.notifyDataSetChanged(mDatas);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyProgressDialog.dialogHide();
            }
        });
    }


}
