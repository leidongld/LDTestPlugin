package com.example.leidong.ldplugin.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
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

        project.getRootProject().subprojects.each { ele ->
            String name = ele.getName()
            println "模块${name}用到了这些依赖："
            ele.configurations.each { Configuration configuration ->
                configuration.resolutionStrategy {
                    eachDependency {
                        println
                        it.requested.group + ":"
                        it.requested.name + ":"
                        it.requested.version
                    }
                }
            }

        }
    }
}