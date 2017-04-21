package com.hawk.widget.utils;

public class SMLog {

    public static final boolean SHOW_CALLSTACK = false;

    private static final String TAG = "[Hawk]";

    public static void i() {
        if (SHOW_CALLSTACK)
            android.util.Log.i(TAG, getClassFunctionName());
        else
            android.util.Log.i(TAG, "");
    }

    public static void i(String msg) {
        if (SHOW_CALLSTACK)
            android.util.Log.i(TAG, getClassFunctionName()+msg);
        else
            android.util.Log.i(TAG, msg);
    }

    public static void i(String tag, String msg) {
        if (SHOW_CALLSTACK)
            android.util.Log.i(TAG + tag, getClassFunctionName()+msg);
        else
            android.util.Log.i(TAG + tag, msg);
    }

    public static void i(String tag, String msg, Throwable e) {
        if (SHOW_CALLSTACK)
            android.util.Log.i(TAG + tag, getClassFunctionName()+msg, e);
        else
            android.util.Log.i(TAG + tag, msg, e);
    }

    public static void v() {
        if (SHOW_CALLSTACK)
            android.util.Log.v(TAG, getClassFunctionName());
        else
            android.util.Log.v(TAG, "");
    }

    public static void v(String msg) {
        if (SHOW_CALLSTACK)
            android.util.Log.v(TAG, getClassFunctionName()+msg);
        else
            android.util.Log.v(TAG, msg);
    }

    public static void v(String tag, String msg) {
        if (SHOW_CALLSTACK)
            android.util.Log.i(TAG + tag, getClassFunctionName()+msg);
        else
            android.util.Log.i(TAG + tag, msg);
    }

    public static void v(String tag, String msg, Throwable e) {
        if (SHOW_CALLSTACK)
            android.util.Log.i(TAG + tag, getClassFunctionName()+msg, e);
        else
            android.util.Log.i(TAG + tag, msg, e);
    }

    public static void d() {
        if (SHOW_CALLSTACK)
            android.util.Log.d(TAG, getClassFunctionName());
        else
            android.util.Log.d(TAG, "");
    }

    public static void d(String msg) {
        if (SHOW_CALLSTACK)
            android.util.Log.d(TAG, getClassFunctionName()+msg);
        else
            android.util.Log.d(TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (SHOW_CALLSTACK)
            android.util.Log.d(TAG + tag, getClassFunctionName()+msg);
        else
            android.util.Log.d(TAG + tag, msg);
    }

    public static void d(String tag, String msg, Throwable e) {
        if (SHOW_CALLSTACK)
            android.util.Log.d(TAG + tag, getClassFunctionName()+msg, e);
        else
            android.util.Log.d(TAG + tag, msg, e);
    }

    public static void w() {
        if (SHOW_CALLSTACK)
            android.util.Log.w(TAG, getClassFunctionName());
        else
            android.util.Log.w(TAG, "");
    }

    public static void w(String msg) {
        if (SHOW_CALLSTACK)
            android.util.Log.w(TAG, getClassFunctionName()+msg);
        else
            android.util.Log.w(TAG, msg);
    }

    public static void w(String tag, String msg) {
        if (SHOW_CALLSTACK)
            android.util.Log.w(TAG + tag, getClassFunctionName()+msg);
        else
            android.util.Log.w(TAG + tag, msg);
    }

    public static void w(String tag, String msg, Throwable e) {
        if (SHOW_CALLSTACK)
            android.util.Log.w(TAG + tag, getClassFunctionName()+msg, e);
        else
            android.util.Log.w(TAG + tag, msg, e);
    }

    public static void e() {
        if (SHOW_CALLSTACK)
            android.util.Log.e(TAG, getClassFunctionName());
        else
            android.util.Log.e(TAG, "");
    }

    public static void e(String msg) {
        if (SHOW_CALLSTACK)
            android.util.Log.e(TAG, getClassFunctionName()+msg);
        else
            android.util.Log.e(TAG, msg);
    }

    public static void e(String tag, String msg) {
        if (SHOW_CALLSTACK)
            android.util.Log.e(TAG + tag, getClassFunctionName()+msg);
        else
            android.util.Log.e(TAG + tag, msg);
    }

    public static void e(String tag, String msg, Throwable e) {
        if (SHOW_CALLSTACK)
            android.util.Log.e(TAG + tag, getClassFunctionName()+msg, e);
        else
            android.util.Log.e(TAG + tag, msg, e);
    }

    public static String getClassFunctionName(  )
    {
        String Class_Function_Name = "class ";
        Class_Function_Name += Thread.currentThread().getStackTrace()[4].getClassName().toString();
        Class_Function_Name += " :: ";
        Class_Function_Name += Thread.currentThread().getStackTrace()[4].getMethodName().toString();
        Class_Function_Name += "() ";
        return Class_Function_Name;

    }
}