package com.mjm.solid.solidmanager.pager;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mjm.solid.solidmanager.CharacterActivity;
import com.mjm.solid.solidmanager.R;
import com.mjm.solid.solidmanager.domain.FileInfo;
import com.mjm.solid.solidmanager.domain.FolderSpec;
import com.mjm.solid.solidmanager.utils.FileInfoUtils;
import com.mjm.solid.solidmanager.utils.UiUtils;

import java.util.List;

public class InfoPager extends BasePager {

    public InfoPager(Activity mActivity) {
        super(mActivity);
    }

    public InfoPager(CharacterActivity characterActivity, String mPath, List<FileInfo> fileInfoList) {
        super(characterActivity, mPath, fileInfoList);
    }

    private LinearLayout mLlRoot;
    private ProgressBar mProgressBar;
    private FolderSpec mFolderSpec;
    private TextView mTvLoaction;
    private TextView mTvName;
    private TextView mTvParent;
    private TextView mTvDate;
    private TextView mTvType;
    private TextView mTvSize;
    private TextView mTvFolderNum;
    private TextView mTvFileNum;

    @Override
    protected View initView() {
        View rootView = UiUtils.inflate(R.layout.pager_info);
        mLlRoot = (LinearLayout) rootView.findViewById(R.id.pager_info_ll);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.pager_info_pb);
        mTvLoaction = (TextView) rootView.findViewById(R.id.tv_char_location);
        mTvName = (TextView) rootView.findViewById(R.id.tv_char_name);
        mTvParent = (TextView) rootView.findViewById(R.id.tv_char_parent);
        mTvDate = (TextView) rootView.findViewById(R.id.tv_char_date);
        mTvType = (TextView) rootView.findViewById(R.id.tv_char_type);
        mTvSize = (TextView) rootView.findViewById(R.id.tv_char_size);
        mTvFileNum = (TextView) rootView.findViewById(R.id.tv_file_num);
        mTvFolderNum = (TextView) rootView.findViewById(R.id.tv_folder_num);
        return rootView;
    }

    @Override
    public void initData() {
        if (mFolderSpec == null) {
            GetSpecTask task = new GetSpecTask();
            task.execute();
        }
    }

    class GetSpecTask extends AsyncTask<Void, Void, FolderSpec> {


        @Override
        protected FolderSpec doInBackground(Void... params) {
            mFolderSpec = FileInfoUtils.getFolderSpec(mPath);
            return mFolderSpec;
        }

        protected void onPreExecute() {
            mProgressBar.setVisibility(View.VISIBLE);
            mLlRoot.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onPostExecute(FolderSpec s) {
            super.onPostExecute(s);
            mProgressBar.setVisibility(View.INVISIBLE);
            mLlRoot.setVisibility(View.VISIBLE);
            mTvLoaction.setText(s.location);
            mTvName.setText(s.name);
            mTvName.setText(s.parentFolder);
            mTvDate.setText(s.date);
            mTvType.setText(s.type);
            mTvSize.setText(s.size);
            mTvFolderNum.setText(s.folderNum + "");
            mTvFileNum.setText(s.fileNum + "");
        }

    }
}
