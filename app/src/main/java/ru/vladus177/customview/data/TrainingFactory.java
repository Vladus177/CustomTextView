package ru.vladus177.customview.data;

import java.util.ArrayList;

public class TrainingFactory {
    private final int MAX_CYCLES = 3;
    private final int MAX_SET_ACTIVITY_COUNT = 3;
    private final int SET_DURATION = 60;// sec
    private final int ACTIVITY_DURATION = 20;// sec

    public TrainingFactory() {
    }

    public BaseTimerBean getTodayTraining() {
        ArrayList<ActivityBean> activities = new ArrayList<>();
        ActivityBean ab1 = new ActivityBean("Cycling", ACTIVITY_DURATION);
        activities.add(ab1);
        ActivityBean ab2 = new ActivityBean("Swimming", ACTIVITY_DURATION);
        activities.add(ab2);
        ActivityBean ab3 = new ActivityBean("Running", ACTIVITY_DURATION);
        activities.add(ab3);

        return new BaseTimerBean("Iron man training", getTotalTime(), MAX_CYCLES, MAX_SET_ACTIVITY_COUNT, activities);
    }

    private int getTotalTime() {
        return (ACTIVITY_DURATION * MAX_SET_ACTIVITY_COUNT) * MAX_CYCLES;
    }
}
