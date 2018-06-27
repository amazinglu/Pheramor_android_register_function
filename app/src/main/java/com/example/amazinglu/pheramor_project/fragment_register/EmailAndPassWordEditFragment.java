package com.example.amazinglu.pheramor_project.fragment_register;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
    private String editType;

    private AppCompatEditText EditTextEmail;
    private AppCompatEditText EditTextPassword;
    private AppCompatEditText EditTextPasswordConfirm;

    public static EmailAndPassWordEditFragment newInstance(User user, String editType) {
        Bundle args = new Bundle();
        args.putParcelable(MainActivity.KEY_USER, user);
        args.putString(MainActivity.KEY_EDIT_TYPE, editType);
        EmailAndPassWordEditFragment fragment = new EmailAndPassWordEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_email_password, container, false);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        user = getArguments().getParcelable(MainActivity.KEY_USER);
        editType = getArguments().getString(MainActivity.KEY_EDIT_TYPE);

        if (editType.equals(MainActivity.EDIT_TYPE_FIRST_EDIT)) {
            nextButton.setVisibility(View.VISIBLE);
        } else {
            nextButton.setVisibility(View.GONE);
        }

        EditTextEmail = (AppCompatEditText) userEmailLayout.getEditText();
        EditTextPassword = (AppCompatEditText) userPasswordLayout.getEditText();
        EditTextPasswordConfirm = (AppCompatEditText) userPasswordConfirmLayout.getEditText();

        if (user.email != null) {
            email = user.email;
            EditTextEmail.setText(user.email);
        }
        if (user.password != null) {
            password = user.password;
            passwordConfirm = user.password;
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
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container,
                                    UserInfoEditFragment.newInstance(user, MainActivity.EDIT_TYPE_FIRST_EDIT))
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        /**
         * edit type:
         * EDIT_TYPE_RE_EDIT => show the save button in the toolbar
         * EDIT_TYPE_FIRST_EDIT => not show the save button in the toolbar
         * (same to all the fragment in fragment_register folder)
         * */
        if (editType.equals(MainActivity.EDIT_TYPE_RE_EDIT)) {
            inflater.inflate(R.menu.menu_save, menu);
        } else {
            return;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /**
             * edit type:
             * EDIT_TYPE_RE_EDIT => go back to ConfirmFragment
             * EDIT_TYPE_FIRST_EDIT => go back to the previous edit fragment
             * (same to all the fragment in fragment_register folder)
             * */
            case android.R.id.home:
                if (editType.equals(MainActivity.EDIT_TYPE_FIRST_EDIT)) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, RegisterStartFragment.newInstance(user))
                            .addToBackStack(null)
                            .commit();
                } else {
                    getActivity().finish();
                }
                return true;
            case R.id.toolbar_save:
                getInput();
                if (validate()) {
                    user.email = email;
                    user.password = password;
                    Intent intent = new Intent();
                    intent.putExtra(MainActivity.KEY_USER, user);
                    getActivity().setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getInput() {
        email = EditTextEmail.getText().toString();
        password = EditTextPassword.getText().toString();
        passwordConfirm = EditTextPasswordConfirm.getText().toString();
    }

    /**
     * use AppCompatEditText.setError() to show the error msg when the input is not valid
     * for other views to show this msg, we can simply set the style of view to
     * style="@android:style/Widget.EditText"
     * */
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
