package com.tobacco.xinyiyun.knowledge.util;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by YangQiang on 2017/8/16.
 */

public class Utils {

    public static String[] trim(String[] array) {
        if (array == null) {
            return new String[0];
        }

        List<String> mList = new ArrayList<>();
        for (String s : array) {
            if (!TextUtils.isEmpty(s)) {
                mList.add(s);
            }
        }

        return mList.toArray(new String[0]);
    }

    /**
     * 删除目录
     *
     * @param dir     目录
     * @param curTime 当前系统时间
     * @return
     */
    public static int deleteFolder(File dir, long curTime) {
        int deletedFiles = 0;
        if (dir != null && dir.isDirectory()) {
            try {
                for (File child : dir.listFiles()) {
                    if (child.isDirectory()) {
                        deletedFiles += deleteFolder(child, curTime);
                    }
                    if (child.lastModified() < curTime) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return deletedFiles;
    }


    /**
     * 清理app缓存
     *
     * @param context
     */
    public static void clearAppCache(Context context) {
        //清除webview缓存
        File file = context.getCacheDir();

        //先删除WebViewCache目录下的文件

        if (file != null && file.exists() && file.isDirectory()) {
            for (File item : file.listFiles()) {
                item.delete();
            }
            file.delete();
        }
        //清除数据缓存
        deleteFolder(context.getFilesDir(), System.currentTimeMillis());
        deleteFolder(context.getCacheDir(), System.currentTimeMillis());
        //2.2版本才有将应用缓存转移到sd卡的功能
        if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
            deleteFolder(context.getExternalCacheDir(), System.currentTimeMillis());
        }
    }

    /**
     * 判断当前版本是否兼容目标版本的方法
     *
     * @param VersionCode
     * @return
     */
    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }

    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

}
