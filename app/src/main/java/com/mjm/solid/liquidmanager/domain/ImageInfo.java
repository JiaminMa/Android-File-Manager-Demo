package com.mjm.solid.liquidmanager.domain;

public class ImageInfo extends  FileInfo{
    public int id;
    public String bucketId;
    public String title;
    public String displayName;
    public String mimeType;
    public String bucketName;
    public long actualSize;

    @Override
    public String toString() {
        return "ImageInfo{" +
                "path=" + path +
                "id=" + id +
                ", bucketId='" + bucketId + '\'' +
                ", title='" + title + '\'' +
                ", displayName='" + displayName + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", bucketName='" + bucketName + '\'' +
                ", actualSize=" + actualSize +
                '}';
    }
}
