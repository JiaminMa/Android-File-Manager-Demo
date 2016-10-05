package com.mjm.solid.liquidmanager.fragment;

import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FragmentStack {

    private List<BaseFragment> mFragmentStack;
    private Map<String, BaseFragment> mFragmentMap;
    private static final String TAG = "FragmentStack";

    public FragmentStack() {
        mFragmentStack = new LinkedList<BaseFragment>();
        mFragmentMap = new HashMap<String, BaseFragment>();
    }

    public BaseFragment pop() {
        if (mFragmentStack.size() <= 0)
            return null;
        BaseFragment fragment = mFragmentStack.remove(mFragmentStack.size() - 1);
        mFragmentMap.remove(fragment.getLabel());
        return fragment;
    }

    public BaseFragment peek() {
        if (mFragmentStack.size() <= 0)
            return null;
        return mFragmentStack.get(mFragmentStack.size() - 1);
    }

    public void push(BaseFragment fragment) {
        if (mFragmentMap.containsKey(fragment.getLabel()))
            return;
        mFragmentMap.put(fragment.getLabel(), fragment);
        mFragmentStack.add(fragment);
    }

    public boolean isEmpty() {
        return mFragmentStack.size() == 0;
    }

    public boolean hasFragment(String label) {
        return mFragmentMap.containsKey(label);
    }

    public void moveTop(String label) {
        BaseFragment topFragment = null;
        for (BaseFragment frag : mFragmentStack) {
            if (frag.getLabel().equals(label)) {
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
        for (BaseFragment fragment : mFragmentStack) {
            sb.append(fragment.getLabel()).append("\n");
        }
        Log.v(TAG, sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append("MAP IS ###########################\n");
        Iterator it = mFragmentMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, BaseFragment> entry = (Map.Entry<String, BaseFragment>) it.next();
            String key = entry.getKey();
            BaseFragment fragment = entry.getValue();
            sb2.append("key:" + key + ",value:" + fragment.getLabel()).append("\n");
        }
//        Log.v(TAG, sb2.toString());
    }

    public void clear() {
        mFragmentStack.clear();
        mFragmentMap.clear();
    }

}
