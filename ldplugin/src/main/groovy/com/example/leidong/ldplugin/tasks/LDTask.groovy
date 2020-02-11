package com.example.leidong.ldplugin.tasks

import com.example.leidong.ldplugin.utils.LogUtils
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class LDTask extends DefaultTask {
    @Input
    public String author

    @Input
    public String time

    @Input
    public String content

    LDTask() {
        setDescription("这是LDTask")
    }

    @TaskAction
    void showDetail() {
        println("开始执行LDTask的Action了")
        LogUtils.printContent(author)
        LogUtils.printContent(time)
        LogUtils.printContent(content)
    }

    @TaskAction
    void finish() {
        println "结束执行LDTask了"
    }
}