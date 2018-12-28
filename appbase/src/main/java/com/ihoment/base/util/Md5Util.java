package com.ihoment.base.util;

import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by xieyingwu on 2017/3/23.
 * MD5计算工具
 */

public class Md5Util {

    public static String getMd5Str(byte[] bytes) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] digestValues = digest.digest(bytes);
            String hexString = "";
            for (byte b : digestValues) {
                int temp = b & 255;
                if (temp < 16 && temp >= 0) {
                    hexString = hexString + "0" + Integer.toHexString(temp);
                } else {
                    hexString = hexString + Integer.toHexString(temp);
                }
            }
            return hexString;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获取单个文件的MD5值
     *
     * @param file
     * @return
     */
    public static String getFileMD5(File file) {
        if (!file.exists() || !file.isFile()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");// 生成MD5类的实例
            FileInputStream fs = new FileInputStream(file);
            BufferedInputStream bi = new BufferedInputStream(fs);
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int len;
            // 将文件以字节方式读到数组b中
            while ((len = bi.read(buf)) != -1) {
                md5.update(buf, 0, len);// 执行MD5算法

            }
            for (byte by : md5.digest()) {
                sb.append(String.format("%02x", by));// 将生成的字节MD５值转换成字符串
            }
            bo.close();
            bi.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String getFileMd5(File file, int start, int end) {
        try {
            String fileMD5 = getFileMD5(file);
            if (TextUtils.isEmpty(fileMD5)) return "";
            return fileMD5.substring(start, end);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /***
     * 通过文件路径读取文件的MD5
     *
     * @param filepath
     * @return
     */
    public static String getFileMD5(String filepath) {
        File file = new File(filepath);
        return getFileMD5(file);
    }
}
