package com.mjm.solid.solidmanager.engine;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.mjm.solid.solidmanager.domain.VideoInfo;
import java.util.ArrayList;
import java.util.List;

public class VideoProvider extends MediaProviderFactory {
    private Context context;

    public VideoProvider(Context context) {
        this.context = context;
    }

    @Override
    public List<?> getList() {
        List<VideoInfo> list = null;
        if (context != null) {
            Cursor cursor = context.getContentResolver().query(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null,
                    null, null);
            if (cursor != null) {
                list = new ArrayList<VideoInfo>();
                while (cursor.moveToNext()) {
                    VideoInfo info = new VideoInfo();
                    info.id = cursor.getInt(cursor
                            .getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                    info.title = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
                    info.album = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.ALBUM));
                    info.artist = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST));
                    info.displayName = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
                    info.mimeType = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
                    info.path = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                    info.duration = cursor
                            .getInt(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                    info.bucketId = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_ID));
                    info.bucketName = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME));

                    list.add(info);
                }
                cursor.close();
            }
        }
        return list;
    }
}