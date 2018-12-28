package com.ihoment.base.glide;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.AppGlideModule;
import com.ihoment.base.Constants;

/**
 * Created by xieyingwu on 2017/4/13.
 * 自定义的GlideModule；进行Glide的配置
 */
@GlideModule
public class IAppGlideModule extends AppGlideModule {
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        DiskLruCacheFactory diskLruCacheFactory = new DiskLruCacheFactory(Constants.downloadDirectoryPath, GlideConstants.GLIDE_PHONE_CACHE_SIZE);
        builder.setDiskCache(diskLruCacheFactory);
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
