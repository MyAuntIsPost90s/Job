package com.bs.job.gateway.base;

import lingshi.valid.StringValid;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BaseConstant {

    public static final String OPER_SUCCESS_CN = "操作成功";
    public static final String OPER_FAILURE_CN = "操作失败";

    public static final String UPLOADER_BASE_PATH = "/Uploader";

    public static final String UPLOADER_TEMP_PATH = "/Uploader/temp";

    public static final String UPLOADER_Edit_PATH = "/Uploader/edit";

    public static String getExt(String name) {
        if (StringValid.isNullOrWhiteSpace(name)) {
            return "";
        }
        String ext = name.substring(name.lastIndexOf(".") + 1);
        return ext;
    }

    public static String getBaseUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath();
    }

    /**
     * 判断文件是否为图片
     *
     * @param fileName
     * @return
     */
    public static boolean isImage(String fileName) {
        String ext = fileName.substring(fileName.lastIndexOf('.') + 1);
        switch (ext) {
            case "jpge":
                return true;
            case "jpg":
                return true;
            case "png":
                return true;
            case "gif":
                return true;
            default:
                return false;
        }
    }

    /**
     * 合并文件
     *
     * @param
     * @return
     **/
    public static void mergeFile(String source, String target) throws Exception {
        File[] files = new File(source).listFiles();
        if (files == null || files.length < 1) {
            return;
        }
        // 对取到的文件根据下标从小到大排序
        List<File> list = Arrays.asList(files);
        Collections.sort(list, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.getName() == null || o2.getName() == null) {
                    return -1;
                }
                if (o1.getName().length() > o2.getName().length()) {
                    return 1;
                }
                if (o1.getName().length() < o2.getName().length()) {
                    return -1;
                }
                if (o1.getName().compareTo(o2.getName()) > 0) {
                    return 1;
                }
                if (o1.getName().compareTo(o2.getName()) < 0) {
                    return -1;
                }
                if (o1.getName().compareTo(o2.getName()) == 0) {
                    return 0;
                }
                return 0;
            }
        });

        try (OutputStream outputStream = new FileOutputStream(target)) {
            // 依次写入
            for (int i = 0; i < list.size(); i++) {
                File file = list.get(i);
                try (InputStream inputStream = new FileInputStream(file)) {
                    byte[] buffer = new byte[1024 * 1024 * 1]; // 缓冲区文件大小为1M
                    int len = 0;
                    while ((len = inputStream.read(buffer, 0, 1024 * 1024 * 1)) != -1) {
                        outputStream.write(buffer, 0, len);
                    }
                }
            }
        }
    }

    /**
     * 删除文件夹
     *
     * @param
     * @return
     **/
    public static void deleteDir(String path) {
        if (StringValid.isNullOrEmpty(path)) {
            return;
        }
        File file = new File(path);
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                // 递归删除目录中的子目录下
                for (int i = 0; i < children.length; i++) {
                    deleteDir(children[i].getPath());
                }
            }
            file.delete();
        }
    }
}
