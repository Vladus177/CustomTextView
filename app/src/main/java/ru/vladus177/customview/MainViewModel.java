package ru.vladus177.customview;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.CountDownTimer;
import android.view.View;


import java.util.Locale;
import java.util.concurrent.TimeUnit;

import ru.vladus177.customview.data.ActivityBean;
import ru.vladus177.customview.data.BaseTimerBean;
import ru.vladus177.customview.data.TrainingFactory;

public class MainViewModel extends BaseObservable {

    private static final String TAG = "MainViewModel";

    public final ObservableField<String> title = new ObservableField<>();

    public final ObservableField<String> activity = new ObservableField<>();

    public final ObservableField<String> activityTimeLeft = new ObservableField<>();

    public final ObservableField<String> totalTimeLeft = new ObservableField<>();

    public final ObservableField<String> info = new ObservableField<>();

    public final ObservableInt sets = new ObservableInt();

    public final ObservableInt cycles = new ObservableInt();

    public final ObservableBoolean isRunning = new ObservableBoolean();

    private MainNavigator mNavigator;

    private BaseTimerBean mBtb;

    private ActivityBean mCurrentActivity;

    private CountDownTimer mActivityTimer;


    MainViewModel() {
        sets.set(0);
        cycles.set(0);
    }

    void onActivityCreated(MainNavigator navigator) {
        mNavigator = navigator;
    }

    void onActivityDestroyed() {
        // Clear references to avoid potential memory leaks.
        mNavigator = null;
        mActivityTimer = null;
    }

    void start() {
        TrainingFactory mFactory = new TrainingFactory();
        mBtb = mFactory.getTodayTraining();
        mBtb.setTotalTimeLeft(mBtb.getTotalTime());
        mCurrentActivity = mBtb.getActivityBean(sets.get());
        activity.set(mCurrentActivity.getName());
        setActivityTime();
        setTotalTime();
        info.set("Press Play to start");

    }

    private void setTotalTime() {

        int time = mBtb.getTotalTimeLeft();
        int min = time / 60;
        int seconds = time % 60;
        String str = String.format(Locale.getDefault(), "%02d:%02d", min, seconds);
        totalTimeLeft.set(str);
    }

    private void setActivityTime() {

        int time2 = mCurrentActivity.getTimeLeft();
        int min2 = time2 / 60;
        int seconds2 = time2 % 60;
        String str2 = String.format(Locale.getDefault(), "%02d:%02d", min2, seconds2);
        activityTimeLeft.set(str2);

    }

    public ObservableField<String> getTitle() {
        return title;
    }

    public ObservableField<String> getActivity() {
        return activity;
    }

    public ObservableField<String> getActivityTimeLeft() {
        return activityTimeLeft;
    }

    public ObservableField<String> getTotalTimeLeft() {
        return totalTimeLeft;
    }

    public ObservableInt getSets() {
        return sets;
    }

    public ObservableInt getCycles() {
        return cycles;
    }

    public ObservableBoolean getIsRunning() {
        return isRunning;
    }

    public void onPause(View view) {
        if (mNavigator != null && view != null) {
            mNavigator.onTimerStop(view);
        }

        if (isRunning.get()) {

            isRunning.set(false);
            stopActivityTimer();
            info.set("Paused. Press Play to start");

        } else {

            isRunning.set(true);
            startNewActivityTimer();

        }
    }

    private void startNewActivityTimer() {

        mActivityTimer = new CountDownTimer(TimeUnit.SECONDS.toMillis(mBtb.getTotalTimeLeft()), 1000) {
            @Override
            public void onTick(long l) {

                mBtb.setTotalTimeLeft(mBtb.getTotalTimeLeft() - 1);

                mCurrentActivity.setTimeLeft(mCurrentActivity.getTimeLeft() - 1);

                if (mCurrentActivity.getTimeLeft() == 0) {

                    if (sets.get() < mBtb.getActivities().size() - 1) {

                        sets.set(sets.get() + 1);
                        mCurrentActivity = mBtb.getActivityBean(sets.get());
                        activity.set(mCurrentActivity.getName());
                        mCurrentActivity.setTimeLeft(mCurrentActivity.getDuration());

                    } else {

                        if (cycles.get() < mBtb.getCycles() - 1) {
                            cycles.set(cycles.get() + 1);
                            sets.set(0);
                            mCurrentActivity = mBtb.getActivityBean(sets.get());
                            activity.set(mCurrentActivity.getName());
                            mCurrentActivity.setTimeLeft(mCurrentActivity.getDuration());
                        } else {
                            if (mActivityTimer != null) {
                                mActivityTimer.cancel();
                            }

                        }
                    }

                }

                setTotalTime();

                setActivityTime();

            }

            @Override
            public void onFinish() {
                cycles.set(0);
                sets.set(0);
                mCurrentActivity = null;
                stopActivityTimer();
                isRunning.set(false);
                MainViewModel.this.start();

            }
        }.start();
    }

    private void stopActivityTimer() {

        if (mActivityTimer != null) {
            mActivityTimer.cancel();
        }
    }

}
