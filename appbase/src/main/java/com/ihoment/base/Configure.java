package com.ihoment.base;

import com.ihoment.base.util.FileUtil;

public class Configure {
    public static String STORAGE_SHARE = "share";
    private static String STORAGE_FOLDER = "TABLETDATA";
    private static String LOG_FOLDER = "TABLETLOG";

    public static String log_folder() {
        String sd_dir = FileUtil.getExternalStorageDir().getAbsolutePath();
        String folder = sd_dir + "/" + LOG_FOLDER + "/";
        FileUtil.createDir(folder);
        return folder;
    }

    public static String storage_folder() {
        String sd_dir = FileUtil.getExternalStorageDir().getAbsolutePath();
        String folder = sd_dir + "/" + STORAGE_FOLDER;
        FileUtil.createDir(folder);
        return folder;
    }
}
