package com.example.leidong.ldplugin.beans

import org.gradle.api.artifacts.Dependency

/**
 * Created by Lei Dong on 2020/2/11.
 */
class DependencyBean {
    private String projectName

    private List<Dependency> dependencyList

    String getProjectName() {
        return projectName
    }

    void setProjectName(String projectName) {
        this.projectName = projectName
    }

    List<Dependency> getDependencyList() {
        return dependencyList
    }

    void setDependencyList(List<Dependency> dependencyList) {
        this.dependencyList = dependencyList
    }
}
