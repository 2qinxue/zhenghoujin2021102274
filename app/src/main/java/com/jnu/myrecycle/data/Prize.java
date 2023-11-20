package com.jnu.myrecycle.data;

import java.io.Serializable;

public class Prize implements Serializable {
    protected String PrizeName,Score,Num;
    public String getPrizeName() {
        return PrizeName;
    }

    public String getScore() {
        return Score;
    }

    public String getNum() {
        return Num;
    }

    public Prize(String TaskName, String Score, String Num) {
        this.PrizeName = TaskName;
        this.Score = Score;
        this.Num = Num;
    }
    public void setPrizeName(String newTaskName) {
        this.PrizeName = newTaskName;
    }

    public void setScore(String newTaskScore) {
        this.Score = newTaskScore;
    }
}
