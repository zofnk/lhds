package com.lh16808.app.lhds.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.lh16808.app.lhds.R;
import com.lh16808.app.lhds.model.ForumModel;
import com.lh16808.app.lhds.utils.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/11/7.
 */

public class ForumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    List<ForumModel> list;

    public ForumAdapter(Context context) {
        mContext = context;
    }

    public void changeData(List<ForumModel> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View view;
//        switch (viewType) {
//            default:
//            case TYPE_FOOTER:
//                vh = new ForumAdapter.FooterViewHolder(LayoutInflater.from(
//                        mContext).inflate(R.layout.sample_common_list_footer_loading, parent,
//                        false));
//                return vh;
//            case TYPE_NORMAL:
        vh = new ForumAdapter.MyViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_rv_forum, parent,
                false));
        return vh;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
//        //这时候 article是 null，先把 footer 处理了
//        if (holder instanceof FooterViewHolder) {
////            ((FooterViewHolder) holder).rcvLoadMore.spin();
//            return;
//        }
//        if (holder instanceof MyViewHolder) {
        MyViewHolder holderx = (MyViewHolder) holder;
        ForumModel forumModel = list.get(position);
        holderx.tv_forum_name.setText(forumModel.getUsername());
        holderx.tv_forum_time.setText(forumModel.getNewstime());
        holderx.tv_forum_sum.setText("已有" + forumModel.getOncliclk() + "人阅读");
        holderx.tv_forum_title.setText(forumModel.getTitle());
        ImageLoader.LoaderNetHead(mContext, forumModel.getUserpic(), holderx.img_forum_ico);
        holderx.tv_forum_fenShu.setText(forumModel.getUserfen());
        holderx.tv_forum_dengJi.setText(forumModel.getGroupname());
        holderx.tv_forum_huiFu.setText(String.valueOf(forumModel.getRnum()));
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

//            }
        }
    }
//    public final static int TYPE_FOOTER = 2;//底部--往往是loading_more
//    public final static int TYPE_NORMAL = 1; // 正常的一条文章
//
//    @Override
//    public int getItemViewType(int position) {
//        if (list.get(position) == null) {
//            return TYPE_FOOTER;
//        } else {
//            return TYPE_NORMAL;
//        }
//    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        else
            return 0;
    }

class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView img_forum_ico;
    TextView tv_forum_name;
    TextView tv_forum_dengJi;
    TextView tv_forum_fenShu;
    TextView tv_forum_time;
    TextView tv_forum_sum;
    TextView tv_forum_title;
    TextView tv_forum_huiFu;

    public MyViewHolder(View itemView) {
        super(itemView);
        tv_forum_huiFu = (TextView) itemView.findViewById(R.id.tv_forum_huiFu);
        tv_forum_title = (TextView) itemView.findViewById(R.id.tv_forum_title);
        tv_forum_sum = (TextView) itemView.findViewById(R.id.tv_forum_sum);
        tv_forum_time = (TextView) itemView.findViewById(R.id.tv_forum_time);
        tv_forum_fenShu = (TextView) itemView.findViewById(R.id.tv_forum_fenShu);
        tv_forum_dengJi = (TextView) itemView.findViewById(R.id.tv_forum_dengJi);
        tv_forum_name = (TextView) itemView.findViewById(R.id.tv_forum_name);
        img_forum_ico = (ImageView) itemView.findViewById(R.id.img_forum_ico);
    }
}

public interface OnItemClickLitener {
    void onItemClick(View view, int position);
}

    private ForumAdapter.OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(ForumAdapter.OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

/**
 * 底部加载更多
 */
//class FooterViewHolder extends RecyclerView.ViewHolder {
//    public FooterViewHolder(View itemView) {
//        super(itemView);
//    }
//}
}
