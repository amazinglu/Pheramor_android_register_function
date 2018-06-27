package com.example.amazinglu.pheramor_project.fragment_register;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.amazinglu.pheramor_project.MainActivity;
import com.example.amazinglu.pheramor_project.R;
import com.example.amazinglu.pheramor_project.base.BaseFragment;
import com.example.amazinglu.pheramor_project.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterStartFragment extends Fragment {

    @BindView(R.id.register_start_button) Button registerButton;

    private User user;

    public static RegisterStartFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putParcelable(MainActivity.KEY_USER, user);
        RegisterStartFragment fragment = new RegisterStartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * hide the toolbar in this fragment
     * */
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        user = getArguments().getParcelable(MainActivity.KEY_USER);

        /**
         * when replace the fragment, the current fragment will be remove
         * */
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container,
                                EmailAndPassWordEditFragment.newInstance(user, MainActivity.EDIT_TYPE_FIRST_EDIT))
                        .addToBackStack(null)
                        .commit();
            }
        });

        setTransition();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setTransition() {
        Transition mExplodeTransition =
                TransitionInflater.from(getContext()).
                        inflateTransition(R.transition.silde_transition);
        setExitTransition(mExplodeTransition);
        setEnterTransition(mExplodeTransition);
    }
}
