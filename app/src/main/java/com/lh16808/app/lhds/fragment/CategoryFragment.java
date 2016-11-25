package com.lh16808.app.lhds.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lh16808.app.lhds.R;
import com.lh16808.app.lhds.activity.CategoryDetailActivity;
import com.lh16808.app.lhds.adapter.CategoryAdapter;
import com.lh16808.app.lhds.model.CateModel;
import com.lh16808.app.lhds.utils.ToastUtil;
import com.lh16808.app.lhds.utils.http.H;
import com.lh16808.app.lhds.widget.MyLinearLayoutManager;
import com.lh16808.app.lhds.widget.RecyclerViewDecoration;
import com.lh16808.app.lhds.widget.imagelook.ui.ImagePagerActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment implements SwipyRefreshLayout.OnRefreshListener {

    private int position;
    private RecyclerView mRecyclerView;
    View view;
    @SuppressLint("ValidFragment")
    public CategoryFragment() {
    }
    @SuppressLint("ValidFragment")
    public CategoryFragment(int position) {
        this.position = position;
    }

    String[] classid = {"66", "89", "92", "65"};
    ArrayList<CateModel> list = new ArrayList<>();
    CategoryAdapter mAdapter;
    FragmentActivity mActivity;
    SwipyRefreshLayout mSwipyRefreshLayout;
    MyLinearLayoutManager mLayoutManager;

    private void autoRefresh(boolean b) {
        mSwipyRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipyRefreshLayout.setRefreshing(true);
            }
        });
        if (b) {
            this.onRefresh(SwipyRefreshLayoutDirection.TOP);
        } else {
            this.onRefresh(SwipyRefreshLayoutDirection.BOTTOM);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            mActivity = getActivity();
            view = inflater.inflate(R.layout.fragment_category, container, false);
            mSwipyRefreshLayout = (SwipyRefreshLayout) view.findViewById(R.id.srl_cy_refresh);
            mSwipyRefreshLayout.setOnRefreshListener(this);
            autoRefresh(true);
            mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_cf_list);
            mLayoutManager = new MyLinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.addItemDecoration(new RecyclerViewDecoration(getActivity(), LinearLayoutManager.VERTICAL));
            mAdapter = new CategoryAdapter(getActivity(), new CategoryAdapter.OnItemClickLitener() {


                @Override
                public void onItemClick(View view, int position) {
                    if (CategoryFragment.this.position != 3) {
                        Intent intent = new Intent(mActivity, CategoryDetailActivity.class);
                        intent.putExtra("type", 0);
                        intent.putExtra("des", list.get(position).getTitle());
                        ArrayList<String> urls = null;
                        urls = getUrl(position);
                        intent.putStringArrayListExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(mActivity, ImagePagerActivity.class);
                        intent.putExtra("type", 0);
                        intent.putExtra("des", list.get(position).getTitle());
                        ArrayList<String> urls = new ArrayList<>();
                        urls.add(list.get(position).getUrl());
                        intent.putStringArrayListExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
                        startActivity(intent);
                    }
                }
            }, list);
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
                        ToastUtil.toastShow(mActivity, "上拉可加载更多...");
                    }
                }
            });
            mRecyclerView.setAdapter(mAdapter);
        }
        return view;
    }

    @NonNull
    private ArrayList<String> getUrl(int i) {
        ArrayList<String> urls = new ArrayList<>();
        CateModel cateModel = list.get(i);
        String qishu = cateModel.getQishu();
        Integer integer = Integer.valueOf(qishu);
        for (int j = integer; j > 0; j--) {
            String s = cateModel.getUrl() + getStr(j) + "/" + cateModel.getType() + ".jpg";
            urls.add(s);
        }
        return urls;
    }

    private String getStr(int a) {
        StringBuffer sb = new StringBuffer();
        if (a < 100) {
            sb.append("0");
        } else if (a < 10) {
            sb.append("0");
        }
        sb.append(a);
        return sb.toString();
    }

    int star = 0;

    private void loadData() {
        //        六合图库,,,
//        66,彩色图库,,
//        89,玄机彩图,,
//        92,黑白图库,,
//        65,全年图库,,
        RequestParams params = new RequestParams();
        params.add("enews", "photolist");
        params.add("classid", classid[position]);
        params.add("star", String.valueOf(star));
        H.TuKu(params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                mSwipyRefreshLayout.setRefreshing(false);
                String json = new String(responseBody);
                try {
                    JSONArray jsonArray = new JSONArray(json);
                    int length = jsonArray.length();
                    if (length > 0) {
                        for (int i = 0; i < length; i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String type = "";
                            String qishu = "";
                            if (position != 3) {
                                qishu = jsonObject.getString("qishu");
                                type = jsonObject.getString("type");
                            }
                            String url = jsonObject.getString("url");
                            String title = jsonObject.getString("title");
                            String newstime = jsonObject.getString("newstime");
                            CateModel cateModel = new CateModel(qishu, url, title, type, newstime);
                            list.add(cateModel);
                        }
                    } else {
                        ToastUtil.toastShow(getActivity(), "数据已结束");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                mSwipyRefreshLayout.setRefreshing(false);
                ToastUtil.toastShow(getActivity(), "网络错误~");
            }
        });
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (direction == SwipyRefreshLayoutDirection.TOP) {
            ToastUtil.toastShow(mActivity, "加载中...");
            list.clear();
            star = 0;
            loadData();
        } else {
            ToastUtil.toastShow(mActivity, "加载中...");
            star += 20;
            loadData();
        }
    }
}
