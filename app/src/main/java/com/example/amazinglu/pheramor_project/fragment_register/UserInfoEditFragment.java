package com.example.amazinglu.pheramor_project.fragment_register;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amazinglu.pheramor_project.MainActivity;
import com.example.amazinglu.pheramor_project.R;
import com.example.amazinglu.pheramor_project.base.BaseFragment;
import com.example.amazinglu.pheramor_project.model.User;
import com.example.amazinglu.pheramor_project.utils.InputUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserInfoEditFragment extends BaseFragment implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.user_name_layout) TextInputLayout userNameLayout;
    @BindView(R.id.user_zip_code_layout) TextInputLayout userZipcodeLayout;
    @BindView(R.id.user_height_layout) TextInputLayout userHeightLayout;
    @BindView(R.id.height_unit_of_measurement) Spinner heightUnitChooser;
    @BindView(R.id.next_button_two) Button nextButton;

    private User user;
    private String userName;
    private String zipCode;
    private double height;
    private String heightStr;
    private String heightMeasureUnit;

    private AppCompatEditText editTextUserName;
    private AppCompatEditText editTextZipCode;
    private AppCompatEditText editTextHeight;

    public static UserInfoEditFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putParcelable(MainActivity.KEY_USER, user);
        UserInfoEditFragment fragment = new UserInfoEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_name_zipcode_height, container, false);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        user = getArguments().getParcelable(MainActivity.KEY_USER);

        editTextUserName = (AppCompatEditText) userNameLayout.getEditText();
        editTextZipCode = (AppCompatEditText) userZipcodeLayout.getEditText();
        editTextHeight = (AppCompatEditText) userHeightLayout.getEditText();

        // setup the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.height_unit_chooser, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        heightUnitChooser.setAdapter(adapter);
        heightUnitChooser.setVisibility(View.VISIBLE);
        heightUnitChooser.setOnItemSelectedListener(this);

        if (user.name != null) {
            editTextUserName.setText(user.name);
        }
        if (user.zipCode != null) {
            editTextZipCode.setText(user.zipCode);
        }
        if (user.height != null) {
            editTextHeight.setText(Double.toString(user.height));
            setSpinText(heightUnitChooser, user.heightMeasureUnit);
        }

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserInput();
                if (validate()) {
                    user.name = userName;
                    user.zipCode = zipCode;
                    user.height = height;
                    user.heightMeasureUnit = heightMeasureUnit;
                    Toast.makeText(getContext(), "input is valid", Toast.LENGTH_SHORT).show();

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
                        .replace(R.id.fragment_container, EmailAndPassWordEditFragment.newInstance(user))
                        .addToBackStack(null)
                        .commit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getUserInput() {
        userName = editTextUserName.getText().toString();
        zipCode = editTextZipCode.getText().toString();
        heightStr = editTextHeight.getText().toString();
        if (!heightStr.isEmpty()) {
            height = Double.parseDouble(editTextHeight.getText().toString());
        }
    }

    private boolean validate() {
        editTextUserName.setError(null);
        editTextZipCode.setError(null);
        editTextHeight.setError(null);
        if (InputUtil.isEmpty(userName)) {
            editTextUserName.setError(
                    getActivity().getResources().getString(R.string.user_name_empty_warning));
            return false;
        }

        if (InputUtil.isEmpty(zipCode)) {
            editTextZipCode.setError(
                    getActivity().getResources().getString(R.string.zipcode_empty_warning));
            return false;
        }

        if (InputUtil.isEmpty(heightStr)) {
            editTextHeight.setError(
                    getActivity().getResources().getString(R.string.height_empty_warning));
            return false;
        } else if (!InputUtil.isHeightValid(heightStr)) {
            editTextHeight.setError(
                    getActivity().getResources().getString(R.string.height_not_valid_warning));
            return false;
        }
        return true;
    }

    private void setSpinText(Spinner spinner, String text) {
        for (int i = 0; i < spinner.getAdapter().getCount(); ++i) {
            if (spinner.getAdapter().getItem(i).toString().equals(text)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    /**
     * OnItemSelectedListener of spinner
     * */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        heightMeasureUnit = ((TextView)view).getText().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
