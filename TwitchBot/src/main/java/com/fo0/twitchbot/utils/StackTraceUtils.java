package com.fo0.twitchbot.utils;

import lombok.extern.log4j.Log4j2;

/**
 * @created 11.10.2020 - 00:10:09
 * @author fo0 (GH:fo0)
 * @version 0.1
 */
@Log4j2
public class StackTraceUtils {

    private static final String UNKNOWN = "UnknownClass.Method";

    /**
     * argument 0 is the current method name
     *
     * @param callsBack
     *            must be positive
     * @return
     */
    public static String methodCaller(int callsBack) {
        if (callsBack < 0) {
            throw new IllegalArgumentException("only positive values are allowed");
        }

        int modifycall = callsBack + 2;

        try {
            StackTraceElement[] stack = Thread.currentThread().getStackTrace();
            return stack[modifycall].getClassName() + "." + stack[modifycall].getMethodName();
        } catch (Exception e) {
            log.error("failed to call stacktrace", e);
        }

        return UNKNOWN;
    }

    /**
     * argument 0 is the current method name
     *
     * @param callsBack
     *            must be positive
     * @return
     */
    public static String methodSimpleNameCaller(int callsBack) {
        if (callsBack < 0) {
            throw new IllegalArgumentException("only positive values are allowed");
        }

        int modifycall = callsBack + 2;

        try {
            StackTraceElement[] stack = Thread.currentThread().getStackTrace();
            return stack[modifycall].getMethodName();
        } catch (Exception e) {
            log.error("failed to call stacktrace", e);
        }

        return UNKNOWN;
    }

}