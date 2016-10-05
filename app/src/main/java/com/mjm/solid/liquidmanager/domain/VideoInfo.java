package com.mjm.solid.liquidmanager.domain;

public class VideoInfo extends  FileInfo{
    public int id;
    public String bucketId;
    public String title;
    public String displayName;
    public String mimeType;
    public String bucketName;
    public long actualSize;
    public String album;
    public String artist;
    public long duration;

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
