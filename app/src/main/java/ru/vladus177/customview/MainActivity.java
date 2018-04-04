package ru.vladus177.customview;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import ru.vladus177.customview.util.ActivityUtils;
import ru.vladus177.customview.util.ViewModelHolder;

public class MainActivity extends AppCompatActivity implements MainNavigator {

    private MainViewModel mViewModel;
    public static final String VIEWMODEL_TAG = "MAIN_VIEWMODEL_TAG";
    Animation slideUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainFragment mainFragment = findOrCreateViewFragment();

        mViewModel = findOrCreateViewModel();

        // Link View and ViewModel
        mainFragment.setViewModel(mViewModel);

        slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);

        mViewModel.onActivityCreated(this);
    }

    @Override
    protected void onDestroy() {

        mViewModel.onActivityDestroyed();
        super.onDestroy();
    }

    @NonNull
    private MainFragment findOrCreateViewFragment() {

        MainFragment mainFragment = (MainFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (mainFragment == null) {
            mainFragment = MainFragment.newInstance();

            // Send the task ID to the fragment
            Bundle bundle = new Bundle();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    mainFragment, R.id.contentFrame);
        }
        return mainFragment;
    }

    private MainViewModel findOrCreateViewModel() {

        @SuppressWarnings("unchecked")
        ViewModelHolder<MainViewModel> retainedViewModel =
                (ViewModelHolder<MainViewModel>) getSupportFragmentManager()
                        .findFragmentByTag(VIEWMODEL_TAG);

        if (retainedViewModel != null && retainedViewModel.getViewmodel() != null) {
            // If the model was retained, return it.
            return retainedViewModel.getViewmodel();
        } else {
            // There is no ViewModel yet, create it.
            MainViewModel viewModel = new MainViewModel();

            // and bind it to this Activity's lifecycle using the Fragment Manager.
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel),
                    VIEWMODEL_TAG);
            return viewModel;
        }
    }

    @Override
    public void onTimerStop(View view) {
        view.startAnimation(slideUp);
    }
}
