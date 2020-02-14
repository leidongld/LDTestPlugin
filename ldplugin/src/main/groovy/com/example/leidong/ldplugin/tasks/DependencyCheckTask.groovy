package com.example.leidong.ldplugin.tasks


import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class DependencyCheckTask extends DefaultTask {
    @Input
    public Project project

    DependencyCheckTask() {
        setDescription("DependencyCheckTask用来搜索各个模块的依赖情况")
        setGroup("leidong")
    }

    @TaskAction
    def checkDependencies() {
        if (project == null) {
            return
        }

        project.allprojects.each { project ->
            String name = project.getName()
            project.dependencies.each { dependency ->
                println "[" + name + "] " + dependency.toString()
            }
        }
    }
}