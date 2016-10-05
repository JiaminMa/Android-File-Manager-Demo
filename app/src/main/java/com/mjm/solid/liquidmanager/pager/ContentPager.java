package com.mjm.solid.liquidmanager.pager;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.mjm.solid.liquidmanager.CharacterActivity;
import com.mjm.solid.liquidmanager.R;
import com.mjm.solid.liquidmanager.adapter.FolderAdapter;
import com.mjm.solid.liquidmanager.domain.FileInfo;
import com.mjm.solid.liquidmanager.utils.FileInfoUtils;
import com.mjm.solid.liquidmanager.utils.UiUtils;

import java.util.List;

public class ContentPager extends BasePager {


    private RecyclerView mRcRecyclerView;
    private FolderAdapter mAdapter;
    private List<FileInfo> fileInfos;
    public ContentPager(Activity mActivity) {
        super(mActivity);
    }

    public ContentPager(CharacterActivity characterActivity, String mPath, List<FileInfo> fileInfoList) {
        super(characterActivity, mPath, fileInfoList);
    }

    @Override
    protected View initView() {
        View rootView = UiUtils.inflate(R.layout.pager_recyler_view);
        mRcRecyclerView = (RecyclerView) rootView.findViewById(R.id.pager_recylerview);
        return rootView;
    }

    @Override
    public void initData() {
        GetFileListTask task = new GetFileListTask();
        task.execute();
    }

    class GetFileListTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            mFileInfos = FileInfoUtils.getFileInfoFromPath(mPath, mActivity);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.e("bb", "ContentPager: onPostExecute");
            if (mAdapter == null) {
                mAdapter = new FolderAdapter(mActivity, mFileInfos);
                mRcRecyclerView.setAdapter(mAdapter);
                mRcRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
            } else
                mAdapter.notifyDataSetChanged();
        }
    }
}
