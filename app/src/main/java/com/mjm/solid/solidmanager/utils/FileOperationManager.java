package com.mjm.solid.solidmanager.utils;

public class FileOperationManager {

    private static FileOperationManager sManager;

    private int mStatus;

    private static final int STATUS_COPY = 0;
    private static final int STATUS_DEL = 1;
    private static final int STATUS_MOVE = 2;
    private static final int STATUS_CONFIRM = 3;
    private static final int STATUS_CANCEL = 4;

    private String mDst;
    private String mSrc;

    private boolean isDir;

    private FileOperationManager() {

    }

    public synchronized static FileOperationManager getInstance() {
        if (sManager == null) {
            sManager = new FileOperationManager();
        }
        return sManager;
    }

    public void clear() {
        sManager = null;
    }

    public void copy(String src) {

        this.mSrc = src;
        this.mStatus = STATUS_COPY;
    }

    public void move(String src) {
        this.mSrc = src;
        this.mStatus = STATUS_MOVE;
    }

    public void delete(String src) {
        this.mSrc = src;
        this.mStatus = STATUS_DEL;
    }

    public void confirm(String dst) {
        this.mDst = dst;
        switch (mStatus) {
            case STATUS_COPY:
                ShellUtils.execCommand("cp " + this.mSrc + " " + this.mDst, false);
                break;
            case STATUS_MOVE:
                ShellUtils.execCommand("mv " + this.mSrc + " " + this.mDst, false);
                break;
            case STATUS_DEL:
                ShellUtils.execCommand("rm -rf " + this.mSrc, false);
                break;
            default:
                break;
        }
        this.mStatus = STATUS_CONFIRM;
    }

    public void cancel() {
        this.mStatus = STATUS_CANCEL;
    }

}
