package com.mjm.solid.solidmanager.pager;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mjm.solid.solidmanager.CharacterActivity;
import com.mjm.solid.solidmanager.R;
import com.mjm.solid.solidmanager.adapter.FolderAdapter;
import com.mjm.solid.solidmanager.domain.FileInfo;
import com.mjm.solid.solidmanager.utils.FileInfoUtils;
import com.mjm.solid.solidmanager.utils.UiUtils;

import java.util.List;

public class KindPager extends BasePager {

    private RecyclerView mRcRecyclerView;
    private FolderAdapter mAdapter;
    private List<FileInfo> fileInfos;
    public KindPager(Activity mActivity) {
        super(mActivity);
    }

    public KindPager(CharacterActivity characterActivity, String mPath, List<FileInfo> fileInfoList) {
        super(characterActivity, mPath, fileInfoList);
    }

    @Override
    protected View initView() {
        View rootView = UiUtils.inflate(R.layout.fragment_recyler_view);
        mRcRecyclerView = (RecyclerView) rootView.findViewById(R.id.menu_recylerview);
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
            if (mAdapter == null) {
                mAdapter = new FolderAdapter(mActivity, mFileInfos);
                mRcRecyclerView.setAdapter(mAdapter);
            } else
                mAdapter.notifyDataSetChanged();
        }
    }
}
