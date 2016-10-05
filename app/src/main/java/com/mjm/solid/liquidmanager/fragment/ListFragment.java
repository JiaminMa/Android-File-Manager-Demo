package com.mjm.solid.liquidmanager.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.mjm.solid.liquidmanager.HomeActivity;
import com.mjm.solid.liquidmanager.R;
import com.mjm.solid.liquidmanager.adapter.BaseAdapter;
import com.mjm.solid.liquidmanager.domain.FileInfo;
import com.mjm.solid.liquidmanager.utils.PreUtils;
import com.mjm.solid.liquidmanager.utils.FileInfoUtils;

import java.util.List;

public abstract class ListFragment extends BaseFragment {

    protected RecyclerView mRvRootDir;
    protected BaseAdapter mFolderAdapter;
    protected List<FileInfo> mFileInfoList;

    public static final String LIST_CHANGE_ACTION = "list_change_action";
    public static final String LIST_CHANGE_TYPE = "list_change_type";
    public static final String LIST_CHANG_ISCHECKED = "list_change_ischeced";
    public static final int DISPLAY_HIDE = 0;
    public static final int FOLDER_PRE = 1;
    public static final int MEMORY_LAST_FOLDER = 2;
    public static final int DISPLAY_FOLDER_SIZE = 3;
    public static final int DISPLAY_GROUP = 4;

    private ListChange mListChangeReceiver;

    public ListFragment(String mLabel) {
        super(mLabel);
    }

    public ListFragment() {

    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_recyler_view, null);
        mRvRootDir = (RecyclerView) view.findViewById(R.id.menu_recylerview);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        mFileInfoList = getData();
        mFolderAdapter = newAdapter(mActivity, mFileInfoList);
        mRvRootDir.setAdapter(mFolderAdapter);
        int mode = PreUtils.getInt(mActivity, HomeActivity.PREF_DISPLAY_MODE, HomeActivity.DISPLAY_AS_LIST);
        Log.e("bb", "mode:" + mode);
        switch (mode) {
            case HomeActivity.DISPLAY_AS_LIST:
                mRvRootDir.setLayoutManager(new LinearLayoutManager(mActivity));
                break;
            case HomeActivity.DISPLAY_AS_GRID:
                mRvRootDir.setLayoutManager(new GridLayoutManager(mActivity, 3));
                break;
            default:
                mRvRootDir.setLayoutManager(new LinearLayoutManager(mActivity));
                break;
        }
        mFolderAdapter.setOnItemClickListener(new BaseAdapter.FileOnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(v);
            }

            @Override
            public void onLongClick(View v) {
                onItemLongClick(v);
            }
        });
        mListChangeReceiver = new ListChange();
        IntentFilter filter = new IntentFilter();
        filter.addAction(LIST_CHANGE_ACTION);
        mActivity.registerReceiver(mListChangeReceiver, filter);
    }

    protected abstract BaseAdapter newAdapter(Activity activity, List<FileInfo> fileInfo);

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity.unregisterReceiver(mListChangeReceiver);
    }

    class ListChange extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            int type = intent.getIntExtra(LIST_CHANGE_TYPE, -1);
            boolean isChecked = intent.getBooleanExtra(LIST_CHANG_ISCHECKED, false);
            if (action.equals(LIST_CHANGE_ACTION)) {
                switch (type) {
                    case DISPLAY_HIDE:
                        handleDisplayHide(isChecked);
                        break;
                    case FOLDER_PRE:
                        handleFolderPre(isChecked);
                        break;
                    case MEMORY_LAST_FOLDER:
//                        handlerMemoryLastFolder();
                        break;
                    case DISPLAY_FOLDER_SIZE:
                        handleDisplayFolderSize(isChecked);
                        break;
                    case DISPLAY_GROUP:
//                        handleDisplayGroup();
                        break;
                    default:
                        break;
                }

            }
        }
    }

    protected void handleDisplayHide(boolean isChecked) {
        mFileInfoList.clear();
        mFileInfoList.addAll(getData());
        mFolderAdapter.notifyDataSetChanged();
    }


    private void handleFolderPre(boolean isChecked) {
        FileInfoUtils.sortFileInfoList(mFileInfoList, mActivity);
        mFolderAdapter.notifyDataSetChanged();
    }

    private void handleDisplayFolderSize(boolean isChecked) {
        for (FileInfo info : mFileInfoList) {
            if (info.isDir) {
                info.displayFolderSize = isChecked;
            }
        }
        mFolderAdapter.notifyDataSetChanged();
    }

    protected List<FileInfo> getFileInfo() {
        return mFileInfoList;
    }

    protected abstract void onItemClick(View v);

    protected abstract void onItemLongClick(View v);

    protected abstract List<FileInfo> getData();
}
