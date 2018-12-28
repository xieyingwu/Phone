package com.ihoment.module;

import android.text.TextUtils;

import com.ihoment.base.util.StrUtil;

import java.io.Serializable;

/**
 * Created by pengxianglin on 2017/4/10.
 * photo item
 */

public class Photo implements Serializable {
    public int id;
    public String name;
    public String url;
    public String uuid;
    public String suffix;
    public String ptype;//image video
    public int height;
    public int width;
    public int scaleHeight;
    public int scaleWidth;
    public boolean favorite;
    public int angle;/*新属性；角度*/

    /*操作属性*/
    public boolean isPlaceHolder;

    public String showUrl;

    public String showThumbnailUrl;

    public boolean isSourceSuc;
    public boolean isVideoSuc;
    public boolean isVideoThumbnailSuc;

    private String videoUrl;
    private String videoCoverUrl;
    private String photoUrl;

    public boolean isLocal;
    public String localFileUrl;

    public boolean isNew;
    public long firstPlayTime = 0;

    public Photo(boolean isPlaceHolder) {
        this.isPlaceHolder = isPlaceHolder;
    }

    public String getPicName() {
        return uuid + "." + suffix;
    }

    public Photo() {
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getVideoCoverUrl() {
        return videoCoverUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void makeUrl(int sH, int sw) {
        if (!isVideo() && TextUtils.isEmpty(photoUrl)) {
            photoUrl = StrUtil.generateUrl(url, sH, sw);
        }
        if (isVideo()) {
            if (TextUtils.isEmpty(videoUrl))
                videoUrl = StrUtil.generateVideoUrl(url);
            if (TextUtils.isEmpty(videoCoverUrl))
                videoCoverUrl = StrUtil.generateVideoThumbnail(url, sH, sw);
        }
    }

    public Photo(int id, String name, String url, String uuid, String suffix, String ptype, int height, int width, int scaleHeight, int scaleWidth, boolean favorite) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.uuid = uuid;
        this.suffix = suffix;
        this.ptype = ptype;
        this.height = height;
        this.width = width;
        this.scaleHeight = scaleHeight;
        this.scaleWidth = scaleWidth;
        this.favorite = favorite;
    }

    public boolean isGif() {
        return !isVideo() && "gif".equalsIgnoreCase(suffix);
    }

    public boolean isVideo() {
        return "video".equals(ptype);
    }

    public boolean isInvalid() {
        return !isVideo() && (height <= 0 || width <= 0);
    }

    public boolean isInvalidVideo() {
        return isVideo() && !StrUtil.isHadMediaSuffix(url);
    }

    public void setScaleWH(int scaleHeight) {
        if (isVideo()) return;
        this.scaleHeight = scaleHeight;
        this.scaleWidth = (int) (1.0f * scaleHeight * width / height);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void updateFirstShowTime() {
        if (isNew && firstPlayTime == 0) {
            firstPlayTime = System.currentTimeMillis();
        }
    }
}
