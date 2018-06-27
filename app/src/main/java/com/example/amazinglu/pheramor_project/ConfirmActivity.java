package com.example.amazinglu.pheramor_project;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.amazinglu.pheramor_project.fragment_confirm.ConfirmFragment;
import com.example.amazinglu.pheramor_project.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfirmActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.confirm_activity_title);

        User user = getIntent().getParcelableExtra(MainActivity.KEY_USER);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, ConfirmFragment.newInstance(user))
                .commit();
    }
}
