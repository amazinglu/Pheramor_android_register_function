package com.example.amazinglu.pheramor_project.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.transition.Fade;
import android.support.transition.TransitionInflater;
import android.support.v4.app.Fragment;
import android.transition.Slide;
import android.transition.Transition;

import com.example.amazinglu.pheramor_project.MainActivity;
import com.example.amazinglu.pheramor_project.R;
import com.example.amazinglu.pheramor_project.model.User;

public abstract class BaseFragment extends Fragment {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setUpTransition();
    }

    /**
     * set up simple transition for the fragment
     * https://github.com/lgvalle/Material-Animations
     * */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setUpTransition() {
        Transition mFadeTransition = android.transition.TransitionInflater.from(getContext()).
                        inflateTransition(R.transition.fade_transition);
        Transition mSildeTransition = android.transition.TransitionInflater.from(getContext()).
                inflateTransition(R.transition.silde_transition);
        Transition mExplodeTransition = android.transition.TransitionInflater.from(getContext()).
                inflateTransition(R.transition.explode_transition);

        setEnterTransition(mExplodeTransition);
        setExitTransition(mSildeTransition);
        setReenterTransition(mFadeTransition);
        setReturnTransition(mFadeTransition);
    }
}
