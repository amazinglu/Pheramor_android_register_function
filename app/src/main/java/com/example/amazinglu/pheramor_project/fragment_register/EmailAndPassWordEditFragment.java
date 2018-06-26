package com.example.amazinglu.pheramor_project.fragment_register;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amazinglu.pheramor_project.MainActivity;
import com.example.amazinglu.pheramor_project.R;
import com.example.amazinglu.pheramor_project.base.BaseFragment;
import com.example.amazinglu.pheramor_project.model.User;
import com.example.amazinglu.pheramor_project.utils.InputUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.internal.Utils;

public class EmailAndPassWordEditFragment extends BaseFragment {

    @BindView(R.id.user_email_layout) TextInputLayout userEmailLayout;
    @BindView(R.id.user_password_layout) TextInputLayout userPasswordLayout;
    @BindView(R.id.user_password_confirm_layout) TextInputLayout userPasswordConfirmLayout;
    @BindView(R.id.next_button_one) Button nextButton;

    private String email;
    private String password;
    private String passwordConfirm;

    private User user;

    private AppCompatEditText EditTextEmail;
    private AppCompatEditText EditTextPassword;
    private AppCompatEditText EditTextPasswordConfirm;

    private String warning;

    public static EmailAndPassWordEditFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putParcelable(MainActivity.KEY_USER, user);
        EmailAndPassWordEditFragment fragment = new EmailAndPassWordEditFragment();
        fragment.setArguments(args);
        return fragment;
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

        user = getArguments().getParcelable(MainActivity.KEY_USER);

        EditTextEmail = (AppCompatEditText) userEmailLayout.getEditText();
        EditTextPassword = (AppCompatEditText) userPasswordLayout.getEditText();
        EditTextPasswordConfirm = (AppCompatEditText) userPasswordConfirmLayout.getEditText();

        if (user.email != null && !user.email.isEmpty()) {
            EditTextEmail.setText(user.email);
        }
        if (user.password != null && !user.password.isEmpty()) {
            EditTextPassword.setText(user.password);
            EditTextPasswordConfirm.setText(user.password);
        }


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInput();
                if (validate()) {
                    user.email = email;
                    user.password = password;
                    Toast.makeText(getContext(), "inout is valid", Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, UserInfoEditFragment.newInstance(user))
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, RegisterStartFragment.newInstance(user))
                        .addToBackStack(null)
                        .commit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getInput() {
        email = EditTextEmail.getText().toString();
        password = EditTextPassword.getText().toString();
        passwordConfirm = EditTextPasswordConfirm.getText().toString();
    }

    private boolean validate() {
        //reset errors
        EditTextEmail.setError(null);
        EditTextPassword.setError(null);
        EditTextPasswordConfirm.setError(null);

        if (InputUtil.isEmpty(email)) {
            EditTextEmail.setError(
                    getActivity().getResources().getString(R.string.email_empty_warning));
            return false;
        } else if (!InputUtil.isEmailValid(email)) {
            EditTextEmail.setError(
                    getActivity().getResources().getString(R.string.email_not_valid_warning));
            return false;
        }

        if (InputUtil.isEmpty(password)) {
            EditTextPassword.setError(
                    getActivity().getResources().getString(R.string.password_empty_warning));
            return false;
        } else if (!InputUtil.isPasswordValid(password)) {
            EditTextPassword.setError(
                    getActivity().getResources().getString(R.string.password_not_valid_warning));
            return false;
        }

        if (InputUtil.isEmpty(passwordConfirm)) {
            EditTextPasswordConfirm.setError(
                    getActivity().getResources().getString(R.string.password_empty_warning));
            return false;
        } else if (!InputUtil.isPasswordValid(passwordConfirm)) {
            EditTextPasswordConfirm.setError(
                    getActivity().getResources().getString(R.string.password_not_valid_warning));
            return false;
        } else if (!InputUtil.isPasswordMatch(password, passwordConfirm)) {
            EditTextPasswordConfirm.setError(
                    getActivity().getResources().getString(R.string.password_not_match_warning));
            return false;
        }
        return true;
    }
}
