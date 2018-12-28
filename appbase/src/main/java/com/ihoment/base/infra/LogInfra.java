package com.ihoment.base.infra;

import com.ihoment.base.util.JsonUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * 会自动保持一天最大日志为MAX_LOG_SIZE_M*2
 * Created by wuwenlong on 3/12/16.
 */
public class LogInfra {
    private static String TAG;
    private static String DIR;
    private static int LEVEL;
    private static final int MAX_LOG_SIZE_M = 4;
//    private static ExecutorService service = Executors.newSingleThreadExecutor();

    public static String dir() {
        return DIR;
    }


    /***
     * 传入App级别TAG，存储目录，级别高于level才存入文件
     * @param tag
     * @param dir
     * @param level
     */
    public static void init(String tag, String dir, int level) {
        TAG = tag;
        LEVEL = level;
        DIR = dir;
    }

    /***
     * V和D只进行android输出
     * 其他会依据设置的LEVEL进行输出和存储文件
     */
    public static class Log {
        private static boolean log(int level) {
            return LEVEL > level;
        }

        private static Calendar calendar = Calendar.getInstance();

        private static String tag(String tag, String msg) {
            calendar.setTime(new Date());
            String txt = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
            return "[" + txt + " " + tag + "]" + msg;
        }

        public static void v(String tag, String msg) {
            if (log(android.util.Log.VERBOSE)) return;
            android.util.Log.v(TAG + " " + tag, msg);
        }

        public static void v(String tag, String msg, Throwable tr) {
            if (log(android.util.Log.VERBOSE)) return;
            android.util.Log.v(TAG + " " + tag, msg, tr);
        }

        public static void d(String tag, String msg) {
            if (log(android.util.Log.DEBUG)) return;
            android.util.Log.d(TAG + " " + tag, msg);
        }

        public static void d(String tag, String msg, Throwable tr) {
            if (log(android.util.Log.DEBUG)) return;
            android.util.Log.d(TAG + " " + tag, msg, tr);
        }

        public static void i(String tag, String msg) {
            if (log(android.util.Log.INFO)) return;
            android.util.Log.i(TAG + " " + tag, msg);
        }

        public static void i(String tag, Object msg) {
            if (log(android.util.Log.INFO)) return;
            android.util.Log.i(TAG + " " + tag, msg.getClass().getSimpleName() + ":" + JsonUtil.toJson(msg));
        }

        public static void i(String tag, String msg, Throwable tr) {
            if (log(android.util.Log.INFO)) return;
            android.util.Log.i(TAG + " " + tag, msg, tr);
        }

        public static void w(String tag, String msg) {
            if (log(android.util.Log.WARN)) return;
            android.util.Log.w(TAG + " " + tag, msg);
        }

        public static void w(String tag, Throwable tr) {
            if (log(android.util.Log.WARN)) return;
            android.util.Log.w(TAG + " " + tag, tr);
        }

        public static void w(String tag, String msg, Throwable tr) {
            if (log(android.util.Log.WARN)) return;
            android.util.Log.w(TAG + " " + tag, msg, tr);
        }

        public static void e(final String tag, final String msg) {
            if (log(android.util.Log.ERROR)) return;
            android.util.Log.e(TAG + " " + tag, msg);
//            service.submit(new Runnable() {
//                @Override
//                public void run() {
//                    XLog.e(tag(tag, msg));
//                }
//            });
        }


        public static void e(final String tag, final Throwable tr) {
            if (log(android.util.Log.ERROR)) return;
            android.util.Log.e(TAG + " " + tag, "", tr);
//            service.submit(new Runnable() {
//                @Override
//                public void run() {
//                    XLog.e(tag, tr);
//                }
//            });
        }

        public static void e(final String tag, final String msg, final Throwable tr) {
            if (log(android.util.Log.ERROR)) return;
            android.util.Log.e(TAG + " " + tag, msg, tr);
//            service.submit(new Runnable() {
//                @Override
//                public void run() {
//                    XLog.e(tag(tag, msg), tr);
//                }
//            });
        }

    }
}
