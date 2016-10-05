package com.mjm.solid.solidmanager.fragment;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;

import com.mjm.solid.solidmanager.adapter.BaseAdapter;
import com.mjm.solid.solidmanager.adapter.FolderAdapter;
import com.mjm.solid.solidmanager.domain.FileInfo;

import java.util.ArrayList;
import java.util.List;

public class AudioFragment extends GridFragment {

    public AudioFragment(String mLabel) {
        super(mLabel);
    }

    @Override
    protected List<FileInfo> getData() {
        List<FileInfo> list = new ArrayList<>();
        FileInfo ablum = new FileInfo();
        ablum.name = "专辑";
        ablum.isDir = true;
        ablum.displayFolderSize = true;
        FileInfo all = new FileInfo();
        all.name = "所有曲目";
        all.isDir = true;
        all.displayFolderSize = true;
        FileInfo artist = new FileInfo();
        artist.name = "艺术家";
        artist.isDir = true;
        artist.displayFolderSize = true;
        FileInfo notification = new FileInfo();
        notification.name = "通知";
        notification.isDir = true;
        notification.displayFolderSize = true;
        FileInfo ring = new FileInfo();
        ring.name = "铃声";
        ring.isDir = true;
        ring.displayFolderSize = true;
        FileInfo wakelock = new FileInfo();
        wakelock.name = "闹钟";
        wakelock.displayFolderSize = true;
        wakelock.isDir = true;
        list.add(wakelock);
        list.add(ring);
        list.add(notification);
        list.add(artist);
        list.add(all);
        list.add(ablum);
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
