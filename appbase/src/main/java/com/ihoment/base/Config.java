package com.ihoment.base;


import android.content.Context;

import com.github.fernandodev.androidproperties.lib.AssetsProperties;
import com.github.fernandodev.androidproperties.lib.Property;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sky.wang on 4/29/16.
 */
public class Config extends AssetsProperties {
    @Property
    public String run_mode;
    @Property
    boolean crash_report;
    @Property
    public boolean diable_signout;
    @Property
    public String aws_congnito_id;
    @Property
    public String aws_s3_region;
    @Property
    public String aws_s3_bucket;
    @Property
    public int ga_dispatch_secs;
    @Property
    public String statistics_prefix;
    @Property
    public static String dashtop_app_name;

    @Property
    public String IOT_ENDPOINT;
    @Property
    public String IOT_COGNITO_POOL_ID;
    @Property
    public String IOT_REGION;
    @Property
    public String IOT_KEYSTORE_NAME;
    @Property
    public String IOT_KEYSTORE_PASSWORD;
    @Property
    public String IOT_CERTIFICATE_ID;

    private Map<RUNMODE, RunConfig> configs;

    public Config(Context context) {
        super(context);
        configs = new HashMap<>();
        for (RUNMODE mode : RUNMODE.values()) {
            configs.put(mode, new RunConfig(context, "config_" + mode.name()));
        }
    }

    public String domain_api() {
        return configs.get(RUNMODE.current).domain_api;
    }

    public String photoupload_api() {
        return configs.get(RUNMODE.current).photoupload_api;
    }

    public int log_level() {
        return configs.get(RUNMODE.current).log_level;
    }

    public boolean low_mode_enable() {
        return configs.get(RUNMODE.current).low_mode_enable;
    }

    public String web_socket() {
        return configs.get(RUNMODE.current).web_socket;
    }

    public class RunConfig extends AssetsProperties {
        @Property
        String domain_api;
        @Property
        String photoupload_api;
        @Property
        int log_level;
        @Property
        boolean low_mode_enable;
        @Property
        String web_socket;

        public RunConfig(Context context, String propertiesFileName) {
            super(context, propertiesFileName);
        }
    }
}