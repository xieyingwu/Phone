package com.ihoment.base.util;

import android.text.TextUtils;

import com.ihoment.module.Account;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xieyingwu on 2017/3/27.
 * 字符操作工具类
 */

public class StrUtil {
    private static final String TAG = StrUtil.class.getName();

    public static String getValue(String rawValue, String key, String linkStr, String endStr) {
        int indexOfKey = rawValue.indexOf(key);
        if (indexOfKey == -1) return null;
        int indexOfEnd = rawValue.indexOf(endStr, indexOfKey);
        if (indexOfEnd == -1) return null;
        return rawValue.substring(indexOfKey + key.length() + linkStr.length(), indexOfEnd).trim();
    }

    public static String jointStr(List<String> values, String jointStr) {
        String result = "";
        if (values == null || values.isEmpty()) return result;
        for (String value : values) {
            if (result.isEmpty()) {
                result = value;
            } else {
                result = result + jointStr + value;
            }
        }
        return result;
    }

    public static boolean checkEmail(String email) {
        if (TextUtils.isEmpty(email)) return false;
        String pattern = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static String generateUrl(String url, int height, int width) {
        if (TextUtils.isEmpty(url)) return "";
        Account account = Account.read();
        if (account == null) return "";
        try {
            String sign = URLEncoder.encode(account.sign, "UTF-8");
            String size = width + "*" + height;
            url = url.replaceAll("\\{sign\\}", sign)
                    .replaceAll("\\{size\\}", size);
        } catch (UnsupportedEncodingException e) {
            url = "";
        }
        return url;
    }

    public static String generateVideoUrl(String url) {
        return generateUrl(url, 0, 0);
    }

    public static boolean isSuffix(String url, String suffix) {
        return !TextUtils.isEmpty(url) && !TextUtils.isEmpty(suffix) && url.endsWith(suffix);
    }

    public static boolean isHadMediaSuffix(String url) {
        if (TextUtils.isEmpty(url)) return false;
        int lastIndexOf = url.lastIndexOf(".");
        if (lastIndexOf == -1) return false;
        String sub = url.substring(lastIndexOf);
        return sub.length() > 2;
    }

    public static String generateVideoThumbnail(String url, int height, int width) {
        String generateUrl = generateUrl(url, height, width);
        generateUrl = generateUrl.replace("kind=photo", "kind=thumbnail");
        if (TextUtils.isEmpty(generateUrl)) return generateUrl;
        int lastPointPos = generateUrl.lastIndexOf(".");
        if (lastPointPos == -1) return "";
        String sub = generateUrl.substring(0, lastPointPos);
        return sub + ".jpg";
    }

    public static String getSuffix(String url) {
        if (TextUtils.isEmpty(url)) return null;
        int lastIndexOf = url.lastIndexOf(".");
        if (lastIndexOf == -1) return null;
        return url.substring(lastIndexOf);
    }

    public static String changeCapitalize(String resource, LetterType type) {
        if (TextUtils.isEmpty(resource) || type == null) return "";
        switch (type) {
            case Lower:
                return resource.toLowerCase();
            case Upper:
                return resource.toUpperCase();
            case FirstUpper:
                if (resource.length() == 1) {
                    return resource.toUpperCase();
                } else {
                    String sub = resource.substring(1, resource.length());
                    return String.valueOf(resource.charAt(0)).toUpperCase() + sub.toLowerCase();
                }
        }
        return "";
    }

    public enum LetterType {
        Lower,
        Upper,
        FirstUpper
    }

    public static String getNumStr(long num) {
        if (num <= 0) return "00";
        if (num >= 10) {
            return String.valueOf(num);
        }
        return "0" + num;
    }
}
