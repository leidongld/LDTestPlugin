package com.example.leidong.ldplugin


import com.example.leidong.ldplugin.beans.TaskTimeBean
import com.example.leidong.ldplugin.extensions.LDExtension
import com.example.leidong.ldplugin.tasks.*
import com.example.leidong.ldplugin.utils.LogUtils
import org.gradle.BuildListener
import org.gradle.BuildResult
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.initialization.Settings
import org.gradle.api.invocation.Gradle
import org.gradle.api.tasks.TaskState

/**
 * 雷栋的自定义插件
 */
class LDPlugin implements Plugin<Project> {
    private Project project
    private FunctionType type

//    private LDExtension ldExtension

    enum FunctionType {
        testLifeCycle,
        testExtensionAndTask,
        testListener,
        testDependencyCheck,
        testProjectApi,
        testGetProperty,
        testPrintVariants,
        testCheckAllActivities
    }

    @Override
    void apply(Project project) {
        LogUtils.printTitle("LDPlugin Start")
        init(project)

        switch (type) {
            case FunctionType.testLifeCycle:
                testLifeCycle()
                break
            case FunctionType.testExtensionAndTask:
                testExtensionAndTask()
                break
            case FunctionType.testListener:
                testListener()
                break
            case FunctionType.testDependencyCheck:
                testDependencyCheck()
                break
            case FunctionType.testProjectApi:
                testProjectApi()
                break
            case FunctionType.testGetProperty:
                testGetProperty()
                break
            case FunctionType.testPrintVariants:
                testPrintVariants()
                break
            case FunctionType.testCheckAllActivities:
                testCheckAllActivities()
                break
            default:
                break
        }

        LogUtils.printTitle("LDPlugin End")
    }

    /**
     * 查找所有的Activity并打印输出
     */
    def testCheckAllActivities() {
        project.task("checkAllActivities", type: CheckActivitiesTask) { Task task ->
            task.doFirst {
                task.project = project
            }
        }
    }

    /**
     * 打印所有的variant名称
     *
     * @return
     */
    def testPrintVariants() {
        project.afterEvaluate {
            project.android.applicationVariants.all { variant ->
                def name = variant.name
                println name
            }
        }
    }

    /**
     * 获取gradle.properties中的自定义参数
     *
     * @return
     */
    def testGetProperty() {
        Properties properties = new Properties()
        properties.load(project.getRootProject().file('gradle.properties').newInputStream())
        def includeModuleA = properties.getProperty("includeModuleA")
        println("includeModuleA:" + includeModuleA)

        println "includeModuleA:${project.getRootProject().getProperties().get("includeModuleA")}"
    }

    /**
     * 测试Project的一些API
     */
    def testProjectApi() {
        // 切换到Root Project遍历所有的Project
        println "输出所有的project信息"
        project.getRootProject().getAllprojects().eachWithIndex { Project pro, int index ->
            if (0 == index) {
                println "Root Project:" + pro
            } else {
                println "子Project：" + pro
            }
        }

        // 根据名称获取指定的project
        project.getRootProject().project("app") { Project pro ->
            println(pro.name)
        }

        // 获取RootDir 获取BuildDir
        println("rootDir = ") + project.getRootProject().getRootDir().getAbsolutePath()
        println("buildDir = " + project.getBuildDir().getAbsolutePath())

        // 对所有Project进行配置
        project.getRootProject().allprojects {
            println "hahaha"
        }


        //
    }

    /**
     * 一些配置的必要初始化操作
     *
     * @param project
     * @return
     */
    def init(Project project) {
        this.project = project
        this.type = FunctionType.testDependencyCheck
    }

    /**
     * 测试生命周期
     * @return
     */
    def testLifeCycle() {
        project.beforeEvaluate {
            LogUtils.printTitle("project.beforeEvaluate")
        }

        project.afterEvaluate {
            LogUtils.printTitle("project.afterEvaluate")
        }

        project.gradle.settingsEvaluated {
            LogUtils.printTitle("gradle.settingsEvaluated")
        }

        project.getGradle().projectsLoaded {
            LogUtils.printTitle("gradle.projectLoaded")
        }

        project.getGradle().projectsEvaluated {
            LogUtils.printTitle("gradle.projectsEvaluated")
        }

        project.getGradle().beforeProject {
            LogUtils.printTitle("gradle.beforeProject")
        }

        project.getGradle().afterProject {
            LogUtils.printTitle("gradle.afterProject")
        }

        project.getGradle().buildStarted {
            LogUtils.printTitle("gradle.buildStarted")
        }

        project.getGradle().buildFinished {
            LogUtils.printTitle("gradle.buildFinished")
        }
    }

    /**
     * 简单测试一下extension和自定义Task
     */
    def testExtensionAndTask() {
        project.extensions.create("ldExtension", LDExtension)

        project.task("ldTask", type: LDTask) { Task task ->
            println "ldTask config"

            task.doFirst {
                println ">>>" + project.ldExtension.author
                println ">>>" + project.ldExtension.time
                println ">>>" + project.ldExtension.content

                author = "leidong"
                time = new Date().toString()
                content = "This is content."
                println("ldTask doFirst")
            }

            task.doLast {
                println("ldTask doLast")
            }
        }
    }

    /**
     * 测试监听器功能
     *
     * @return
     */
    def testListener() {
        HashMap<String, TaskTimeBean> taskTimeBeans = new HashMap<>()

        project.gradle.addListener(new TaskExecutionListener() {
            @Override
            void beforeExecute(Task task) {
                TaskTimeBean bean = new TaskTimeBean()
                bean.setTaskName(task.getName())
                bean.setTaskStartTime(System.currentTimeMillis())
                taskTimeBeans.put(task.getName(), bean)
            }

            @Override
            void afterExecute(Task task, TaskState taskState) {
                TaskTimeBean bean = taskTimeBeans.get(task.getName())
                if (bean != null) {
                    bean.setTaskEndTime(System.currentTimeMillis())
                    long taskDuration = bean.getTaskEndTime() - bean.getTaskStartTime()
                    bean.setTaskDuration(taskDuration)
                }
            }
        })

        project.gradle.addBuildListener(new BuildListener() {
            @Override
            void buildStarted(Gradle gradle) {

            }

            @Override
            void settingsEvaluated(Settings settings) {

            }

            @Override
            void projectsLoaded(Gradle gradle) {

            }

            @Override
            void projectsEvaluated(Gradle gradle) {

            }

            @Override
            void buildFinished(BuildResult buildResult) {
                for (Map.Entry<String, TaskTimeBean> ele : taskTimeBeans.entrySet()) {
                    String taskName = ele.getKey()
                    TaskTimeBean bean = ele.getValue()
                    println("[" + taskName + "] 用时：" + bean.getTaskDuration() + "ms")
                }
            }
        })
    }

    /**
     * 测试依赖
     */
    def testDependencyCheck() {
        project.task("ldTestDependencyCheck", type: DependencyCheckTask) { task ->
            doFirst {
                LogUtils.printContent("DependencyCheck Task doFirst")
                task.project = project
            }
            doLast {
                LogUtils.printContent("DependencyCheck Task doLast")
            }
        }
    }
}