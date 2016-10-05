package com.mjm.solid.solidmanager.utils;

import android.content.Context;

import com.mjm.solid.solidmanager.HomeActivity;
import com.mjm.solid.solidmanager.domain.FileInfo;
import com.mjm.solid.solidmanager.domain.FolderSpec;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileInfoUtils {

    public static List<FileInfo> getFileInfoFromPath(String path, Context context) {
        List<FileInfo> ret = new ArrayList<FileInfo>();
        File rootDir = new File(path);
        File[] files = rootDir.listFiles();
        if (files == null)
            return ret;
        for (File file : files) {
            FileInfo fileInfo = new FileInfo();
            fileInfo.name = file.getName();
            fileInfo.path = file.getPath();
            fileInfo.isDir = file.isDirectory();
            fileInfo.length = file.length();
            //判断是否为显示文件夹大小
            if (!file.isDirectory()) {
                fileInfo.type = getType(file.getName());
                fileInfo.size = getSize(file.length());
            } else {
                fileInfo.displayFolderSize = PreUtils.getBoolean(context,
                        HomeActivity.PREF_DISPLAY_FOLDER_SIZE, false);
                File[] subFiles = file.listFiles();
                if (subFiles == null) {
                    fileInfo.displayFolderSize = false;
                } else {
                    fileInfo.fileCount = subFiles.length;
                    fileInfo.size = getSize(file.length());
                }
            }

            //判断这个文件的隐藏性
            boolean displayHidden = PreUtils.getBoolean(context, HomeActivity.PREF_DISPLAY_HIDDEN, false);
            if (!displayHidden && file.isHidden()) {
                continue;
            }

            if (file.canRead()) {
                fileInfo.permission += "r";
            } else {
                fileInfo.permission += "-";
            }
            if (file.canWrite()) {
                fileInfo.permission += "w";
            } else {
                fileInfo.permission += "-";
            }
            if (file.canExecute()) {
                fileInfo.permission += "x";
            } else {
                fileInfo.permission += "-";
            }
            ret.add(fileInfo);
        }
        sortFileInfoList(ret, context);
        return ret;
    }

    private static void getFolderSize(FileInfo fileInfo, File file) {
        if (!file.isDirectory())
            return;
    }

    private static String getSize(long length) {
        if (length < 1024)
            return length + "B";
        else if (1024 <= length && length < Math.pow(1024, 2)) {
            float scale = (float) length / 1024;
            DecimalFormat fnum = new DecimalFormat("##0.00");
            String dd = fnum.format(scale) + "KB";
            return dd;
        } else if (Math.pow(1024, 2) <= length && length < Math.pow(1024, 3)) {
            float scale = (float) length / 1024 / 1024;
            DecimalFormat fnum = new DecimalFormat("##0.00");
            String dd = fnum.format(scale) + "MB";
            return dd;
        } else {
            float scale = (float) length / 1024 / 1024 / 1024;
            DecimalFormat fnum = new DecimalFormat("##0.00");
            String dd = fnum.format(scale) + "GB";
            return dd;
        }
    }

    public static int getType(String name) {
        String[] strs = name.split("\\.");
        if (strs.length == 0)
            return FileInfo.TYPE_UNKOWN;
        String postfix = strs[strs.length - 1];
        if (postfix.equalsIgnoreCase("txt") ||
                postfix.equalsIgnoreCase("doc") ||
                postfix.equalsIgnoreCase("c") ||
                postfix.equalsIgnoreCase("java") ||
                postfix.equalsIgnoreCase("html") ||
                postfix.equalsIgnoreCase("rc") ||
                postfix.equalsIgnoreCase("py")) {
            return FileInfo.TYPE_DOCUMENT;
        } else if (postfix.equalsIgnoreCase("mp3") ||
                postfix.equalsIgnoreCase("wav") ||
                postfix.equals("flac")) {
            return FileInfo.TYPE_AUDIO;
        } else if (postfix.equalsIgnoreCase("mp4") ||
                postfix.equalsIgnoreCase("avi") ||
                postfix.equalsIgnoreCase("3gp") ||
                postfix.equalsIgnoreCase("wmv")) {
            return FileInfo.TYPE_VIDEO;
        } else if (postfix.equalsIgnoreCase("apk")) {
            return FileInfo.TYPE_APK;
        } else if (postfix.equalsIgnoreCase("jpg") ||
                postfix.equalsIgnoreCase("png") || postfix.equalsIgnoreCase("jpeg")) {
            return FileInfo.TYPE_PIC;
        } else {
            return FileInfo.TYPE_UNKOWN;
        }
    }

    public static void sortFileInfoList(List<FileInfo> fileInfos, Context context) {
        boolean sortByFolder = PreUtils.getBoolean(context, HomeActivity.PREF_FOLDER_PRE, false);
        Collections.sort(fileInfos, new StringfileInfos());
        if (sortByFolder) {
            List<FileInfo> folders = new ArrayList();
            List<FileInfo> files = new ArrayList();
            for (FileInfo info : fileInfos) {
                if (info.isDir)
                    folders.add(info);
                else
                    files.add(info);
            }
            fileInfos.clear();
            fileInfos.addAll(folders);
            fileInfos.addAll(files);
        }
    }

    static class StringfileInfos implements Comparator<FileInfo> {

        @Override
        public int compare(FileInfo lhs, FileInfo rhs) {
            return lhs.name.compareToIgnoreCase(rhs.name);
        }
    }

    public static FolderSpec getFolderSpec(String path) {
        File file = new File(path);
        FolderSpec spec = new FolderSpec();
        spec.location = file.getAbsolutePath();
        spec.name = file.getName();
        spec.parentFolder = file.getParent();
        Calendar cal = Calendar.getInstance();
        long time = file.lastModified();
        cal.setTimeInMillis(time);
        spec.date = cal.getTime().toLocaleString();
        spec.type = "文件夹";
        File[] files = file.listFiles();
        long size = getDirSize(file);
        for (File f : files) {
            if (f.isDirectory())
                spec.folderNum++;
            else
                spec.fileNum++;
        }
        spec.size = getSize(size);
        return spec;
    }

    private static long getDirSize(File file) {
        //判断文件是否存在
        if (file.exists()) {
            //如果是目录则递归计算其内容的总大小
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                if (children == null)
                    return 0;
                long size = 0;
                for (File f : children)
                    size += getDirSize(f);
                return size;
            } else {//如果是文件则直接返回其大小,以“兆”为单位
                long size = file.length();
                return size;
            }
        } else {
            return 0;
        }
    }

    public static Map<Integer, Integer> getFolderContnet(List<FileInfo> fileInfoList) {

        Map<Integer, Integer> map = new HashMap();
        for (FileInfo info : fileInfoList) {
            Integer count = map.get(info.type);
            if (count == null) {
                map.put(info.type, 1);
            } else {
                count = count + 1;
                map.put(info.type, count);
            }
        }
        return map;
    }

    public static List<FileInfo> getBest20(List<FileInfo> list) {
        List<FileInfo> sort = new ArrayList(list);
        Collections.sort(sort, new Comparator<FileInfo>() {
            @Override
            public int compare(FileInfo lhs, FileInfo rhs) {
                return (int) (rhs.length - lhs.length);
            }
        });

        List<FileInfo> ret = new ArrayList<>();
        for (int i = 0; i < sort.size() && i < 20; i++)
            ret.add(sort.get(i));
        return ret;
    }

    public static List<FileInfo> getBest20(Context context, String path) {
        List<FileInfo> sort = new ArrayList();
        File file = new File(path);
        getAllFile(sort, file, context);
        Collections.sort(sort, new Comparator<FileInfo>() {
            @Override
            public int compare(FileInfo lhs, FileInfo rhs) {
                return (int) (rhs.length - lhs.length);
            }
        });

        List<FileInfo> ret = new ArrayList<>();
        for (int i = 0; i < sort.size() && i < 20; i++)
            ret.add(sort.get(i));
        return ret;
    }

    private static void getAllFile(List<FileInfo> list, File file, Context context) {
        //判断文件是否存在
        if (file.exists()) {
            //如果是目录则递归计算其内容的总大小
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                if (children == null)
                    return;
                for (File f : children)
                    getAllFile(list, f, context);
                return;
            } else {//如果是文件则直接返回其大小,以“兆”为单位
                FileInfo fileInfo = new FileInfo();
                fileInfo.name = file.getName();
                fileInfo.path = file.getPath();
                fileInfo.isDir = file.isDirectory();
                fileInfo.length = file.length();
                //判断是否为显示文件夹大小
                if (!file.isDirectory()) {
                    fileInfo.type = getType(file.getName());
                    fileInfo.size = getSize(file.length());
                } else {
                    fileInfo.displayFolderSize = PreUtils.getBoolean(context,
                            HomeActivity.PREF_DISPLAY_FOLDER_SIZE, false);
                    File[] subFiles = file.listFiles();
                    if (subFiles == null) {
                        fileInfo.displayFolderSize = false;
                    } else {
                        fileInfo.fileCount = subFiles.length;
                        fileInfo.size = getSize(file.length());
                    }
                }

                //判断这个文件的隐藏性
                boolean displayHidden = PreUtils.getBoolean(context, HomeActivity.PREF_DISPLAY_HIDDEN, false);

                if (file.canRead()) {
                    fileInfo.permission += "r";
                } else {
                    fileInfo.permission += "-";
                }
                if (file.canWrite()) {
                    fileInfo.permission += "w";
                } else {
                    fileInfo.permission += "-";
                }
                if (file.canExecute()) {
                    fileInfo.permission += "x";
                } else {
                    fileInfo.permission += "-";
                }
                list.add(fileInfo);
            }
        } else
            return;
    }

    public static List<FileInfo> getPicUnderFolder(Context context, String path) {
        List<FileInfo> sort = new ArrayList();
        File file = new File(path);
        getAllFile(sort, file, context);
        List<FileInfo> ret = new ArrayList<>();
        for (FileInfo info : sort) {
            if (getType(info.name) == FileInfo.TYPE_PIC) {
                ret.add(info);
            }
        }
        return ret;
    }
}
