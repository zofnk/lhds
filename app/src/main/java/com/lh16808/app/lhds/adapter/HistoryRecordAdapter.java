package com.lh16808.app.lhds.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.lh16808.app.lhds.R;
import com.lh16808.app.lhds.marco.Constants;
import com.lh16808.app.lhds.model.HistoryRecordModel;
import com.lh16808.app.lhds.utils.MyUtils;
import com.lh16808.app.lhds.utils.dataUtils.DateFormatUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by admin on 2016/10/14.
 */
public class HistoryRecordAdapter extends RecyclerView.Adapter<HistoryRecordAdapter.MyViewHolder> {

    public List<HistoryRecordModel> mDatas = new ArrayList<>();
    public Context mContext;
    MyItemClickListener longClickListener;

    public HistoryRecordAdapter(Context mContext, MyItemClickListener longClickListener) {
        this.mContext = mContext;
        this.longClickListener = longClickListener;
    }

    public void notifyDataSetChanged(List<HistoryRecordModel> mDatas) {
        this.mDatas.addAll(mDatas);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_history_record, parent, false);
        MyViewHolder mVH = new MyViewHolder(view);
        return mVH;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        HistoryRecordModel lottery = mDatas.get(position);
        holder.tvDate.setText(DateFormatUtils.formatWithYMD2(Long.valueOf(mDatas.get(position).getNewstime()) * 1000));
        holder.tvNum.setText(mDatas.get(position).getBq() + "期");
        holder.tvZ1m.setBackgroundResource(MyUtils.isRBG(lottery.z1m));
        holder.tvZ2m.setBackgroundResource(MyUtils.isRBG(lottery.z2m));
        holder.tvZ3m.setBackgroundResource(MyUtils.isRBG(lottery.z3m));
        holder.tvZ4m.setBackgroundResource(MyUtils.isRBG(lottery.z4m));
        holder.tvZ5m.setBackgroundResource(MyUtils.isRBG(lottery.z5m));
        holder.tvZ6m.setBackgroundResource(MyUtils.isRBG(lottery.z6m));
        holder.tvTm.setBackgroundResource(MyUtils.isRBG(lottery.tm));
        holder.tvZ1m.setText(lottery.z1m);
        holder.tvZ2m.setText(lottery.z2m);
        holder.tvZ3m.setText(lottery.z3m);
        holder.tvZ4m.setText(lottery.z4m);
        holder.tvZ5m.setText(lottery.z5m);
        holder.tvZ6m.setText(lottery.z6m);
        holder.tvTm.setText(lottery.tm);
        holder.tvZ1sx.setText(lottery.z1sx);
        holder.tvZ2sx.setText(lottery.z2sx);
        holder.tvZ3sx.setText(lottery.z3sx);
        holder.tvZ4sx.setText(lottery.z4sx);
        holder.tvZ5sx.setText(lottery.z5sx);
        holder.tvZ6sx.setText(lottery.z6sx);
        holder.tvTmsx.setText(lottery.tmsx);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                longClickListener.onItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private int isRBG(String str) {
        int parseInt = Integer.parseInt(str);
        for (int i = 0; i < Constants.redbo.length; i++) {
            if (parseInt == Constants.redbo[i]) {
                return 0;
            }
        }
        for (int i = 0; i < Constants.bulebo.length; i++) {
            if (parseInt == Constants.bulebo[i]) {
                return 1;
            }
        }
        for (int i = 0; i < Constants.greenbo.length; i++) {
            if (parseInt == Constants.greenbo[i]) {
                return 2;
            }
        }
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvDate;
        private TextView tvNum;
        private TextView tvZ1m;
        private TextView tvZ1sx;
        private TextView tvZ2m;
        private TextView tvZ2sx;
        private TextView tvZ3m;
        private TextView tvZ3sx;
        private TextView tvZ4m;
        private TextView tvZ4sx;
        private TextView tvZ5m;
        private TextView tvZ5sx;
        private TextView tvZ6m;
        private TextView tvZ6sx;
        private TextView tvTm;
        private TextView tvTmsx;


        public MyViewHolder(View itemView) {
            super(itemView);

            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvNum = (TextView) itemView.findViewById(R.id.tv_num);
            tvZ1m = (TextView) itemView.findViewById(R.id.tv_z1m);
            tvZ1sx = (TextView) itemView.findViewById(R.id.tv_z1sx);
            tvZ2m = (TextView) itemView.findViewById(R.id.tv_z2m);
            tvZ2sx = (TextView) itemView.findViewById(R.id.tv_z2sx);
            tvZ3m = (TextView) itemView.findViewById(R.id.tv_z3m);
            tvZ3sx = (TextView) itemView.findViewById(R.id.tv_z3sx);
            tvZ4m = (TextView) itemView.findViewById(R.id.tv_z4m);
            tvZ4sx = (TextView) itemView.findViewById(R.id.tv_z4sx);
            tvZ5m = (TextView) itemView.findViewById(R.id.tv_z5m);
            tvZ5sx = (TextView) itemView.findViewById(R.id.tv_z5sx);
            tvZ6m = (TextView) itemView.findViewById(R.id.tv_z6m);
            tvZ6sx = (TextView) itemView.findViewById(R.id.tv_z6sx);
            tvTm = (TextView) itemView.findViewById(R.id.tv_tm);
            tvTmsx = (TextView) itemView.findViewById(R.id.tv_tmsx);


        }
    }

    public interface MyItemClickListener {
        public void onItemClick(View view, int postion);
    }
}
