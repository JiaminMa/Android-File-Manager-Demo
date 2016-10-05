package com.mjm.solid.liquidmanager.pager;

import android.app.Activity;
import android.view.View;

import com.mjm.solid.liquidmanager.domain.FileInfo;

import java.util.List;

public abstract class BasePager {

	protected Activity mActivity;
	public View mRootView;
	public String mPath;
	public List<FileInfo> mFileInfos;
	
	public BasePager(Activity mActivity) {
		this.mActivity = mActivity;
		mRootView = initView();
	}

	public BasePager(Activity mActivity, String path, List<FileInfo> fileInfos) {
		this.mActivity = mActivity;
		this.mPath = path;
		this.mFileInfos = fileInfos;
		mRootView = initView();
	}



	protected abstract View initView() ;
	public abstract void initData();
	public void initData(String styleUrl){
		
	}
}
