package ru.vladus177.customview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.vladus177.customview.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {

    private FragmentMainBinding mViewDataBinding;
    private MainViewModel mViewModel;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.start();
    }

    public void setViewModel(@NonNull MainViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_main, container, false);
        if (mViewDataBinding == null) {
            mViewDataBinding = FragmentMainBinding.bind(root);
        }

        mViewDataBinding.setViewmodel(mViewModel);

        setRetainInstance(false);

        return mViewDataBinding.getRoot();
    }

}
