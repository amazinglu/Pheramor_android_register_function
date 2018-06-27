package com.example.amazinglu.pheramor_project.fragment_confirm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.amazinglu.pheramor_project.MainActivity;
import com.example.amazinglu.pheramor_project.R;
import com.example.amazinglu.pheramor_project.base.BaseFragment;
import com.example.amazinglu.pheramor_project.model.User;
import com.example.amazinglu.pheramor_project.utils.DateUtil;
import com.example.amazinglu.pheramor_project.utils.ImageUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfirmFragment extends BaseFragment {

    @BindView(R.id.user_email_confirm) AppCompatTextView userEmailConfirm;
    @BindView(R.id.user_password_confirm) AppCompatTextView userPasswordConfirm;
    @BindView(R.id.user_name_confirm) AppCompatTextView userNameConfirm;
    @BindView(R.id.user_zip_code_confirm) AppCompatTextView userZipcodeConfirm;
    @BindView(R.id.user_height_confirm) AppCompatTextView userHeightConfirm;
    @BindView(R.id.user_gender_confirm) AppCompatTextView userGenderConfirm;
    @BindView(R.id.user_dob_confirm) AppCompatTextView userDobConfirm;
    @BindView(R.id.user_gender_in_interest_confirm) AppCompatTextView userGenderInInterestConfirm;
    @BindView(R.id.user_interest_age_range_confirm) AppCompatTextView userAgeRangeConfirm;
    @BindView(R.id.user_race_confirm) AppCompatTextView userRaceConfirm;
    @BindView(R.id.user_religion_confirm) AppCompatTextView userReligionConfirm;

    @BindView(R.id.user_email_password_edit) AppCompatTextView emailPasswordEdit;
    @BindView(R.id.user_name_zip_code_height_edit) AppCompatTextView userInfoEdit;
    @BindView(R.id.user_gender_dob_edit) AppCompatTextView genderDobEdit;
    @BindView(R.id.user_race_religion_edit) AppCompatTextView raceReligionEdit;

    @BindView(R.id.user_image_confirm) ImageView userImageConfirm;

    public static final String KEY_EDIT_KIND = "edit_kind";
    public static final String EDIT_KIND_EMAIL_PASSWORD = "edit_kind_email_password";
    public static final String EDIT_KIND_USER_INFO = "edit_kind_user_info";
    public static final String EDIT_KIND_GENDER_DOB = "edit_kind_GENDER_DOB";
    public static final String EDIT_KIND_RACE_RELIGION = "edit_kind_race_religion";

    public static final int REQ_CODE_EMAIL_PASSWORD_EDIT = 1;
    public static final int REQ_CODE_USER_INFO_EDIT = 2;
    public static final int REQ_CODE_GENDER_DOB_EDIT = 3;
    public static final int REQ_CODE_RACE_RELIGION_EDIT = 4;

    private User user;

    public static ConfirmFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putParcelable(MainActivity.KEY_USER, user);
        ConfirmFragment fragment = new ConfirmFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirm, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        user = getArguments().getParcelable(MainActivity.KEY_USER);
        setUpViewContent();
        setUpEditButton();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE_EMAIL_PASSWORD_EDIT && resultCode == Activity.RESULT_OK) {
            user = data.getParcelableExtra(MainActivity.KEY_USER);
            setUpEmailPassword();
        }
        if (requestCode == REQ_CODE_USER_INFO_EDIT && resultCode == Activity.RESULT_OK) {
            user = data.getParcelableExtra(MainActivity.KEY_USER);
            setUpUserInfo();
        }
        if (requestCode == REQ_CODE_GENDER_DOB_EDIT && resultCode == Activity.RESULT_OK) {
            user = data.getParcelableExtra(MainActivity.KEY_USER);
            setUpGenderDob();
        }
        if (requestCode == REQ_CODE_RACE_RELIGION_EDIT && resultCode == Activity.RESULT_OK) {
            user = data.getParcelableExtra(MainActivity.KEY_USER);
            setUpRaceReligion();
        }
    }

    private void setUpEditButton() {
        emailPasswordEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra(KEY_EDIT_KIND, EDIT_KIND_EMAIL_PASSWORD);
                intent.putExtra(MainActivity.KEY_USER, user);
                /**
                 * activity.startActivityForResult will return to activity's onActivityResult
                 * fragment.startActivityForResult will return to fragment's onActivityResult
                 * */
                startActivityForResult(intent, REQ_CODE_EMAIL_PASSWORD_EDIT);
            }
        });

        userInfoEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra(KEY_EDIT_KIND, EDIT_KIND_USER_INFO);
                intent.putExtra(MainActivity.KEY_USER, user);
                startActivityForResult(intent, REQ_CODE_USER_INFO_EDIT);
            }
        });

        genderDobEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra(KEY_EDIT_KIND, EDIT_KIND_GENDER_DOB);
                intent.putExtra(MainActivity.KEY_USER, user);
                startActivityForResult(intent, REQ_CODE_GENDER_DOB_EDIT);
            }
        });

        raceReligionEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra(KEY_EDIT_KIND, EDIT_KIND_RACE_RELIGION);
                intent.putExtra(MainActivity.KEY_USER, user);
                startActivityForResult(intent, REQ_CODE_RACE_RELIGION_EDIT);
            }
        });
    }

    private void setUpViewContent() {
        setUpEmailPassword();
        setUpUserInfo();
        setUpGenderDob();
        setUpRaceReligion();
    }

    private void setUpEmailPassword() {
        userEmailConfirm.setText(getResources().getString(R.string.email_title) + " " + user.email);
        userPasswordConfirm.setText(getResources().getString(R.string.password_title) + " " + user.password);
    }

    private void setUpUserInfo() {
        userNameConfirm.setText(getResources().getString(R.string.name_title) + " " + user.name);
        userZipcodeConfirm.setText(getResources().getString(R.string.zipcode_title) + " " + user.zipCode);
        userHeightConfirm.setText(getResources().getString(R.string.height_title) + " " +
                user.height + user.heightMeasureUnit);
        if (user.userImageUrl != null) {
            ImageUtil.loadImageLocal(getContext(), user.userImageUrl, userImageConfirm);
        }
    }

    private void setUpGenderDob() {
        userGenderConfirm.setText(getResources().getString(R.string.gender_title) + " " + user.gender);
        userDobConfirm.setText(getResources().getString(R.string.dob_title) + " " +
                DateUtil.dateToString(user.dateOfBirth));
        userGenderInInterestConfirm.setText(getResources().
                getString(R.string.gender_in_interest_title) + " " + user.genderInInterest);
        userAgeRangeConfirm.setText(getResources().getString
                (R.string.age_range_title) + " " + user.interestMinAge + " -> " + user.interestMaxAge);
    }

    private void setUpRaceReligion() {
        userRaceConfirm.setText(getResources().getString(R.string.race_title) + " " + user.race);
        userReligionConfirm.setText(getResources().getString(R.string.religion_title) + " " + user.religion);
    }
}
