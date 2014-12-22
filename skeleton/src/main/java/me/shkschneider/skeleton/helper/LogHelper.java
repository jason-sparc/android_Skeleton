package me.shkschneider.skeleton.helper;

import android.support.annotation.NonNull;
import android.util.Log;

import me.shkschneider.skeleton.java.StringHelper;

public class LogHelper {

    private static final int VERBOSE = Log.VERBOSE;
    private static final int DEBUG = Log.DEBUG;
    private static final int INFO = Log.INFO;
    private static final int WARN = Log.WARN;
    private static final int ERROR = Log.ERROR;
    private static final int WTF = Log.ASSERT;

    private static String TAG = ApplicationHelper.packageName();
    private static int LEVEL = (ApplicationHelper.debug() ? VERBOSE : WARN);

    private static void log(final int level, final String msg) {
        if (level < LEVEL) {
            return ;
        }
        if (StringHelper.nullOrEmpty(msg)) {
            return ;
        }
        // Uses StackTrace to build the log tag
        final StackTraceElement[] elements = new Throwable().getStackTrace();
        String callerClassName = "?";
        String callerMethodName = "?";
        String callerLineNumber = "?";
        if (elements.length >= 3) {
            callerClassName = elements[2].getClassName();
            callerClassName = callerClassName.substring(callerClassName.lastIndexOf('.') + 1);
            if (callerClassName.indexOf("$") > 0) {
                callerClassName = callerClassName.substring(0, callerClassName.indexOf("$"));
            }
            callerMethodName = elements[2].getMethodName();
            callerMethodName = callerMethodName.substring(callerMethodName.lastIndexOf('_') + 1);
            if (callerMethodName.equals("<init>")) {
                callerMethodName = callerClassName;
            }
            callerLineNumber = String.valueOf(elements[2].getLineNumber());
        }

        final String stack = callerClassName + "." + callerMethodName + "():" + callerLineNumber;
        switch (level) {
            case VERBOSE: Log.v(TAG, "[" + stack + "] " + msg); break ;
            case DEBUG: Log.d(TAG, "[" + stack + "] " + msg); break ;
            case INFO: Log.i(TAG, "[" + stack + "] " + msg); break ;
            case WARN: Log.w(TAG, "[" + stack + "] " + msg); break ;
            case ERROR: Log.e(TAG, "[" + stack + "] " + msg); break ;
            default: break ;
        }
    }

    public static void debug(@NonNull final String msg) {
        LogHelper.log(DEBUG, msg);
    }

    public static void verbose(@NonNull final String msg) {
        LogHelper.log(VERBOSE, msg);
    }

    public static void info(@NonNull final String msg) {
        LogHelper.log(INFO, msg);
    }

    public static void warning(@NonNull final String msg) {
        LogHelper.log(WARN, msg);
    }

    public static void error(@NonNull final String msg) {
        LogHelper.log(ERROR, msg);
    }

    public static void wtf(@NonNull final Throwable throwable) {
        LogHelper.log(WTF, throwable.getClass().getName() + ": " + throwable.getMessage());
        if (ApplicationHelper.debug()) {
            throwable.printStackTrace();
        }
    }

}