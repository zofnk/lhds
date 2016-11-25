package com.lh16808.app.lhds.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.lh16808.app.lhds.R;
import com.lh16808.app.lhds.adapter.ListViewPagerAdapter;
import com.lh16808.app.lhds.marco.ApiConfig;
import com.lh16808.app.lhds.model.Lottery;
import com.lh16808.app.lhds.model.XuanJi;
import com.lh16808.app.lhds.other.MyProgressDialog;
import com.lh16808.app.lhds.utils.http.H;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class MysteryFragment extends Fragment {


    private int arg0;
    private View vb_ll;
    private WebView vb_wv;
    private RadioButton mRadioButton1;
    private RadioButton mRadioButton2;
    private RadioGroup mRadioGroup;
    private SwipyRefreshLayout mSwipyRefreshLayout;
    private ListView mListView;
    private String type = "24";
    private boolean isRefash;
    private String qishu;
    ArrayList<XuanJi> xjList = new ArrayList<>();
    private ListViewPagerAdapter mAdapter;
    private SwipyRefreshLayout webSrl;

    @SuppressLint("ValidFragment")
    public MysteryFragment(int arg0) {
        super();
        this.arg0 = arg0;
    }

    @SuppressLint("ValidFragment")
    public MysteryFragment() {
        super();
    }

    View view;
    FragmentActivity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            mActivity = getActivity();
            view = inflater.inflate(R.layout.fragment_mystery, container, false);
            initUI();
        }
        return view;
    }

    String nowUrl;

    private void autoRefresh() {
        webSrl.post(new Runnable() {
            @Override
            public void run() {
                webSrl.setRefreshing(true);
            }
        });
        listener2.onRefresh(SwipyRefreshLayoutDirection.TOP);
    }

    SwipyRefreshLayout.OnRefreshListener listener2 = new SwipyRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh(SwipyRefreshLayoutDirection direction) {
            vb_wv.loadUrl(nowUrl);
        }
    };

    private void initUI() {
        vb_ll = view.findViewById(R.id.ll_ft_mystery);
        webSrl = (SwipyRefreshLayout) view.findViewById(R.id.rls_ft_ms_web);
        if (arg0 == 2) {
            vb_wv = (WebView) view.findViewById(R.id.web_ft_mystery);
            vb_ll.setVisibility(View.GONE);
            webSrl.setVisibility(View.VISIBLE);
            nowUrl = ApiConfig.getBaseUrl(ApiConfig.KJURL);
            autoRefresh();
            uriIntent();
            webSrl.setOnRefreshListener(listener2);
        } else {
            mAdapter = new ListViewPagerAdapter(getActivity(), xjList);
            mSwipyRefreshLayout = (SwipyRefreshLayout) view.findViewById(R.id.srl_ft_ms_refresh);
            mSwipyRefreshLayout.setOnRefreshListener(listener);
            mListView = (ListView) view.findViewById(R.id.rv_ft_ms_list);
            mRadioGroup = (RadioGroup) view.findViewById(R.id.radio_shenghe);
            mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    //获取变更后的选中项的ID
                    int radioButtonId = group.getCheckedRadioButtonId();
                    switch (radioButtonId) {
                        default:
                        case R.id.radio_shenghe1:
                            xjList.clear();
                            type = "24";
                            break;
                        case R.id.radio_shenghe2:
                            xjList.clear();
                            type = "25";
                            break;
                    }
                    initData();
                }
            });
            // 已审核
            mRadioButton1 = (RadioButton) view.findViewById(R.id.radio_shenghe1);
            // 未审核
            mRadioButton2 = (RadioButton) view.findViewById(R.id.radio_shenghe2);
            if (arg0 == 0) {
                mRadioButton1.setText("铁板神诗");
                mRadioButton2.setText("特码诗");
            } else {
                mRadioButton1.setText("脑筋急转弯");
                mRadioButton2.setText("玄机笑话");
            }
            initData();
            mListView.setAdapter(mAdapter);
        }
    }

    public String fourmatSum(int qishu) {
        StringBuffer str = new StringBuffer();
        if (qishu < 100)
            str.append("0");
        if (qishu < 10)
            str.append("0");
        str.append(qishu);
        return str.toString();
    }

    private void autoRefresh(boolean b) {
        mSwipyRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipyRefreshLayout.setRefreshing(true);
            }
        });
        if (b) {
            listener.onRefresh(SwipyRefreshLayoutDirection.TOP);
        } else {
            listener.onRefresh(SwipyRefreshLayoutDirection.BOTTOM);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void uriIntent() {
        WebSettings settings = vb_wv.getSettings();
        settings.setJavaScriptEnabled(true);
        vb_wv.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                webSrl.setRefreshing(false);
            }
        });
        vb_wv.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            webSrl.setRefreshing(false);
                        }
                    }, 500);
                }
            }
        });
        vb_wv.loadUrl(nowUrl);
    }

    private void initData() {
        autoRefresh(true);
        mRadioButton2.setEnabled(false);
        mRadioButton1.setEnabled(false);
        RequestParams p = new RequestParams();
        p.put("enews", "xianji");
        switch (arg0) {
            default:
            case 0:
                p.put("classid", "93");
                break;
            case 1:
                p.put("classid", "94");
                break;
        }
        p.put("typeid", type);
        if (isRefash)
            p.put("qishu", qishu);
        H.XianJi(p, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        mSwipyRefreshLayout.setRefreshing(false);
                        mRadioButton1.setEnabled(true);
                        mRadioButton2.setEnabled(true);
                        String s = new String(responseBody);
                        String qishu = MysteryFragment.this.qishu;
                        try {
                            JSONObject JSONObject = new JSONObject(s);
                            int zt = JSONObject.getInt("zt");
                            if (zt == 0) {
                                Toast.makeText(mActivity, "沒有更早的數據", Toast.LENGTH_SHORT).show();
                            } else {
                                String title = JSONObject.getString("title");
                                String xianji = JSONObject.getString("xianji");
                                String jieda = JSONObject.getString("jieda");
                                if (!isRefash) {
                                    qishu = JSONObject.getString("qishu");
                                }
                                XuanJi xuanJi = new XuanJi(qishu, title, xianji, jieda);
                                xjList.add(xuanJi);
                                if (mAdapter != null) {
                                    mAdapter.notifyDataSetChanged();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        isRefash = false;
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers,
                                          byte[] responseBody, Throwable error) {
                        isRefash = false;
                        mSwipyRefreshLayout.setRefreshing(false);
                        mRadioButton1.setEnabled(true);
                        mRadioButton2.setEnabled(true);
                    }
                }

        );
    }

    SwipyRefreshLayout.OnRefreshListener listener = new SwipyRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh(SwipyRefreshLayoutDirection direction) {
            if (direction == SwipyRefreshLayoutDirection.TOP) {

            } else {
                isRefash = true;
                int qishu = Integer.parseInt(xjList.get(0).getQishu());
                int i = qishu - xjList.size();
                MysteryFragment.this.qishu = fourmatSum(i);
                initData();
            }

        }
    };
}
