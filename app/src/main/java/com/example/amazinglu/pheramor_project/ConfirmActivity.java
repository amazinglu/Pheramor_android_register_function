package com.example.amazinglu.pheramor_project;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.widget.ProgressBar;

import com.example.amazinglu.pheramor_project.fragment_confirm.ConfirmFragment;
import com.example.amazinglu.pheramor_project.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfirmActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.confirm_activity_title);

        User user = getIntent().getParcelableExtra(MainActivity.KEY_USER);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, ConfirmFragment.newInstance(user))
                .commit();

        setTransition();
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setTransition() {
        Transition mExplodeTransition = android.transition.TransitionInflater.from(ConfirmActivity.this).
                inflateTransition(R.transition.explode_transition);
        getWindow().setEnterTransition(mExplodeTransition);
        getWindow().setExitTransition(mExplodeTransition);
    }
}
