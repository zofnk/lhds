package com.lh16808.app.lhds.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lh16808.app.lhds.R;
import com.lh16808.app.lhds.model.CateModel;
import com.lh16808.app.lhds.utils.dataUtils.DateFormatUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private Context context;

    public CategoryAdapter(Context context, CategoryAdapter.OnItemClickLitener mOnItemClickLitener, List<CateModel> list) {

        this.context = context;
        this.mOnItemClickLitener = mOnItemClickLitener;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_category_list, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        CateModel cateModel = list.get(position);
        holder.tvTitle.setText(cateModel.getTitle());
        holder.tvTime.setText(DateFormatUtils.formatWithMDHm(Long.parseLong(cateModel.getNewstime()) * 1000));
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
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvTime;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_cy_title);
            tvTime = (TextView) itemView.findViewById(R.id.tv_cy_time);
        }
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    private CategoryAdapter.OnItemClickLitener mOnItemClickLitener;
    private List<CateModel> list;

    public void setOnItemClickLitener(CategoryAdapter.OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
