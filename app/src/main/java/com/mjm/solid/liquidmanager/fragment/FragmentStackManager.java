package com.mjm.solid.liquidmanager.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import com.mjm.solid.liquidmanager.HomeActivity;
import com.mjm.solid.liquidmanager.R;

public class FragmentStackManager {

    private static FragmentStackManager sManager;
    private FragmentStack mFragmentStack;
    private android.support.v4.app.FragmentManager mFragmentManager;
    private Activity mActivity;
    private TextView mTvPath;
    private FragViewChangeReceiver mFragViewChangeReceiver;

    private FragmentStackManager(android.support.v4.app.FragmentManager fm, Activity activity) {
        mFragmentStack = new FragmentStack();
        this.mFragmentManager = fm;
        this.mActivity = activity;
        registerReceiver();
    }

    private FragmentStackManager(android.support.v4.app.FragmentManager fm) {
        mFragmentStack = new FragmentStack();
        this.mFragmentManager = fm;
    }

    public void setTextPath(TextView textPath) {
        this.mTvPath = textPath;
    }

    private FragmentStackManager() {
        mFragmentStack = new FragmentStack();
    }

    public synchronized static FragmentStackManager getInstance(android.support.v4.app.FragmentManager fm, Activity activity) {
        if (sManager == null) {
            sManager = new FragmentStackManager(fm, activity);
        }
        return sManager;
    }


    public synchronized static FragmentStackManager getInstance(android.support.v4.app.FragmentManager fm) {
        if (sManager == null) {
            sManager = new FragmentStackManager(fm);
        }
        return sManager;
    }

    public synchronized static FragmentStackManager getInstance() {
        if (sManager == null) {
            sManager = new FragmentStackManager();
        }
        return sManager;
    }

    public void switchGridFragment(String label, String flag) {

        BaseFragment fragment = null;
        //如果栈中不存在fragment，则新建一个并且push
        if (!mFragmentStack.hasFragment(label)) {
            fragment = new PhotoVideoPreviewFragment(label);
            mFragmentStack.push(fragment);
        } else {
            mFragmentStack.moveTop(label);
            fragment = mFragmentStack.peek();
        }
        mFragmentStack.printData();
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.setCustomAnimations(R.anim.side_in, 0);
        ft.replace(R.id.fl_home, fragment, flag);
        ft.commit();
        mTvPath.setText(label);
    }


    public void switchFragment(String label, String flag) {
        BaseFragment fragment = null;
        //如果栈中不存在fragment，则新建一个并且push
        if (!mFragmentStack.hasFragment(label)) {
            if (label.equals(GridFragment.PHOTO_FRAGMENT_LABEL)) {
                fragment = new PhotoFragment(label);
            } else if (label.equals(GridFragment.VIDEO_FRAGMENT_LABEL)) {
                fragment = new VideoFragment(label);
            } else if (label.equals(GridFragment.AUDIO_FRAGMENT_LABEL)) {
                fragment = new AudioFragment(label);
            } else if (label.equals(GridFragment.APP_FRAGMENT_LABEL)) {
                fragment = new AppFragment(label);
            } else {
                fragment = new DirFragment(label);
            }
            mFragmentStack.push(fragment);
        } else {
            mFragmentStack.moveTop(label);
            fragment = mFragmentStack.peek();
        }
        mFragmentStack.printData();
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.setCustomAnimations(R.anim.side_in, 0);
        ft.replace(R.id.fl_home, fragment, flag);
        ft.commit();
        mTvPath.setText(label);
    }

    public void clear() {
        mFragmentStack.clear();
        sManager = null;
        mActivity.unregisterReceiver(mFragViewChangeReceiver);
    }

    public String getPeek() {
        return mFragmentStack.peek().getLabel();
    }

    public BaseFragment getPeekFragment() {
        return mFragmentStack.peek();
    }

    public void removeTop() {
        mFragmentStack.pop();
        BaseFragment fragment = mFragmentStack.peek();
        switchFragment(fragment.getLabel(), fragment.getLabel());
    }

    class FragViewChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(HomeActivity.COPY_CONFIRM_ACTION)) {
                mFragmentStack.peek().refreshData();
            }
        }
    }

    public void setmActivity(Activity activity) {
        this.mActivity = activity;
    }

    private void registerReceiver() {
        mFragViewChangeReceiver = new FragViewChangeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(HomeActivity.COPY_CONFIRM_ACTION);
        mActivity.registerReceiver(mFragViewChangeReceiver, filter);
    }
}
