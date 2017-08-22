package com.tobacco.xinyiyun.knowledge.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.tobacco.xinyiyun.knowledge.BuildConfig;

import java.util.List;
import java.util.Map;


/**
 * SharedPreferences util class
 *
 * @author Administrator
 */
public class SPreferencesUtils {

    private static SharedPreferences mSharedPreferences = null;

    public static void putObject(Context context, String key, Object value) {
        Gson gson = new Gson();
        putString(context, key, gson.toJson(value));
    }

    public static <T> T getObject(Context context, String key, Class<T> classOfT) {
        Gson gson = new Gson();
        return gson.fromJson(getString(context, key, ""), classOfT);
    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences sharedPreferences = getInstance(context);
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(Context context, String key,
                                   String defaultValue) {
        SharedPreferences sharedPreferences = getInstance(context);
        return sharedPreferences.getString(key, defaultValue);
    }

    public static void putBoolean(Context context, String key, Boolean value) {
        SharedPreferences sharedPreferences = getInstance(context);
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static Boolean getBoolean(Context context, String key,
                                     Boolean defaultValue) {
        SharedPreferences sharedPreferences = getInstance(context);
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public static void putLong(Context context, String key, long value) {
        SharedPreferences sharedPreferences = getInstance(context);
        Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static long getLong(Context context, String key, long defaultValue) {
        SharedPreferences sharedPreferences = getInstance(context);
        return sharedPreferences.getLong(key, defaultValue);
    }

    public static int getInt(Context context, String key, int defaultValue) {
        SharedPreferences sharedPreferences = getInstance(context);
        return sharedPreferences.getInt(key, defaultValue);
    }

    public static void putInt(Context context, String key, int value) {
        SharedPreferences sharedPreferences = getInstance(context);
        Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void remove(Context context, String key) {
        SharedPreferences sharedPreferences = getInstance(context);
        sharedPreferences.edit().remove(key).apply();
    }

    public static void clear(Context context, List<String> notClear) {
        SharedPreferences sharedPreferences = getInstance(context);
        Editor mEditor = sharedPreferences.edit();
        if (notClear != null) {
            Map<String, ?> mMap = sharedPreferences.getAll();
            for (String key : mMap.keySet()) {
                if (!notClear.contains(key)) {
                    mEditor.remove(key);
                }
            }
        }
        mEditor.apply();
    }

    public static synchronized SharedPreferences getInstance(
            Context context) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID,
                    Context.MODE_PRIVATE);
        }
        return mSharedPreferences;
    }


    public static String bytesToHexString(byte[] bArray) {
        if (bArray == null) {
            return null;
        }
        if (bArray.length == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }
}
