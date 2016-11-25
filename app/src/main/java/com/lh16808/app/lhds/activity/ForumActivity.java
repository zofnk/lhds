package com.lh16808.app.lhds.activity;

import android.content.Intent;
import android.os.Bundle;


import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.lh16808.app.lhds.R;
import com.lh16808.app.lhds.adapter.ForumAdapter;
import com.lh16808.app.lhds.base.BaseActivity;
import com.lh16808.app.lhds.model.Data;
import com.lh16808.app.lhds.model.ForumModel;
import com.lh16808.app.lhds.other.ShowBannerInfo;
import com.lh16808.app.lhds.utils.ToastUtil;
import com.lh16808.app.lhds.utils.http.H;
import com.lh16808.app.lhds.widget.MyLinearLayoutManager;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ForumActivity extends BaseActivity implements SwipyRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private ForumAdapter mAdapter;
    private int yeShu;
    private ArrayList<ForumModel> list = new ArrayList<ForumModel>();
    private boolean oneload;
    private boolean loading;
    private SwipyRefreshLayout srlForum;
    ArrayList<ForumModel> morelist = new ArrayList<>();
    private ShowBannerInfo mShowBannerInfo;

    @Override
    protected void initVariables() {
        View rlBanner = findViewById(R.id.rl_forum_banner);
        ViewPager vpBanner = (ViewPager) findViewById(R.id.vp_forum_banner);
        mShowBannerInfo = new ShowBannerInfo(this, rlBanner, vpBanner);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_forum);
        initRecycView();
        initRefresh();
        initData();
    }

    @Override
    protected void loadData() {

    }

    private void initRefresh() {
        srlForum = (SwipyRefreshLayout) findViewById(R.id.srl_forum);
        srlForum.setOnRefreshListener(this);
        autoRefresh(true);
    }

    private void initRecycView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_forum);
        //设置布局管理器
        mRecyclerView.setLayoutManager(new MyLinearLayoutManager(this));
        //设置adapter
        mAdapter = new ForumAdapter(this);
        mAdapter.setOnItemClickLitener(new ForumAdapter.OnItemClickLitener() {

            @Override
            public void onItemClick(View view, int position) {
                if (list.size() > 0) {
                    Intent intent = new Intent(ForumActivity.this, ForumDetailActivity.class);
                    ForumModel forumModel = ForumActivity.this.list.get(position);
                    intent.putExtra("forumModel", forumModel);
                    startActivity(intent);
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(-1)) {
                    Log.e("-------onScrolled", "onScrolledToTop()");
                } else if (!recyclerView.canScrollVertically(1)) {
                    Log.e("-------onScrolled", "onScrolledToBottom()");
                    if (!loading) {
                        autoRefresh(false);
                    }
                }
            }
        });
    }

    private void initData() {
//        if (list.size() > 0) {
//            list.add(null);

        // notifyItemInserted(int position)，这个方法是在第position位置
        // 被插入了一条数据的时候可以使用这个方法刷新，
        // 注意这个方法调用后会有插入的动画，这个动画可以使用默认的，也可以自己定义。
//            mAdapter.notifyItemInserted(list.size() - 1);
//        }
        RequestParams params = new RequestParams();
        params.add("enews", "bbslist");
        params.add("star", String.valueOf(yeShu));
//        H.BBS(new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                srlForum.setRefreshing(false);
//                String s = new String(responseBody);
////                Data.getInstance().setBBS(s);
//                try {
//                    JSONArray list = new JSONArray(s);
//                    for (int i = 0; i < list.length(); i++) {
//                        JSONObject jsonObject1 = list.getJSONObject(i);
//                        String id = jsonObject1.getString("sid");
//                        String title = jsonObject1.getString("title");
//                        String userpic = jsonObject1.getString("userpic");
//                        String groupname = jsonObject1.getString("groupname");
//                        String username = jsonObject1.getString("username");
//                        String userfen = jsonObject1.getString("userfen");
//                        String newstime = jsonObject1.getString("newstime");
//                        String onclick = jsonObject1.getString("onclick");
//                        int rnum = jsonObject1.getInt("rnum");
//                        ForumModel forumModel = new ForumModel(id, title, userpic, groupname, username, userfen, newstime, onclick, rnum);
//                        morelist.add(forumModel);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                List<ForumModel> forumModels = null;
//                if (morelist.size() > 20) {
//                    forumModels = morelist.subList(0, 20);
//                } else {
//                    forumModels = morelist;
//                }
//                loading = false;
//                list.addAll(forumModels);
//                mAdapter.changeData(list);
//                oneload = true;
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                loading = false;
//                srlForum.setRefreshing(false);
//            }
//        });
        H.ForumData(params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                srlForum.setRefreshing(false);
                String s = new String(responseBody);
                try {
                    JSONArray list = new JSONArray(s);
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject jsonObject1 = list.getJSONObject(i);
                        String id = jsonObject1.getString("sid");
                        String title = jsonObject1.getString("title");
                        String userpic = jsonObject1.getString("userpic");
                        String groupname = jsonObject1.getString("groupname");
                        String username = jsonObject1.getString("username");
                        String userfen = jsonObject1.getString("userfen");
                        String newstime = jsonObject1.getString("newstime");
                        String onclick = jsonObject1.getString("onclick");
                        int rnum = jsonObject1.getInt("rnum");
                        ForumModel forumModel = new ForumModel(id, title, userpic, groupname, username, userfen, newstime, onclick, rnum);
                        morelist.add(forumModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                if (list.size() > 0) {
//                    //删除 footer
//                    list.remove(list.size() - 1);
                loading = false;
//                }
                list.addAll(morelist);
                mAdapter.changeData(list);
                oneload = true;
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                if (list.size() > 0) {
//                    //删除 footer
//                    list.remove(list.size() - 1);
                loading = false;
//                    mAdapter.notifyDataSetChanged();
//                }
                srlForum.setRefreshing(false);
//                ShowDialog.showDialogNetError(ForumActivity.this, onNetTimeOut);
            }
        });
    }

    //    MyUtitls.OnNetTimeOut onNetTimeOut = new MyUtitls.OnNetTimeOut() {
//        @Override
//        public void timeOut() {
//            initData();
//        }
//
//        @Override
//        public void error() {
//            finish();
//        }
//    };
    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (direction == SwipyRefreshLayoutDirection.TOP ? true : false) {
            yeShu = 0;
            ForumActivity.this.list.clear();
            mAdapter.notifyDataSetChanged();
            initData();
//            add();
        } else {
            yeShu += 20;
//            add();
            loading = true;
            initData();
            ToastUtil.toastShow(ForumActivity.this, "加载中...");
        }
    }

//    private void add() {
//        int toIndex = yeShu + 20;
//        if (toIndex < morelist.size()) {
//            final List<ForumModel> forumModels = morelist.subList(yeShu, toIndex);
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    srlForum.setRefreshing(false);
//                    loading = false;
//                    list.addAll(forumModels);
//                    mAdapter.changeData(list);
//                    oneload = true;
//                }
//            }, 1500);
//        } else {
//            ToastUtil.toastShow(ForumActivity.this, "加载已到底部...");
//        }
//    }

    private void autoRefresh(boolean b) {
        srlForum.post(new Runnable() {
            @Override
            public void run() {
                srlForum.setRefreshing(true);
            }
        });
        if (b) {
            this.onRefresh(SwipyRefreshLayoutDirection.TOP);
        } else {
            this.onRefresh(SwipyRefreshLayoutDirection.BOTTOM);
        }

    }
}
