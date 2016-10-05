package com.mjm.solid.solidmanager.domain;


public class FileInfo {

    public String name;
    public String path;
    public String permission;
    public String size;
    public int type;
    public String date;
    public long length;
    public boolean displayFolderSize;
    public boolean isHidden;
    public boolean isDir;
    public int fileCount;
    public static final int TYPE_UNKOWN = 0;
    public static final int TYPE_DOCUMENT = 1;
    public static final int TYPE_AUDIO = 2;
    public static final int TYPE_VIDEO = 3;
    public static final int TYPE_APK = 4;
    public static final int TYPE_PIC = 5;


    public FileInfo() {
        this.permission = "";
        this.displayFolderSize = false;
        this.isHidden = false;
    }

    public FileInfo(String name, String path, String permission, String size, int type, String date,
                    boolean displayFolderSize, boolean isHidden, boolean isDir, int fileCount) {
        this.name = name;
        this.path = path;
        this.permission = permission;
        this.size = size;
        this.type = type;
        this.date = date;
        this.displayFolderSize = displayFolderSize;
        this.isHidden = isHidden;
        this.isDir = isDir;
        this.fileCount = fileCount;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", permission='" + permission + '\'' +
                ", size='" + size + '\'' +
                ", type=" + type +
                ", date='" + date + '\'' +
                ", displayFolderSize=" + displayFolderSize +
                ", isHidden=" + isHidden +
                ", isDir=" + isDir +
                ", fileCount=" + fileCount +
                '}';
    }
}
