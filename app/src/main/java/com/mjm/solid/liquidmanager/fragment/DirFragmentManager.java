package com.mjm.solid.liquidmanager.fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import com.mjm.solid.liquidmanager.R;

public class DirFragmentManager {

    private static DirFragmentManager sManager;
    private DirFragStack mDirFragStack;
    private FragmentManager mFragmentManager;

    private TextView mTvPath;

    private DirFragmentManager(FragmentManager fm) {
        mDirFragStack = new DirFragStack();
        this.mFragmentManager = fm;
    }

    public void setTextPath(TextView textPath) {
        this.mTvPath = textPath;
    }

    private DirFragmentManager() {
        mDirFragStack = new DirFragStack();
    }

    public synchronized static DirFragmentManager getInstance(FragmentManager fm) {
        if (sManager == null) {
            sManager = new DirFragmentManager(fm);
        }
        return sManager;
    }

    public synchronized static DirFragmentManager getInstance() {
        if (sManager == null) {
            sManager = new DirFragmentManager();
        }
        return sManager;
    }

    public void switchFragment(String path, String flag) {
        DirFragment fragment = null;
        //如果栈中不存在fragment，则新建一个并且push
        if (!mDirFragStack.hasFragment(path)) {
            fragment = new DirFragment(path);
            mDirFragStack.push(fragment);
        } else {
            mDirFragStack.moveTop(path);
            fragment = mDirFragStack.peek();
        }
        mDirFragStack.printData();
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.setCustomAnimations(R.anim.side_in, 0);
        ft.replace(R.id.fl_home, fragment, flag);
        ft.commit();
        mTvPath.setText(path);
    }

    public void clear() {
        mDirFragStack.clear();
        sManager = null;
    }

    public String getPeek() {
        return mDirFragStack.peek().getPath();
    }

    public void removeTop() {
        mDirFragStack.pop();
        DirFragment fragment = mDirFragStack.peek();
        switchFragment(fragment.getPath(), fragment.getPath());
    }

}
