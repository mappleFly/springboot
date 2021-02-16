package com.lms.springboot.utils;

import de.innosystec.unrar.Archive;
import de.innosystec.unrar.NativeStorage;
import de.innosystec.unrar.rarfile.FileHeader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class UnZipAndRarUtil {

    // 获取当前运行环境是win还是linux
    private static final boolean isLinux = System.getProperty("os.name").toLowerCase().indexOf("linux") >= 0;

    /**
     * 使用apache ant实现zip解压缩
     *
     * @param sourceZip 压缩文件
     * @param outDir    解压结果路径
     * @throws IOException
     */
    public static void unZip(String sourceZip, String outDir) throws IOException {

        File outFileDir = new File(outDir);
        if (!outFileDir.exists()) {
            boolean isMakDir = outFileDir.mkdirs();
            if (isMakDir) {
                System.out.println("创建压缩目录成功");
            }
        }

        ZipFile zip = new ZipFile(new File(sourceZip));
        for (Enumeration enumeration = zip.entries(); enumeration.hasMoreElements(); ) {
            ZipEntry entry = (ZipEntry) enumeration.nextElement();
            String zipEntryName = entry.getName();
            InputStream in = zip.getInputStream(entry);

            if (entry.isDirectory()) {      //处理压缩文件包含文件夹的情况
                File fileDir = new File(outDir + zipEntryName);
                fileDir.mkdir();
                continue;
            }

            File file = new File(outDir, zipEntryName);
            file.createNewFile();
            OutputStream out = new FileOutputStream(file);
            byte[] buff = new byte[1024];
            int len;
            while ((len = in.read(buff)) > 0) {
                out.write(buff, 0, len);
            }
            in.close();
            out.close();
        }
    }

    /**
     * 使用junrar解压,只支持winrar 5以下解压缩
     *
     * @param sourceRar 压缩文件
     * @param outDir    解压结果路径
     * @throws Exception
     */
    public static void unRar(String sourceRar, String outDir) throws Exception {
        File outFileDir = new File(outDir);
        if (!outFileDir.exists()) {
            boolean isMakDir = outFileDir.mkdirs();
            if (isMakDir) {
                System.out.println("创建压缩目录成功");
            }
        }
        Archive archive = new Archive(new NativeStorage(new File(sourceRar)));
        FileHeader fileHeader = archive.nextFileHeader();
        while (fileHeader != null) {
            if (fileHeader.isDirectory()) {
                fileHeader = archive.nextFileHeader();
                continue;
            }
            File out = new File(outDir + fileHeader.getFileNameString());
            if (!out.exists()) {
                if (!out.getParentFile().exists()) {
                    out.getParentFile().mkdirs();
                }
                out.createNewFile();
            }
            FileOutputStream os = new FileOutputStream(out);
            archive.extractFile(fileHeader, os);
            os.close();
            fileHeader = archive.nextFileHeader();
        }
        archive.close();
    }


    /**
     * 采用命令行方式解压文件,支持winrar 5以上解压缩
     *
     * @param sourceRar 压缩文件
     * @param destDir   解压结果路径
     * @param cmdPath   WinRAR.exe的路径，也可以在代码中写死
     * @return
     */
    public static boolean realExtract(String sourceRar, String destDir, String cmdPath) {
        File zipFile = new File(sourceRar);
        // 解决路径中存在/..格式的路径问题
        destDir = new File(destDir).getAbsoluteFile().getAbsolutePath();
        while (destDir.contains("..")) {
            String[] sepList = destDir.split("\\\\");
            destDir = "";
            for (int i = 0; i < sepList.length; i++) {
                if (!"..".equals(sepList[i]) && i < sepList.length - 1 && "..".equals(sepList[i + 1])) {
                    i++;
                } else {
                    destDir += sepList[i] + File.separator;
                }
            }
        }

        boolean bool = false;
        if (!zipFile.exists()) {
            return false;
        }
        // 开始调用命令行解压，参数-o+是表示覆盖的意思
        cmdPath = "C:\\Program Files\\WinRAR\\WinRAR.exe";
        if (isLinux) {
            cmdPath = "/usr/local/bin/unrar";
        }
        String cmd = cmdPath + " X -o+ " + zipFile + " " + destDir;
        System.out.println(cmd);
        try {
            Process proc = Runtime.getRuntime().exec(cmd);
            if (proc.waitFor() != 0) {
                if (proc.exitValue() == 0) {
                    bool = false;
                }
            } else {
                bool = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("解压" + (bool ? "成功" : "失败"));
        return bool;
    }


    private static void unfile(String sourceFile, String destDir) throws Exception {
        // 根据类型，进行相应的解压缩
        String type = sourceFile.substring(sourceFile.lastIndexOf(".") + 1);
        if (type.toLowerCase().equals("zip")) {
            UnZipAndRarUtil.unZip(sourceFile, destDir);
        } else if (type.toLowerCase().equals("rar")) {
            UnZipAndRarUtil.realExtract(sourceFile, destDir, null);
        } else {
            System.out.println(type.toLowerCase());
            throw new RuntimeException("deCompress: 只支持zip和rar格式的压缩包！");
        }
    }

    /**
     * 解压到指定目录
     */
    public static void deCompress(String sourceFile, String destDir) throws Exception {
        if (sourceFile == null || destDir == null) {
            throw new RuntimeException("deCompress: 目录不能为空");
        }
        // 保证文件夹路径最后是"/"或者"\"
        char lastChar = destDir.charAt(destDir.length() - 1);
        if (lastChar != '/' && lastChar != '\\') {
            // 代码中拼接文件夹 / 建议都用File.separator规避系统不同系统影响
            destDir += File.separator;
        }
        unfile(sourceFile, destDir);
    }

    /**
     * 解压到指定目录
     */
    public static void deCompress(File sourceFile, String destDir) throws Exception {
        if (!sourceFile.exists() || sourceFile.isDirectory()) {
            throw new RuntimeException("deCompress: 文件不存在");
        }
        deCompress(sourceFile.getPath(), destDir);
    }

    /**
     * 解压到当前目录
     */
    public static void deCompress(String sourceFile) throws Exception {

        // 获得文件目录
        int i = sourceFile.lastIndexOf("/");
        int d = sourceFile.lastIndexOf("\\");
        if (i == -1 && d == -1) {
            throw new RuntimeException("deCompress: 目录separator异常");
        } else if (i == -1) {
            i = d;
        }
        String destDir = sourceFile.substring(0, i + 1);
        unfile(sourceFile, destDir);
    }

    /**
     * 解压到当前目录
     *
     * @param sourceFile 当前文件路径
     * @throws Exception
     */
    public static void deCompress(File sourceFile) throws Exception {
        if (!sourceFile.exists() || sourceFile.isDirectory()) {
            throw new RuntimeException("deCompress: 文件不存在");
        }
        deCompress(sourceFile.getPath());
    }

    public static void main(String[] args) throws Exception {
        String zipFilepath = "E:\\自己\\test\\自己.rar";
        UnZipAndRarUtil.deCompress(new File(zipFilepath));
    }

}
