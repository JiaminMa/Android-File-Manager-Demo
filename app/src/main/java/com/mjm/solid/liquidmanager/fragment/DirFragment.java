package com.mjm.solid.liquidmanager.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.mjm.solid.liquidmanager.HomeActivity;
import com.mjm.solid.liquidmanager.R;
import com.mjm.solid.liquidmanager.adapter.BaseAdapter;
import com.mjm.solid.liquidmanager.adapter.FolderAdapter;
import com.mjm.solid.liquidmanager.domain.FileInfo;
import com.mjm.solid.liquidmanager.utils.ApkUtils;
import com.mjm.solid.liquidmanager.utils.FileInfoUtils;
import com.mjm.solid.liquidmanager.utils.FileOperationManager;
import com.mjm.solid.liquidmanager.utils.UiUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DirFragment extends ListFragment {

    private String mCurPath;
    private static final String TAG = "bb";
    public static final String LIST_VIEW_CHANEGE_ACTION = "android.intent.action.dirfragment.listview";
    public static final String LIST_VIEW_MODE = "list_view_mode";

    public DirFragment(String mLabel) {
        super(mLabel);
        this.mCurPath = mLabel;
    }

    public DirFragment() {

    }

    @Override
    protected BaseAdapter newAdapter(Activity activity, List<FileInfo> infoList) {
        return new FolderAdapter(activity, infoList);
    }

    @Override
    protected void onItemClick(View v) {
        TextView pathText = (TextView) v.findViewById(R.id.list_item_tv_cannot_visable);
        String path = pathText.getText().toString();
        File file = new File(path);
        if (file.isDirectory())
            FragmentStackManager.getInstance().switchFragment(path, path);
        else {
            FileInfo data = null;
            for (FileInfo info : getFileInfo()) {
                if (info.path.equals(path)) {
                    data = info;
                    break;
                }
            }
            if (data != null) {
                switch (data.type) {
                    case FileInfo.TYPE_AUDIO:
                        playAudio(data);
                        break;
                    case FileInfo.TYPE_APK:
                        ApkUtils.installAPK(new File(data.path), mActivity);
                        break;
                    case FileInfo.TYPE_DOCUMENT:
                        readDocuments(data);
                        break;
                    case FileInfo.TYPE_VIDEO:
                        playVide(data);
                        break;
                    case FileInfo.TYPE_PIC:
                        picPreview(data);
                    case FileInfo.TYPE_UNKOWN:
                        Toast.makeText(mActivity, "无法打开该文件", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void readDocuments(FileInfo data) {
    }


    private void picPreview(FileInfo data) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + data.path), "image/*");
        mActivity.startActivity(intent);
    }

    private void playVide(FileInfo data) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + data.path), "video/*");
        mActivity.startActivity(intent);
    }

    private void playAudio(FileInfo data) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + data.path), "audio/");
        mActivity.startActivity(intent);
    }

    @Override
    protected void onItemLongClick(View v) {
        // 一个自定义的布局，作为显示的内容

        TextView tvName = (TextView) v.findViewById(R.id.list_item_tv_name);
        String name = tvName.getText().toString();
        final StringBuilder path = new StringBuilder("");
        for (FileInfo info : mFileInfoList) {
            if (info.name.equals(name)) {
                path.append(info.path);
            }

        }
        View contentView = LayoutInflater.from(mActivity).inflate(
                R.layout.popup_copy_delete, null);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int[] location = new int[2];
        v.getLocationInWindow(location);
        int dip = 60;
        int px = UiUtils.dip2px(120);
        ScaleAnimation sa = new ScaleAnimation(0.3f, 1.0f , 0.3f, 1.0f, Animation.RELATIVE_TO_SELF,
                0, Animation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(100);
        AlphaAnimation aa = new AlphaAnimation(0.5f, 1.0f);
        aa.setDuration(100);
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(aa);
        set.addAnimation(sa);
        popupWindow.showAtLocation(v, Gravity.LEFT | Gravity.TOP,
                px, location[1]);
        contentView.startAnimation(set);

        LinearLayout llCopy = (LinearLayout) contentView.findViewById(R.id.ll_copy);
        LinearLayout llDel = (LinearLayout) contentView.findViewById(R.id.ll_delete);
        LinearLayout llMove = (LinearLayout) contentView.findViewById(R.id.ll_cut);

        llCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileOperationManager.getInstance().copy(path.toString());
                refreshView(popupWindow);
            }
        });

        llDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileOperationManager.getInstance().delete(path.toString());
                refreshView(popupWindow);
            }
        });

        llMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileOperationManager.getInstance().move(path.toString());
                refreshView(popupWindow);
            }
        });
    }

    private void displayAccpetOrCancel() {
        Intent intent = new Intent(HomeActivity.COPY_CHANGE_ACTION);
        mActivity.sendBroadcast(intent);
    }

    private void refreshView(PopupWindow popupWindow) {
        displayAccpetOrCancel();
        refreshData();
        popupWindow.dismiss();
    }

    @Override
    protected List<FileInfo> getData() {
        List<FileInfo> list = new ArrayList();
        if (mCurPath != null)
            list = FileInfoUtils.getFileInfoFromPath(mCurPath, mActivity);
        return list;
    }

    public String getPath() {
        return mCurPath;
    }

    public void refreshData() {
        mFileInfoList.clear();
        mFileInfoList.addAll(FileInfoUtils.getFileInfoFromPath(mCurPath, mActivity));
        mFolderAdapter.notifyDataSetChanged();
    }

    class ListViewChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            int mode = intent.getIntExtra(LIST_VIEW_MODE, 0);
            if (action.equals(LIST_VIEW_CHANEGE_ACTION)) {
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
            }
        }
    }

    private ListViewChangeReceiver mListViewChangeReceiver;

    @Override
    public void initData() {
        super.initData();
        mListViewChangeReceiver = new ListViewChangeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(LIST_VIEW_CHANEGE_ACTION);
        mActivity.registerReceiver(mListViewChangeReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity.unregisterReceiver(mListViewChangeReceiver);
    }
}
