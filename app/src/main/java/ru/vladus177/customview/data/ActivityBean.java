package ru.vladus177.customview.data;


public class ActivityBean {
    private String name;
    private int duration;
    private int timeLeft;

    ActivityBean(String name, int duration) {
        this.name = name;
        this.duration = duration;
        this.timeLeft = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }
}
