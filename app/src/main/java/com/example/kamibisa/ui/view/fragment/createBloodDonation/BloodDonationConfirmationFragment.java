package com.example.kamibisa.ui.view.fragment.createBloodDonation;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kamibisa.R;
import com.example.kamibisa.ui.view.activity.CreateBloodDonationActivity;
import com.google.android.material.button.MaterialButton;

public class BloodDonationConfirmationFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "BloodDonationConfirmationFragment";

    private View rootView;

    private MaterialButton yesButton;
    private MaterialButton noButton;
    private MaterialButton backButton;

    public BloodDonationConfirmationFragment() {
        // Required empty public constructor
    }

    public static BloodDonationConfirmationFragment newInstance() {
        BloodDonationConfirmationFragment fragment = new BloodDonationConfirmationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.rootView = inflater.inflate(R.layout.fragment_blood_donation_confirmation, container, false);
        return this.rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeUi();
        setOnClickListeners();
    }

    private void initializeUi() {
        this.yesButton = rootView.findViewById(R.id.btn_bloodDonation_confirmation_yes);
        this.noButton = rootView.findViewById(R.id.btn_bloodDonation_confirmation_no);
        this.backButton = rootView.findViewById(R.id.btn_bloodDonation_confirmation_back);
    }

    private void setOnClickListeners() {
        this.yesButton.setOnClickListener(this);
        this.noButton.setOnClickListener(this);
        this.backButton.setOnClickListener(this);
    }

    @Override
    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_bloodDonation_confirmation_yes:
                ((CreateBloodDonationActivity) this.requireActivity())
                        .insertBloodDonation();
                break;

            case R.id.btn_bloodDonation_confirmation_no:

            case R.id.btn_bloodDonation_confirmation_back:
                this.requireActivity().getSupportFragmentManager()
                        .popBackStackImmediate();
                break;
        }
    }
}