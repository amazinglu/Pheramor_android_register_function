package com.example.amazinglu.pheramor_project.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.amazinglu.pheramor_project.MainActivity;
import com.example.amazinglu.pheramor_project.model.User;

public abstract class BaseFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
}
