package com.mjm.solid.solidmanager.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.mjm.solid.solidmanager.R;
import com.mjm.solid.solidmanager.adapter.BaseAdapter;
import com.mjm.solid.solidmanager.adapter.GridAdapter;
import com.mjm.solid.solidmanager.domain.FileInfo;
import com.mjm.solid.solidmanager.utils.FileInfoUtils;

import java.util.ArrayList;
import java.util.List;

public class PhotoVideoPreviewFragment extends GridFragment {


    public PhotoVideoPreviewFragment(String mLabel) {
        super(mLabel);
    }

    public PhotoVideoPreviewFragment() {
        super();

    }

    @Override
    protected void onItemClick(View v) {
        TextView pathText = (TextView) v.findViewById(R.id.grid_item_path);
        String path = pathText.getText().toString();
        int type = FileInfoUtils.getType(path);
        Intent intent;
        switch(type) {
            case FileInfo.TYPE_PIC:
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse("file://" + path), "image/*");
                mActivity.startActivity(intent);
                break;
            case FileInfo.TYPE_VIDEO:
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse("file://" +path), "video/*");
                mActivity.startActivity(intent);
                break;
            default:
                break;
        }

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
    public void refreshData() {

    }

    @Override
    protected BaseAdapter newAdapter(Activity activity, List<FileInfo> fileInfo) {
        return new GridAdapter(activity, fileInfo);
    }

    @Override
    protected List<FileInfo> getData() {
        List<FileInfo> list = new ArrayList<>();
        if (mLabel != null)
            list = FileInfoUtils.getFileInfoFromPath(mLabel, mActivity);

        for (int i = 0; i < list.size(); i++) {
            FileInfo info = list.get(i);
            if (info.type != FileInfo.TYPE_PIC && info.type != FileInfo.TYPE_VIDEO) {
                list.remove(i);
                i--;
            }
        }
        return list;
    }
}
