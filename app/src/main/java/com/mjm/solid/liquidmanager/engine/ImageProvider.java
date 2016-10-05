package com.mjm.solid.liquidmanager.engine;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.mjm.solid.liquidmanager.domain.ImageInfo;

import java.util.ArrayList;
import java.util.List;

public class ImageProvider extends MediaProviderFactory<ImageInfo> {
    private static final String TAG = "aaa";
    private Context context;


    public ImageProvider(Context context) {
        this.context = context;
    }

    @Override
    public List<ImageInfo> getList() {
        List<ImageInfo> list = null;
        if (context != null) {
            Cursor cursor = context.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null,
                    null, null);
            if (cursor != null) {
                list = new ArrayList<ImageInfo>();
                while (cursor.moveToNext()) {
                    ImageInfo image = new ImageInfo();
                    image.id = cursor
                            .getInt(cursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media._ID));
                    image.title = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media.TITLE));
                    image.path = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                    image.displayName = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
                    image.mimeType = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE));
                    image.bucketName = cursor.getString(cursor.getColumnIndexOrThrow(
                            MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                    image.bucketId = cursor.getString(cursor.getColumnIndexOrThrow(
                            MediaStore.Images.Media.BUCKET_ID));
                    image.actualSize = cursor
                            .getLong(cursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media.SIZE));
                    list.add(image);
                }
                cursor.close();
            }
        }
        return list;
    }
}