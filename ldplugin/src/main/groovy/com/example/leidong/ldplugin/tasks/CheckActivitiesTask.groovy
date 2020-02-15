package com.example.leidong.ldplugin.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class CheckActivitiesTask extends DefaultTask {
    @Input
    public Project project

    CheckActivitiesTask() {
        setDescription("查找工程下所有的Activity")
        setGroup("leidong")
    }

    @TaskAction
    def check() {
        if (project == null) {
            return
        }
        project.getRootProject().subprojects.each { Project ele ->
            if ("ldplugin".equals(ele.name)) {
                return
            }
            println '========================'
            println "Project = " + ele.name
            File manifestFile = ele.file("./src/main/AndroidManifest.xml")
            if (manifestFile.exists()) {
                println manifestFile.getAbsolutePath()
            }
            XmlSlurper xmlSlurper = new XmlSlurper()
            def xml = xmlSlurper.parse(manifestFile)

            println "包名 = " + xml.@package
            println "渠道build.gradle文件中的属性versionName：" + project.android.defaultConfig.versionName

            println "该模块用到了以下Activity："
            for (activity in xml.application.activity) {
                String activityName = activity.@'android:name'
                println activityName.replace(".", "")
            }

            println '该模块定义了以下权限：'
            for (perm in xml.'uses-permission') {
                println perm.@'android:name'
            }
            println '========================'
        }
    }
}