package com.example.leidong.ldplugin.beans

class TaskTimeBean {
    private String taskName

    private long taskStartTime

    private long taskEndTime

    private long taskDuration

    String getTaskName() {
        return taskName
    }

    void setTaskName(String taskName) {
        this.taskName = taskName
    }

    long getTaskStartTime() {
        return taskStartTime
    }

    void setTaskStartTime(long taskStartTime) {
        this.taskStartTime = taskStartTime
    }

    long getTaskEndTime() {
        return taskEndTime
    }

    void setTaskEndTime(long taskEndTime) {
        this.taskEndTime = taskEndTime
    }

    long getTaskDuration() {
        return taskDuration
    }

    void setTaskDuration(long taskDuration) {
        this.taskDuration = taskDuration
    }
}