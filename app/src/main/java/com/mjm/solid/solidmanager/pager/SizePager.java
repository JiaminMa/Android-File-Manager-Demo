package com.mjm.solid.solidmanager.pager;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.mjm.solid.solidmanager.CharacterActivity;
import com.mjm.solid.solidmanager.R;
import com.mjm.solid.solidmanager.adapter.FolderAdapter;
import com.mjm.solid.solidmanager.domain.FileInfo;
import com.mjm.solid.solidmanager.utils.FileInfoUtils;
import com.mjm.solid.solidmanager.utils.UiUtils;

import java.util.List;

public class SizePager extends BasePager {


    private RecyclerView mRcRecyclerView;
    private FolderAdapter mAdapter;
    private List<FileInfo> fileInfos;
    private ProgressBar mPagerPb;

    public SizePager(Activity mActivity) {
        super(mActivity);
    }

    public SizePager(CharacterActivity characterActivity, String mPath, List<FileInfo> fileInfoList) {
        super(characterActivity, mPath, fileInfoList);
    }

    @Override
    protected View initView() {
        View rootView = UiUtils.inflate(R.layout.pager_recyler_view);
        mRcRecyclerView = (RecyclerView) rootView.findViewById(R.id.pager_recylerview);
        mPagerPb = (ProgressBar) rootView.findViewById(R.id.pager_pb);
        return rootView;
    }

    private boolean isFirst = true;
    @Override
    public void initData() {
        if (isFirst) {
            GetFileListTask task = new GetFileListTask();
            task.execute();
            isFirst = false;
        }
    }

    class GetFileListTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            mFileInfos = FileInfoUtils.getBest20(mActivity, mPath);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mPagerPb.setVisibility(View.VISIBLE);
            mRcRecyclerView.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (mAdapter == null) {
                mAdapter = new FolderAdapter(mActivity, mFileInfos);
                mRcRecyclerView.setAdapter(mAdapter);
                mRcRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
            } else
                mAdapter.notifyDataSetChanged();

            mPagerPb.setVisibility(View.INVISIBLE);
            mRcRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}
