package com.example.amazinglu.pheramor_project;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.widget.ProgressBar;

import com.example.amazinglu.pheramor_project.fragment_confirm.ConfirmFragment;
import com.example.amazinglu.pheramor_project.fragment_register.EmailAndPassWordEditFragment;
import com.example.amazinglu.pheramor_project.fragment_register.GenderAndDobFragment;
import com.example.amazinglu.pheramor_project.fragment_register.RaceAndReligionFragment;
import com.example.amazinglu.pheramor_project.fragment_register.RegisterStartFragment;
import com.example.amazinglu.pheramor_project.fragment_register.UserInfoEditFragment;
import com.example.amazinglu.pheramor_project.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;

    public static final String KEY_USER = "user";
    public static final String KEY_EDIT_TYPE = "edit_type";
    public static final String EDIT_TYPE_FIRST_EDIT = "first_edit";
    public static final String EDIT_TYPE_RE_EDIT = "re_edit";

    private User user;

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.register_activity_title);

        String editKind = getIntent().getStringExtra(ConfirmFragment.KEY_EDIT_KIND);
        if (editKind != null) {// from confirm activity, open the proper fragment for editing
            user = getIntent().getParcelableExtra(MainActivity.KEY_USER);
            if (editKind.equals(ConfirmFragment.EDIT_KIND_EMAIL_PASSWORD)) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container,
                                EmailAndPassWordEditFragment.newInstance(user, EDIT_TYPE_RE_EDIT))
                        .commit();
            } else if (editKind.equals(ConfirmFragment.EDIT_KIND_USER_INFO)) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container,
                                UserInfoEditFragment.newInstance(user, EDIT_TYPE_RE_EDIT))
                        .commit();
            } else if (editKind.equals(ConfirmFragment.EDIT_KIND_GENDER_DOB)) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container,
                                GenderAndDobFragment.newInstance(user, EDIT_TYPE_RE_EDIT))
                        .commit();
            } else if (editKind.equals(ConfirmFragment.EDIT_KIND_RACE_RELIGION)) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container,
                                RaceAndReligionFragment.newInstance(user, EDIT_TYPE_RE_EDIT))
                        .commit();
            }
        } else { // begin of a new registration, open the welcome page
            user = new User();
            if (savedInstanceState == null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container, RegisterStartFragment.newInstance(user))
                        .commit();
            }
        }

        setTransition();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setTransition() {
        Transition mExplodeTransition = android.transition.TransitionInflater.from(MainActivity.this).
                inflateTransition(R.transition.explode_transition);
        getWindow().setEnterTransition(mExplodeTransition);
        getWindow().setExitTransition(mExplodeTransition);
    }
}
