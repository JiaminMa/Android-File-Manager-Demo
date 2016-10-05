package com.mjm.solid.solidmanager.fragment;

import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DirFragStack {

    private List<DirFragment> mFragmentStack;
    private Map<String, DirFragment> mFragmentMap;
    private static final String TAG = "DirFragStack";
    public DirFragStack() {
        mFragmentStack = new LinkedList<DirFragment>();
        mFragmentMap = new HashMap<String, DirFragment>();
    }

    public DirFragment pop() {
        if (mFragmentStack.size() <= 0)
            return null;
        DirFragment fragment = mFragmentStack.remove(mFragmentStack.size() - 1);
        mFragmentMap.remove(fragment.getPath());
        return fragment;
    }

    public DirFragment peek() {
        if (mFragmentStack.size() <= 0)
            return null;
        return mFragmentStack.get(mFragmentStack.size() - 1);
    }

    public void push(DirFragment fragment) {
        if (mFragmentMap.containsKey(fragment.getPath()))
            return;
        mFragmentMap.put(fragment.getPath(), fragment);
        mFragmentStack.add(fragment);
    }

    public boolean isEmpty() {
        return mFragmentStack.size() == 0;
    }

    public boolean hasFragment(String path) {
        return mFragmentMap.containsKey(path);
    }

    public void moveTop(String path) {
        DirFragment topFragment = null;
        for (DirFragment frag : mFragmentStack) {
            if (frag.getPath().equals(path)) {
                topFragment = frag;
                mFragmentStack.remove(frag);
                break;
            }
        }
        if (topFragment != null) {
            mFragmentStack.add(topFragment);
        }
    }

    public void printData() {
        StringBuilder sb = new StringBuilder();
        sb.append("STACK IS##########################\n");
        for (DirFragment fragment : mFragmentStack) {
            sb.append(fragment.getPath()).append("\n");
        }
        Log.v(TAG, sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append("MAP IS ###########################\n");
        Iterator it = mFragmentMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, DirFragment> entry = (Map.Entry<String, DirFragment>) it.next();
            String key = entry.getKey();
            DirFragment fragment = entry.getValue();
            sb2.append("key:" + key + ",value:" + fragment.getPath()).append("\n");
        }
        Log.v(TAG, sb2.toString());
    }

    public void clear() {
        mFragmentStack.clear();
        mFragmentMap.clear();
    }

}
