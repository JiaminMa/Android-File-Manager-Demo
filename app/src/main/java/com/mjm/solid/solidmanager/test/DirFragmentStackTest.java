package com.mjm.solid.solidmanager.test;

import android.util.Log;

import com.mjm.solid.solidmanager.fragment.DirFragStack;
import com.mjm.solid.solidmanager.fragment.DirFragment;

public class DirFragmentStackTest {

    private static final String TAG = "DirFragmentStackTest";
    public void test() {
        DirFragment f1 = new DirFragment("/");
        DirFragment f2 = new DirFragment("/a");
        DirFragment f3 = new DirFragment("/b");
        DirFragment f4 = new DirFragment("/c");
        DirFragment f5 = new DirFragment("/d");
        DirFragment f6 = new DirFragment("/e");
        DirFragStack stack = new DirFragStack();
        Log.e(TAG, "#####################");
        stack.push(f1);
        stack.printData();
        Log.e(TAG, "#####################");
        stack.push(f2);
        stack.push(f3);
        stack.push(f2);
        stack.push(f1);
        stack.push(f1);
        stack.printData();
        Log.e(TAG, "stack pop()");
        stack.pop();
        stack.printData();
        Log.e(TAG, "stack pop()");
        stack.pop();
        stack.printData();
        Log.e(TAG, "#####################");
        stack.push(f4);
        stack.push(f5);
        stack.push(f6);
        Log.e(TAG, stack.peek().getPath());
        Log.e(TAG, "stack pop()");
        stack.pop();
        stack.printData();
        Log.e(TAG, "stack pop()");
        stack.pop();
        stack.printData();
        Log.e(TAG, "stack pop()");
        stack.pop();
        stack.printData();
        Log.e(TAG, "stack pop()");
        stack.pop();
        stack.printData();
    }
}
