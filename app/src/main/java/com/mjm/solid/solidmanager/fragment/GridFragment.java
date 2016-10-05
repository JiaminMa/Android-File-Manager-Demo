package com.mjm.solid.solidmanager.fragment;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.mjm.solid.solidmanager.adapter.BaseAdapter;
import com.mjm.solid.solidmanager.adapter.GridAdapter;
import com.mjm.solid.solidmanager.domain.FileInfo;

import java.util.List;

public abstract class GridFragment extends ListFragment {

    public static final String PHOTO_FRAGMENT_LABEL = "照片";
    public static final String VIDEO_FRAGMENT_LABEL = "视频";
    public static final String AUDIO_FRAGMENT_LABEL = "音乐";
    public static final String APP_FRAGMENT_LABEL = "应用";

    public GridFragment(String mLabel) {
        super(mLabel);
    }

    public GridFragment() {
        super();

    }

    @Override
    protected void onItemClick(View v) {

    }

    @Override
    protected void onItemLongClick(View v) {

    }

    @Override
    public void initData() {
        super.initData();
        mRvRootDir.setLayoutManager(new GridLayoutManager(mActivity, 2));
    }

    @Override
    protected BaseAdapter newAdapter(Activity activity, List<FileInfo> fileInfo) {
        return new GridAdapter(activity, fileInfo);
    }

    @Override
    protected abstract List<FileInfo> getData();
}
