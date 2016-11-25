package com.lh16808.app.lhds.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lh16808.app.lhds.R;

/**
 * Created by Administrator on 2016/11/14.
 */

public class AnalyseAdapter extends RecyclerView.Adapter<AnalyseAdapter.MyHolder> {
    public String[] AnalyseTitle = new String[]{"走势分析", "六合属性", "幸运转盘", "生肖卡牌", "摇一摇", "寻宝"};
    public int[] AnalyseIco = new int[]{R.drawable.ico_analyse_zoushifx, R.drawable.ico_analyse_shuxing, R.drawable.ico_analyse_zhuangpan, R.drawable.ico_analyse_kapai, R.drawable.ico_analyse_yaoyiyao, R.drawable.ico_analyse_xunbao};
    //    "幸运转盘", "生肖卡牌", "摇一摇", "拼图", "刮刮运气", "恋人特码", "天机测算", "生肖转盘",
    Context mContext;

    public AnalyseAdapter(Context context) {
        mContext = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(mContext).inflate(R.layout.item_layout_analyse, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        holder.tvTitle.setText(AnalyseTitle[position]);
        holder.ivIco.setImageResource(AnalyseIco[position]);
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return AnalyseTitle.length;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView ivIco;

        public MyHolder(View itemView) {
            super(itemView);
            ivIco = (ImageView) itemView.findViewById(R.id.img_analyse_item_title);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_analyse_item_title);
        }
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    private AnalyseAdapter.OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(AnalyseAdapter.OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


}
