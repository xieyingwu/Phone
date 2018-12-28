package com.ihoment.base.util;

import android.text.TextUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by xieyingwu on 2018/4/20
 * 进行MediaType类型检测
 */
public final class MediaTypeCheck {
    private MediaTypeCheck() {
    }

    private final static String[] mediaPlayerSupportVideoTypes = {
            ".mp4",
            ".3gp",
            ".mov",
    };

    private final static String[] mediaPlayerSupportAudioTypes = {
            ".aac",
            ".mp3",
            ".amr",
            ".ogg",
            ".pcm",
    };

    private final static String[] allSupportAudioTypes = {
            ".aac",
            ".mp3",
            ".m4a",
            ".wav",
            ".amr",
            ".awb",
            ".wma",
            ".ogg",
            ".mid",
            ".xmf",
            ".rtttl",
            ".smf",
            ".imy",
            ".pcm",
    };

    private final static String[] allSupportVideoTypes = {
            ".mp4",
            ".m4v",
            ".3gp",
            ".3gpp",
            ".3g2",
            ".3gpp2",
            ".mkv",
            ".wmv",
            ".mov",
            ".flv",
            ".avi",
            ".mpv2",
            ".mpg",
            ".mpeg",
            ".mpe",
            ".mpa",
            ".movie",
            ".lsx",
            ".lsf",
            ".asx",
            ".asr",
            ".asf",
            ".qt",
            ".rmvb",
            ".ts",
            ".swf",
    };

    private final static String[] allSupportPicsTypes = {
            ".jpg",
            ".jpeg",
//            ".gif",
            ".png",
            ".bmp",
            ".webp",
    };


    private static List<String> mediaPlayerSupportVideoTypeList = Arrays.asList(mediaPlayerSupportVideoTypes);
    private static List<String> mediaPlayerSupportAudioTypeList = Arrays.asList(mediaPlayerSupportAudioTypes);
    private static List<String> allSupportAudioTypeList = Arrays.asList(allSupportAudioTypes);
    private static List<String> allSupportVideoTypeList = Arrays.asList(allSupportVideoTypes);
    private static List<String> allSupportPicsTypeList = Arrays.asList(allSupportPicsTypes);

    public static boolean isMediaPlayerSupport(String suffix) {
        if (TextUtils.isEmpty(suffix)) return false;
        String lowerCase = suffix.toLowerCase();
        return mediaPlayerSupportVideoTypeList.contains(lowerCase) || mediaPlayerSupportAudioTypeList.contains(lowerCase);
    }

    public static String getFilePathSuffixes(String path) {
        if (TextUtils.isEmpty(path)) return null;
        int lastPointPos = path.lastIndexOf(".");
        if (lastPointPos == -1) return null;
        return path.substring(lastPointPos).toLowerCase();
    }

    public static boolean isImageFileType(String path) {
        String suffixes = getFilePathSuffixes(path);
        return !TextUtils.isEmpty(suffixes) && allSupportPicsTypeList.contains(suffixes);
    }

    public static boolean isVideoFileType(String path) {
        String suffixes = getFilePathSuffixes(path);
        return !TextUtils.isEmpty(suffixes) && allSupportVideoTypeList.contains(suffixes);
    }

    public static boolean isAudioFileType(String path) {
        String suffixes = getFilePathSuffixes(path);
        return !TextUtils.isEmpty(suffixes) && allSupportAudioTypeList.contains(suffixes);
    }
}
