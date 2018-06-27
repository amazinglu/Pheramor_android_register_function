package com.example.amazinglu.pheramor_project.fragment_confirm;

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
    @BindView(R.id.user_gender_dob_edit) AppCompatTextView GenderDobEdit;
    @BindView(R.id.user_race_religion_edit) AppCompatTextView raceReligionEdit;

    @BindView(R.id.user_image_confirm) ImageView userImageConfirm;

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
    }

    private void setUpViewContent() {
        userEmailConfirm.setText(getResources().getString(R.string.email_title) + " " + user.email);
        userPasswordConfirm.setText(getResources().getString(R.string.password_title) + " " + user.password);
        userNameConfirm.setText(getResources().getString(R.string.name_title) + " " + user.name);
        userZipcodeConfirm.setText(getResources().getString(R.string.zipcode_title) + " " + user.zipCode);
        userHeightConfirm.setText(getResources().getString(R.string.height_title) + " " +
                user.height + user.heightMeasureUnit);
        userGenderConfirm.setText(getResources().getString(R.string.gender_title) + " " + user.gender);
        userDobConfirm.setText(getResources().getString(R.string.dob_title) + " " +
                DateUtil.dateToString(user.dateOfBirth));
        userGenderInInterestConfirm.setText(getResources().
                getString(R.string.gender_in_interest_title) + " " + user.genderInInterest);
        userAgeRangeConfirm.setText(getResources().getString
                (R.string.age_range_title) + " " + user.interestMinAge + " -> " + user.interestMaxAge);
        userRaceConfirm.setText(getResources().getString(R.string.race_title) + " " + user.race);
        userReligionConfirm.setText(getResources().getString(R.string.religion_title) + " " + user.religion);
        if (user.userImageUrl != null) {
            ImageUtil.loadImageLocal(getContext(), user.userImageUrl, userImageConfirm);
        }
    }
}
