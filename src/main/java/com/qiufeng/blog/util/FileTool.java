package com.qiufeng.blog.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片上传
 */
public class FileTool {

    /**
     * 将图片复制到本地
     * @param file
     * @param filePath
     * @param fileName
     * @throws IOException
     */
    public static void uploadFiles(byte[] file,String filePath,String fileName) throws IOException {
        File targetFile = new File(filePath);
        if (!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }



}
