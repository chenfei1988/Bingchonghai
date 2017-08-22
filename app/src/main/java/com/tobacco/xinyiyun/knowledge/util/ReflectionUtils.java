package com.tobacco.xinyiyun.knowledge.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@SuppressWarnings("unchecked")
public class ReflectionUtils {

    @SuppressWarnings("rawtypes")
    public static Object invokeStaticMethod(String className, String methodName, Class[] paramTypes,
                                            Object[] paramValues) {

        return invokeMethod(null, className, methodName, paramTypes, paramValues);

    }

    @SuppressWarnings("rawtypes")
    public static Object invokeMethod(Object target,
                                      String className,
                                      String methodName,
                                      Class[] paramTypes,
                                      Object[] paramValues) {

        try {
            Class clazz = Class.forName(className);
            Method method = clazz.getDeclaredMethod(methodName, paramTypes);
            method.setAccessible(true);
            return method.invoke(target, paramValues);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }

    @SuppressWarnings("rawtypes")
    public static Object getFieldObject(Object target, Class clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(target);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            // try supper for Miui, Miui has a class named MiuiPhoneWindow
            try {
                Field field = clazz.getSuperclass().getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(target);
            } catch (Exception superE) {
                e.printStackTrace();
                superE.printStackTrace();
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;

    }

    @SuppressWarnings("rawtypes")
    public static Object getFieldObject(Object target, String className, String fieldName) {
        Class clazz = null;
        try {
            clazz = Class.forName(className);
            return getFieldObject(target, clazz, fieldName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static Object getStaticFieldObject(String className, String fieldName) {

        return getFieldObject(null, className, fieldName);
    }

    @SuppressWarnings("rawtypes")
    public static void setFieldObject(Object target,
                                      String className,
                                      String fieldName,
                                      Object fieldValue) {
        Class clazz = null;
        try {
            clazz = Class.forName(className);
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, fieldValue);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            // try supper for Miui, Miui has a class named MiuiPhoneWindow
            try {
                Field field = clazz.getSuperclass().getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(target, fieldValue);
            } catch (Exception superE) {
                e.printStackTrace();
                superE.printStackTrace();
            }
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        } catch (ClassNotFoundException e) {
        }
    }

    public static void setStaticOjbect(String className, String fieldName, Object fieldValue) {
        setFieldObject(null, className, fieldName, fieldValue);
    }


    public static Class getSuperClassGenricType(final Class clazz, final int index) {

        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }

    public static <T> T getSuperClassGenricType(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        } catch (ClassCastException e) {
        }
        return null;
    }
}