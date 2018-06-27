package com.example.amazinglu.pheramor_project.fragment_register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
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

import com.example.amazinglu.pheramor_project.ConfirmActivity;
import com.example.amazinglu.pheramor_project.MainActivity;
import com.example.amazinglu.pheramor_project.R;
import com.example.amazinglu.pheramor_project.base.BaseFragment;
import com.example.amazinglu.pheramor_project.model.User;
import com.example.amazinglu.pheramor_project.utils.InputUtil;
import com.example.amazinglu.pheramor_project.utils.ViewUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RaceAndReligionFragment extends BaseFragment {

    @BindView(R.id.user_race) AppCompatSpinner userRaceChooser;
    @BindView(R.id.user_religion) AppCompatSpinner userReligionChooser;
    @BindView(R.id.next_button_four) Button nextButton;

    private User user;
    private String editType;

    private String race, religion;

    public static RaceAndReligionFragment newInstance(User user, String editType) {
        Bundle args = new Bundle();
        args.putParcelable(MainActivity.KEY_USER, user);
        args.putString(MainActivity.KEY_EDIT_TYPE, editType);
        RaceAndReligionFragment fragment = new RaceAndReligionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_race_religion, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        user = getArguments().getParcelable(MainActivity.KEY_USER);
        editType = getArguments().getString(MainActivity.KEY_EDIT_TYPE);

        // set up race chooser
        ArrayAdapter<CharSequence> RaceChooserAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.race_chooser, android.R.layout.simple_spinner_item);
        RaceChooserAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userRaceChooser.setAdapter(RaceChooserAdapter);
        userRaceChooser.setVisibility(View.VISIBLE);
        userRaceChooser.setOnItemSelectedListener(new RaceChooserOnItemSelectListener());

        // set up religion chooser
        ArrayAdapter<CharSequence> ReligionChooserAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.religion_chooser, android.R.layout.simple_spinner_item);
        ReligionChooserAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userReligionChooser.setAdapter(ReligionChooserAdapter);
        userReligionChooser.setVisibility(View.VISIBLE);
        userReligionChooser.setOnItemSelectedListener(new ReligionChooserOnItemSelectListener());

        if (user.race != null) {
            race = user.race;
            ViewUtil.setSpinText(userRaceChooser, user.race);
        }
        if (user.religion != null) {
            religion = user.religion;
            ViewUtil.setSpinText(userReligionChooser, user.religion);
        }

        // set up the next button
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.race = race;
                user.religion = religion;
                Toast.makeText(getContext(), "input is valid", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ConfirmActivity.class);
                intent.putExtra(MainActivity.KEY_USER, user);
                getActivity().startActivity(intent);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .remove(RaceAndReligionFragment.this)
                        .commit();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container,
                                GenderAndDobFragment.newInstance(user, MainActivity.EDIT_TYPE_FIRST_EDIT))
                        .commit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * RaceChooserOnItemSelectListener
     * */
    class RaceChooserOnItemSelectListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            race = ((TextView)view).getText().toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    /**
     * ReligionChooserOnItemSelectListener
     * */
    class ReligionChooserOnItemSelectListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            religion = ((TextView)view).getText().toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}
