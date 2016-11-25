package com.lh16808.app.lhds.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lh16808.app.lhds.R;
import com.lh16808.app.lhds.model.CateDetailModel;
import com.lh16808.app.lhds.utils.ImageLoader;
import com.lh16808.app.lhds.widget.imagelook.ui.ImagePagerActivity;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryDetailFragment extends Fragment {


    private CateDetailModel mCateDetailModel;
    private int arg0;
    private FragmentActivity mActivity;
    @SuppressLint("ValidFragment")
    public CategoryDetailFragment(int arg0, CateDetailModel list) {
        super();
        this.arg0 = arg0;
        mCateDetailModel = list;
    }
    @SuppressLint("ValidFragment")
    public CategoryDetailFragment(){
        super();
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_category_detail, container, false);
            mActivity = getActivity();
            ImageView iv = (ImageView) view.findViewById(R.id.image_detail);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity, ImagePagerActivity.class);
                    intent.putExtra("type", 0);
                    intent.putExtra("des", mCateDetailModel.getTitle() + mCateDetailModel.getDesTitle());
                    ArrayList<String> urls = new ArrayList<>();
                    urls.add(mCateDetailModel.getUrl());
                    intent.putStringArrayListExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
                    startActivity(intent);
                }
            });
            ImageLoader.LoaderNet(mActivity, mCateDetailModel.getUrl(), iv);
        }
        return view;
    }
}
