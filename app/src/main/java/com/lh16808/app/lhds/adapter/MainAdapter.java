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

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    String[] title = {"历史记录", "开奖日期", "论坛", "玄机", "资料", "图库"};
    String[] text = {"回顾开奖结果", "把握准确的时间", "第一手专家", "精准的玄机预测", "丰富的爆料", "全面的图料"};
    int[] ico = {R.drawable.ico_main_lishijilu, R.drawable.ico_main_kaijianriqo, R.drawable.ico_main_luntan, R.drawable.ico_main_xuanji, R.drawable.ico_main_ziliao, R.drawable.ico_main_tuku};

    public MainAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_main_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final MyViewHolder holder1 = (MyViewHolder) holder;
        holder1.tvTitel.setText(title[position]);
        holder1.tvText.setText(text[position]);
        holder1.ivIco.setImageResource(ico[position]);

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder1.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder1.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder1.itemView, pos);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return title.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitel;
        ImageView ivIco;
        TextView tvText;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivIco = (ImageView) itemView.findViewById(R.id.img_main_item_ico);
            tvText = (TextView) itemView.findViewById(R.id.tv_main_item_text);
            tvTitel = (TextView) itemView.findViewById(R.id.tv_main_item_title);
        }
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    private MainAdapter.OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(MainAdapter.OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

}
