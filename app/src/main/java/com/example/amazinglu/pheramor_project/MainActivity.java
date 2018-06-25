package com.example.amazinglu.pheramor_project;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.amazinglu.pheramor_project.fragment_register.EmailAndPassWordEditFragment;
import com.example.amazinglu.pheramor_project.fragment_register.RegisterStartFragment;
import com.example.amazinglu.pheramor_project.model.User;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_USER = "user";

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = new User();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, RegisterStartFragment.newInstance(user))
                    .commit();
        }
    }
}
