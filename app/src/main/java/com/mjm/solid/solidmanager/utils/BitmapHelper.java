package com.mjm.solid.solidmanager.utils;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.MediaMetadataRetriever;

import com.lidroid.xutils.BitmapUtils;

import java.util.HashMap;
import java.util.Map;

@SuppressLint("NewApi")
public class BitmapHelper {
	private BitmapHelper() {
	}

	private static BitmapUtils bitmapUtils;

	public static BitmapUtils getBitmapUtils() {
		if (bitmapUtils == null) {
			bitmapUtils = new BitmapUtils(UiUtils.getContext());
		}
		return bitmapUtils;
	}

	private static Map<String, Bitmap> mVideoPicMap = new HashMap<String, Bitmap>();

	public static Bitmap getVideoThumbnail(String filePath) {
		Bitmap videoBitmap = null;
		videoBitmap = mVideoPicMap.get(filePath);
		if(videoBitmap == null){
			MediaMetadataRetriever retriever = new MediaMetadataRetriever();
			try {
				retriever.setDataSource(filePath);
				videoBitmap = retriever.getFrameAtTime();
				mVideoPicMap.put(filePath, videoBitmap);
			} 
			catch(IllegalArgumentException e) {
				e.printStackTrace();
			} 
			catch (RuntimeException e) {
				e.printStackTrace();
			} 
			finally {
				try {
					retriever.release();
				} 
				catch (RuntimeException e) {
					e.printStackTrace();
				}
			}
		}
		return videoBitmap;
	}

	public static Map<String, Bitmap> mAudioPicMap = new HashMap<String, Bitmap>();

	public static Bitmap createAlbumArt(final String filePath, int height, int width) {
		Bitmap bitmap = null;
		bitmap = mAudioPicMap.get(filePath);
		if(bitmap == null){
			MediaMetadataRetriever retriever = new MediaMetadataRetriever();
			try {
				retriever.setDataSource(filePath);
				byte[] embedPic = retriever.getEmbeddedPicture();
				Options opt = new Options();
				opt.inJustDecodeBounds = true;
				BitmapFactory.decodeByteArray(embedPic, 0, embedPic.length, opt);
				int scale = getScale(opt, height, width);
				opt.inSampleSize = scale;
				opt.inJustDecodeBounds = false;
				bitmap = BitmapFactory.decodeByteArray(embedPic, 0, embedPic.length, opt);
				mAudioPicMap.put(filePath, bitmap);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					retriever.release();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return bitmap;
	}

	public static Map<String, Bitmap> mPhotoMap;

	public static Bitmap getSmallBitmapFromFile(String path, int height, int width){

		if(mPhotoMap == null){
			mPhotoMap = new HashMap<String, Bitmap>();
		}
		Bitmap bm = mPhotoMap.get(path);
		if(bm == null){
			Options opt = new Options();
			opt.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(path, opt);
			int scale = getScale(opt, height, width);
			opt.inSampleSize = scale;
			opt.inJustDecodeBounds = false;
			bm = BitmapFactory.decodeFile(path, opt);
			mPhotoMap.put(path, bm);
		}
		return bm;
	}

	private static int getScale(Options opt, int height, int width) {
		int imageWidth = opt.outWidth;
		int imageHeight = opt.outHeight;

		int defaultWidth = height;
		int defatultHeight = width;

		int scale = 1;
		int scaleWidth = imageWidth / defaultWidth;
		int scaleHeight = imageHeight / defatultHeight;
		if(scaleWidth >= scaleHeight && scaleWidth >= 1){
			scale = scaleWidth;
		}
		else if(scaleWidth < scaleHeight && scaleHeight >= 1){
			scale = scaleHeight;
		}
		return scale;
	}

}
