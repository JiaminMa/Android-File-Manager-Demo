package com.mjm.solid.solidmanager.fragment;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;

import com.mjm.solid.solidmanager.adapter.BaseAdapter;
import com.mjm.solid.solidmanager.adapter.FolderAdapter;
import com.mjm.solid.solidmanager.domain.FileInfo;

import java.util.ArrayList;
import java.util.List;

public class AppFragment extends GridFragment {

    public AppFragment(String mLabel) {
        super(mLabel);
    }

    @Override
    protected List<FileInfo> getData() {
        List<FileInfo> list = new ArrayList<>();
        FileInfo userApp = new FileInfo();
        userApp.name = "用户应用";
        userApp.isDir = true;
        userApp.displayFolderSize = true;
        FileInfo sysApp = new FileInfo();
        sysApp.name = "系统应用";
        sysApp.displayFolderSize = true;
        sysApp.isDir = true;
        list.add(userApp);
        list.add(sysApp);
        return list;
    }

    @Override
    public void initData() {
        super.initData();
        mRvRootDir.setLayoutManager(new LinearLayoutManager(mActivity));
    }

    @Override
    public void refreshData() {

    }

    @Override
    protected BaseAdapter newAdapter(Activity activity, List<FileInfo> infoList) {
        return new FolderAdapter(activity, infoList);
    }
}
