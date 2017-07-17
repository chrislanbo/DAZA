package com.monkey.framework.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * SD卡操作
 *
 * @return
 */
public class SDCardHelper {
    /**
     * 判断SDCard的加载情况
     *
     * @return true 成功 false 不成功
     */
    public static boolean isSDCardMounted() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    /**
     * 获取SDCard的根路径
     *
     * @return 如果返回null 表示设备加载有问题
     */
    public static File getSDCardAbsolutePath() {
        File file = null;
        if (isSDCardMounted()) {
            file = Environment.getExternalStorageDirectory();
        }
        return file;
    }

    /**
     * StatFs(State File System) 可以查看sdcard的总的空间
     */
    public static long getSDCardTotalMemory() {
        long toolsize = 0;
        if (!isSDCardMounted()) {
            return toolsize;
        }
        // 填写的参数是File路径的getAbsolutePath
        StatFs statFs = new StatFs(getSDCardAbsolutePath().getAbsolutePath());
        // 如何计算内存空间
        // 获取所有块的数量
        long count = statFs.getBlockCount();
        // 每一块对应的内存大小
        int size = statFs.getBlockSize();
        toolsize = count * size / 1024 / 1024;
        return toolsize;
    }

    /**
     * 获取SDCard可用的内存空间
     */
    @SuppressWarnings("deprecation")
    public static long getSDCardAvailableMemory() {
        long toolsize = 0;
        if (!isSDCardMounted()) {
            return toolsize;
        }
        // 填写的参数是File路径的getAbsolutePath
        StatFs statFs = new StatFs(getSDCardAbsolutePath().getAbsolutePath());
        // 如何计算内存空间
        // 获取可用块的数量
        long count = statFs.getAvailableBlocks();
        // 每一块对应的内存大小
        int size = statFs.getBlockSize();
        toolsize = count * size / 1024 / 1024;
        return toolsize;
    }

    /**
     * 保存数据到SDCard
     */
    public static void saveDataToSDCard(String dirPaht, String fileName, byte[] date) {
        File filedir = new File(dirPaht);
        if (!filedir.exists()) {
            filedir.mkdirs();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filedir + File.separator + fileName);
            fos.write(date);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从SDCard读取数据
     */
    public static byte[] readDataFromSDCard(String filePath) {
        FileInputStream fis = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            fis = new FileInputStream(getSDCardAbsolutePath().getAbsolutePath()
                    + File.separator + filePath);
            byte[] b = new byte[1024];
            int a = 0;
            while ((a = fis.read(b)) != -1) {
                baos.write(b, 0, a);
                baos.flush();
            }
            return baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                baos.close();
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取SDCard提供的公共路径 确定类型 可以填写:Environment.DIRECTORY_ALARMS....
     */

    public static File getSDCardPublicPaht(String type) {
        File file = null;
        if (isSDCardMounted()) {
            file = Environment.getExternalStoragePublicDirectory(type);
        }
        return file;
    }

    /**
     * 删除目录文件
     *
     * @param fileName
     */
    public static void delFile(String fileName) {
        File file = new File(fileName);
        if (file.isFile()) {
            file.delete();
        }
        file.exists();
    }
}
