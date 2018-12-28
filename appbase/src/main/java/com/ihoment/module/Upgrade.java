package com.ihoment.module;

import java.io.Serializable;

/**
 * Created by pengxianglin on 2017/3/27.
 */
public class Upgrade implements Serializable {
    public int id;
    public String type;
    public String version_hard; //apkVersion
    public String version_soft; //apkVerCode
    public String file_url;
    public String file_digest;
    public long pkg_size;
    public String pkg_name;
    public String pkg_log;
    public String ip_min;
    public String ip_max;
    public String add_time;
    public long last_update;
    public boolean forced;
    public boolean need_download;

    public String increment_url;

}
