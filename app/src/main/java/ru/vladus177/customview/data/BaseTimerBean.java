package ru.vladus177.customview.data;

import java.util.ArrayList;

public class BaseTimerBean {
    private String name;
    private int totalTime;
    private int totalTimeLeft;
    private int cycles;
    private int sets;
    private ArrayList<ActivityBean> activities;

    public BaseTimerBean(String name, int totalTime, int cycles, int sets, ArrayList<ActivityBean> activities) {
        this.name = name;
        this.totalTime = totalTime;
        this.cycles = cycles;
        this.sets = sets;
        this.activities = activities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getCycles() {
        return cycles;
    }

    public void setCycles(int cycles) {
        this.cycles = cycles;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public ArrayList<ActivityBean> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<ActivityBean> activities) {
        this.activities = activities;
    }

    public int getTotalTimeLeft() {
        return totalTimeLeft;
    }

    public void setTotalTimeLeft(int totalTimeLeft) {
        this.totalTimeLeft = totalTimeLeft;
    }

     public ActivityBean getActivityBean(int position) {
        ActivityBean ab;
        if (!activities.isEmpty() && position < activities.size()) {
            ab = activities.get(position);
        } else {
            ab = new ActivityBean("Error", 20);
        }
        return ab;
     }
}
