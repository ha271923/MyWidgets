package com.hawk.widget.utils;

import com.hawk.widget.Common;

public class SMLog {
    private static final String TAG = "SMLog";

	public static final boolean DEBUG_LOG_ENABLED = true;

	public static void i(String tag, String msg) {
        if (DEBUG_LOG_ENABLED) {
            if (null == msg) {
                msg = "null log string";
            }
            android.util.Log.i(TAG + tag, getClassFunctionName() + msg);
        }
	}

	public static void i(String tag, String msg, Throwable e) {
        if (DEBUG_LOG_ENABLED) {
            if (null == msg) {
                msg = "null log string";
            }
            android.util.Log.i(TAG + tag, getClassFunctionName() + msg, e);
        }
	}

	public static void d(String tag, String msg) {
		if (DEBUG_LOG_ENABLED) {
			if (null == msg)
			{
				msg = "null log string";
			}
			android.util.Log.d(TAG + tag, getClassFunctionName()+msg);
		}
	}

	public static void d(String tag, String msg, Throwable e) {
		if (DEBUG_LOG_ENABLED) {
			if (null == msg)
			{
				msg = "null log string";
			}
			android.util.Log.d(TAG + tag, getClassFunctionName()+msg, e);
		}
	}

	public static void w(String tag, String msg) {
        if (DEBUG_LOG_ENABLED) {
            if (null == msg)
            {
                msg = "null log string";
            }
            android.util.Log.w(TAG + tag, getClassFunctionName()+msg);
        }
	}

	public static void w(String tag, String msg, Throwable e) {
        if (DEBUG_LOG_ENABLED) {
            if (null == msg) {
                msg = "null log string";
            }
            android.util.Log.w(TAG + tag, getClassFunctionName() + msg, e);
        }
	}

	public static void e(String tag, String msg) {
        if (DEBUG_LOG_ENABLED) {
            if (null == msg) {
                msg = "null log string";
            }
            android.util.Log.e(TAG + tag, getClassFunctionName() + msg);
        }
	}

	public static void e(String tag, String msg, Throwable e) {
        if (DEBUG_LOG_ENABLED) {
            if (null == msg) {
                msg = "null log string";
            }
            android.util.Log.e(TAG + tag, getClassFunctionName() + msg, e);
        }
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
