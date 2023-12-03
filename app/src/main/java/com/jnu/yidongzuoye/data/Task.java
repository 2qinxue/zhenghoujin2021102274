package com.jnu.yidongzuoye.data;

import java.io.Serializable;

public class Task implements Serializable {
    protected String TaskName,Score,Num;
    public String getTaskName() {
        return TaskName;
    }

    public String getScore() {
        return Score;
    }

    public String getNum() {
        return Num;
    }

    public Task(String TaskName, String Score, String Num) {
        this.TaskName = TaskName;
        this.Score = Score;
        this.Num = Num;
    }
    public void setTaskName(String newTaskName) {
        this.TaskName = newTaskName;
    }

    public void setScore(String newTaskScore) {
        this.Score = newTaskScore;
    }
}
