package com.example.leidong.ldplugin.utils

import org.gradle.api.GradleException

public class FileUtils {
    /**
     * 将字符串写入文件中
     *
     * @param content
     * @param file
     */
    static void writeToFile(String content, File file) {
        try {
            FileWriter fileWriter = new FileWriter(file.getAbsolutePath())
            fileWriter.write(content)
            fileWriter.flush()
            fileWriter.close()
        } catch (GradleException e) {
            throw new GradleException("写入文件异常")
        }
    }
}