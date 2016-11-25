package com.lh16808.app.lhds.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.lh16808.app.lhds.R;
import com.lh16808.app.lhds.fragment.MainFragment;
import com.lh16808.app.lhds.model.Lottery;
import com.lh16808.app.lhds.model.XuanJi;

import java.util.ArrayList;

public class ListViewPagerAdapter extends BaseAdapter {
    ArrayList<XuanJi> xjList = new ArrayList<>();
    private Context mContext;
    Typeface typeface;

    public ListViewPagerAdapter(Context mContext, ArrayList<XuanJi> xjList) {
        this.mContext = mContext;
        this.xjList = xjList;
        typeface = setSlogan();
    }

    @Override
    public int getCount() {
        return xjList.size();
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_mystery, null);
            viewHolder.tv1 = (TextView) convertView.findViewById(R.id.tv_title_1);
            viewHolder.tv2 = (TextView) convertView.findViewById(R.id.tv_jie_text);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_jie_title);
            viewHolder.tv3 = (TextView) convertView.findViewById(R.id.tv_jie);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        setData(position, viewHolder);
        return convertView;
    }

    private void setData(int position, ViewHolder viewHolder) {
        boolean isVisible_ = false;
        Lottery lottery = MainFragment.mLottery;
        String bq = lottery.bq;
        XuanJi xuanJi = xjList.get(position);
        viewHolder.tv1.setTypeface(typeface);
        viewHolder.tv_title.setText(xuanJi.getTitle());
        viewHolder.tv1.setText(xuanJi.getXianji());
        if (TextUtils.isEmpty(xuanJi.getJieda()) || isVisible_) {
            viewHolder.tv3.setVisibility(View.GONE);
            viewHolder.tv2.setVisibility(View.GONE);
        } else {
            viewHolder.tv3.setVisibility(View.VISIBLE);
            viewHolder.tv3.setTypeface(typeface);
            viewHolder.tv2.setVisibility(View.VISIBLE);
            viewHolder.tv2.setText(xuanJi.getJieda());
        }
    }

    class ViewHolder {
        TextView tv_title;
        TextView tv1;
        TextView tv2;
        TextView tv3;
    }

    Typeface setSlogan() {
        Typeface fontFace = null;
        try {
            AssetManager mgr = mContext.getAssets();
            fontFace = Typeface.createFromAsset(mgr, "fonts/baoli.ttc");
        } catch (Exception e) {
        }
        return fontFace;
    }
}