package com.mjm.solid.solidmanager.domain;

public class FolderSpec {
    public String location;
    public String name;
    public String parentFolder;
    public String date;
    public String type;
    public String size;
    public int folderNum;
    public int fileNum;

    @Override
    public String toString() {
        return "FolderSpec{" +
                "location='" + location + '\'' +
                ", name='" + name + '\'' +
                ", parentFolder='" + parentFolder + '\'' +
                ", date='" + date + '\'' +
                ", type='" + type + '\'' +
                ", size='" + size + '\'' +
                ", folderNum=" + folderNum +
                ", fileNum=" + fileNum +
                '}';
    }
}
