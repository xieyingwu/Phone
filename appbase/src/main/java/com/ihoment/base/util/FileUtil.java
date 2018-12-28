package com.ihoment.base.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RawRes;
import android.text.TextUtils;

import com.ihoment.base.infra.LogInfra;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import okhttp3.ResponseBody;

public class FileUtil {
    private static final String TAG = FileUtil.class.getSimpleName();

    public static boolean hasExternalStorage() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static File getExternalStorageDir() {
        if (!hasExternalStorage()) {
            return null;
        }
        return Environment.getExternalStorageDirectory();
    }

    public static File getExternalDir(String path) {
        File external = getExternalStorageDir();
        if (external == null) {
            return null;
        }
        File dir = new File(external, path);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                dir = null;
            }
        }
        return dir;
    }

    public static boolean isFileExist(String fullPath) {
        if (fullPath == null) return false;
        File file = new File(fullPath);
        return file != null && file.exists();
    }

    public static boolean createDir(String path, String subpath) {
        File root = getExternalDir(path);
        if (root == null) return false;
        File dir = new File(root, subpath);
        if (!dir.exists()) {
            return dir.mkdirs();
        }
        return true;
    }

    public static boolean createDir(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            return dir.mkdirs();
        }
        return true;
    }

    public static File getExternalFile(String path, String filename) {
        File dir = getExternalDir(path);
        if (dir == null) return null;
        File file = new File(dir.getAbsolutePath(), filename);
        if (file.exists()) {
            return file;
        }

        return null;
    }

    public static boolean deleteFile(File file) {
        if (file != null && file.exists()) {
            return file.delete();
        }
        return false;
    }

    public static File[] listFile(String path) {
        File dir = new File(path);
        if (!dir.exists()) return null;
        return dir.listFiles();
    }

    public static File cpFile(File src, String path) {
        try {
            FileInputStream inStream = new FileInputStream(src);
            FileOutputStream outStream = new FileOutputStream(path);
            FileChannel inChannel = inStream.getChannel();
            FileChannel outChannel = outStream.getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
            inStream.close();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        File dst = new File(path);
        if (!dst.exists()) return null;
        return dst;
    }

    public static void findAllFile(String path, ArrayList<File> files, FileFilter filter) {
        if (null == path || null == files) {
            return;
        }

        File file = new File(path);

        if (null == file || !file.exists()) {
            return;
        }

        if (file.isDirectory()) {
            File[] list = file.listFiles();

            for (int i = 0; i < list.length; i++) {
                if (list[i].isDirectory()) {
                    findAllFile(list[i].getAbsolutePath(), files, filter);
                } else {
                    if ((null == filter) || (null != filter && filter.accept(list[i]))) {
                        files.add(list[i]);
                    }

                }
            }
        }

        if ((null == filter) || (null != filter && filter.accept(file))) {
            files.add(file);
        }

    }

    /**
     * 删除目录
     *
     * @param filePath
     * @return
     */
    public static boolean deleteDirectory(String filePath) {

        if (null == filePath) {
            return false;
        }

        File file = new File(filePath);

        if (file == null || !file.exists()) {
            return false;
        }

        if (file.isDirectory()) {
            File[] list = file.listFiles();

            for (int i = 0; i < list.length; i++) {
                if (list[i].isDirectory()) {
                    deleteDirectory(list[i].getAbsolutePath());
                } else {
                    list[i].delete();
                }
            }
        }

        file.delete();
        return true;
    }

    public static boolean copySdcardFile(String fromFile, String toFile) {
        InputStream fosfrom = null;
        OutputStream fosto = null;
        try {
            LogInfra.Log.d(TAG, "copySdcardFile s");
            fosfrom = new FileInputStream(fromFile);
            fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            return true;
        } catch (Exception ex) {
            LogInfra.Log.d(TAG, "copySdcardFile e");
            return false;
        } finally {
            try {
                if (fosfrom != null) {
                    fosfrom.close();
                }
                if (fosto != null) {
                    fosto.close();
                }
                LogInfra.Log.d(TAG, "copySdcardFile c");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean empotyDirectory(String filePath) {
        if (null == filePath) {
            return false;
        }

        File file = new File(filePath);

        if (file == null || !file.exists()) {
            return false;
        }

        if (file.isDirectory()) {
            File[] list = file.listFiles();

            for (int i = 0; i < list.length; i++) {
                if (list[i].isDirectory()) {
                    deleteDirectory(list[i].getAbsolutePath());
                } else {
                    list[i].delete();
                }
            }
        }
        return true;
    }


    public static boolean deleteDirectory(File file) {
        if (file == null || !file.exists()) {
            return false;
        }

        if (file.isDirectory()) {
            File[] list = file.listFiles();

            for (int i = 0; i < list.length; i++) {
                if (list[i].isDirectory()) {
                    deleteDirectory(list[i].getAbsolutePath());
                } else {
                    list[i].delete();
                }
            }
        }

        file.delete();
        return true;
    }

    /**
     * 重命名文件
     *
     * @param oldPath
     * @param newPath
     */
    public static void renameFile(String oldPath, String newPath) {
        if (TextUtils.isEmpty(oldPath) || TextUtils.isEmpty(newPath)) {
            return;
        }

        File file = new File(oldPath);

        if (file.exists()) {
            file.renameTo(new File(newPath));
        } else {
        }
    }

    public static void createFile(String path) {
        try {
            File file = new File(path);
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFile(final String path, final String text, boolean append) {
        try {
            File file = new File(path);
            FileOutputStream fileOut2 = new FileOutputStream(file, append);
            PrintStream out2 = new PrintStream(fileOut2);
            out2.println(text);
            out2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存网络文件到本地
     */
    public static boolean writeResponseBodyToDisk(ResponseBody body, String saveDir, String saveFileName) {
        try {
            File futureStudioIconFile = new File(saveDir, saveFileName);
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    LogInfra.Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }
                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null)
                    inputStream.close();
                if (outputStream != null)
                    outputStream.close();
            }
        } catch (IOException e) {
            return false;
        }
    }

    @NonNull
    public static final void copyFileFromRawToOthers(@NonNull final Context context, @RawRes int id, @NonNull final String targetPath) {
        InputStream in = context.getResources().openRawResource(id);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(targetPath);
            byte[] buff = new byte[1024];
            int read = 0;
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 计算系统磁盘剩余可用空间
     */
    public static long getAvailableInternalMemorySize() {
        // TODO: 2017/9/23 拷贝文件放置目录需要后期定义
        File path = FileUtil.getExternalDir("CopyDir");
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        return availableBlocks * blockSize;
    }

    public static boolean saveImg2File(File file, Bitmap bitmap) throws IOException {
        if (bitmap == null) return false;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            return bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static File generateVideoThumbnail(@NonNull final String videoFilePath, @NonNull final File thumbnailSaveDir) throws IOException {
        long millis = System.currentTimeMillis();
        String fileName = millis + ".temp";
        File file = new File(thumbnailSaveDir, fileName);
        //采用原生的缩略图生成方式生成缩略图
        Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(videoFilePath, MediaStore.Images.Thumbnails.MINI_KIND);
        if (thumbnail == null) {
            return null;
        }
        boolean saveImg2File = saveImg2File(file, thumbnail);
        if (saveImg2File) {
            String newName = millis + ".jpg";
            File newFile = new File(thumbnailSaveDir, newName);
            if (file.renameTo(newFile))
                return newFile;
        }
        return null;
    }

    /*复制Assets中指定目录下的所有文件*/
    public static void copyAssets(@NonNull final Context context, String assetDir, String dir) {
        String[] files;
        try {
            // 获得Assets一共有几多文件
            files = context.getResources().getAssets().list(assetDir);
        } catch (IOException e1) {
            return;
        }
        File mWorkingPath = new File(dir);
        // 如果文件路径不存在
        if (!mWorkingPath.exists()) {
            // 创建文件夹
            if (!mWorkingPath.mkdirs()) {
                // 文件夹创建不成功时调用
            }
        }
        for (int i = 0; i < files.length; i++) {
            try {
                // 获得每个文件的名字
                String fileName = files[i];
                // 根据路径判断是文件夹还是文件
                if (!fileName.contains(".")) {
                    if (0 == assetDir.length()) {
                        copyAssets(context, fileName, dir + "/" + fileName);
                    } else {
                        copyAssets(context, assetDir + "/" + fileName, dir + "/" + fileName);
                    }
                    continue;
                }
                File outFile = new File(mWorkingPath, fileName);
                if (outFile.exists())
                    outFile.delete();
                InputStream in = null;
                if (0 != assetDir.length())
                    in = context.getAssets().open(assetDir + "/" + fileName);
                else
                    in = context.getAssets().open(fileName);
                OutputStream out = new FileOutputStream(outFile);
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
