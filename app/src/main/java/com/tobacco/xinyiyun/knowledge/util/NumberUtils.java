package com.tobacco.xinyiyun.knowledge.util;

import java.math.BigDecimal;

public class NumberUtils {

    public static int toInt(String str,
                            int defValue) {
        try {
            return Integer.valueOf(str);
        } catch (Exception e) {
        }
        return defValue;
    }


    public static int toInt(String str) {
        return toInt(str, 0);
    }

    public static int[] toInts(String[] strs,
                               int defValue) {
        int[] mInt = new int[strs.length];
        for (int i = 0; i < mInt.length; i++) {
            mInt[i] = toInt(strs[i].trim(), defValue);
        }
        return mInt;
    }

    public static float toFloat(String str,
                                float defVal) {
        try {
            return Float.parseFloat(str);
        } catch (Exception e) {
        }
        return defVal;
    }


    public static float toFloat(String str) {
        return toFloat(str, 0f);
    }


    public static float[] toFloats(String[] floats,
                                   int defVal) {
        float[] mFloats = new float[floats.length];
        for (int i = 0; i < mFloats.length; i++) {
            mFloats[i] = toFloat(floats[i], defVal);
        }
        return mFloats;
    }

    public static long toLong(String str,
                              long defVal) {
        try {
            return Long.valueOf(str);
        } catch (Exception e) {
            return defVal;
        }
    }

    public static long toLong(String str) {
        return toLong(str, 0l);
    }

    public static double toDouble(String str,
                                  double defVal) {
        try {
            return Double.valueOf(str);
        } catch (Exception e) {
            return defVal;
        }
    }

    public static double toDouble(String str) {
        return toDouble(str, 0d);
    }

    public static double[] toDoubles(String[] doubles,
                                     int defVal) {
        double[] mDouble = new double[doubles.length];
        for (int i = 0; i < mDouble.length; i++) {
            mDouble[i] = toDouble(doubles[i], defVal);
        }
        return mDouble;
    }

    public static String getFormatSize(double size) {

        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return "";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }


}
