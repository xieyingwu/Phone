package com.ihoment.base;

import android.os.Environment;

import com.ihoment.base.glide.GlideConstants;

/**
 * Created by pengxianglin on 2017/3/23.
 */

public class Constants {
    public static final long APK_FILE_DOWNLOAD_TIMEOUT_SECS = 10 * 60;
    public static final int MAIN_SERVICE_DELAY_SECS = 5;//服务延时启动
    public static final int MAIN_SERVICE_SCHEDULE_SECS = 1;//服务定时器
    public static int NETWORK_CALL_TIMEOUT_SECS = 20; //网络请求超时
    public static String CURRENT_RUN_MODE = "CURRENT_RUN_MODE";
    public static String APK_FILE_NAME = "target.apk";
    public static String APK_FILE_DIR = "apk";
    public static final int MAIN_SERVICE_MEDIAFILE_CHECK_SCHEDULES = 1 * 60;
    public static final int MAIN_SERVICE_MEDIAPROESS_CHECK_SCHEDULES = 1 * 60;
    public static final int MAIN_SERVICE_DOWNLOAD_CHECK_SCHEDULES = 3;


    public static final int MAIN_SERVICE_UPGRADE_CHECK_SCHEDULES = 5 *60;//  5分钟查询一次


    public static final int MAIN_SERVICE_TOP_ACTIVITY_SCHEDULES = 2;
    public static final int MAIN_SERVICE_LOAD_CHECK_SCHEDULES = 10 * 60;
    public static final int MAIN_SERVICE_BABY_MEDIA_CHECK_SCHEDULES = 10 * 60;


    public static final int MAIN_WAKEUP_CHECK = 20;

    public static String downloadDirectoryPath = Environment.getExternalStorageDirectory()
            + "/" + GlideConstants.GLIDE_PAD_CACHE_FOLDER_NAME;
}
