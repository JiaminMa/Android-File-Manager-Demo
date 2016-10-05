package com.mjm.solid.solidmanager.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class PreUtils {
	public static final String PREF_NAME = "config";
	
	public static boolean getBoolean(Context context, String key, boolean defaultVal){
		SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		return sp.getBoolean(key, defaultVal);
	}
	
	public static void setBoolean(Context context, String key, boolean value){
		SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		sp.edit().putBoolean(key, value).commit();
	}
	
	public static String getString(Context context, String key, String defaultVal){
		SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		return sp.getString(key, defaultVal);
	}
	
	public static void setString(Context context, String key, String value){
		SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		sp.edit().putString(key, value).commit();
	}

	public static int getInt(Context context, String key, int defaultVal){
		SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		return sp.getInt(key, defaultVal);
	}

	public static void setInt(Context context, String key, int value){
		SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		sp.edit().putInt(key, value).commit();
	}
}
