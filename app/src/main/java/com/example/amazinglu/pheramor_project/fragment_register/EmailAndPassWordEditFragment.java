package com.example.amazinglu.pheramor_project.fragment_register;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.amazinglu.pheramor_project.R;
import com.example.amazinglu.pheramor_project.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmailAndPassWordEditFragment extends Fragment {

    @BindView(R.id.user_email) TextView emailView;
    @BindView(R.id.user_password) TextView passwordView;
    @BindView(R.id.user_password_confirm) TextView passwordComfirmView;
    @BindView(R.id.next_button_one) Button nextButton;

    public static EmailAndPassWordEditFragment newInstance(User user) {
        EmailAndPassWordEditFragment fragment = new EmailAndPassWordEditFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_email_password, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
