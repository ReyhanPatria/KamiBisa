package com.example.kamibisa.ui.view.fragment.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kamibisa.R;
import com.example.kamibisa.ui.view.activity.CreateDonationActivity;
import com.google.android.material.button.MaterialButton;

public class CreateDonationFragment extends Fragment implements View.OnClickListener {
    public static String TAG = "CreateDonationFragment";

    private View rootView;

    private MaterialButton createDonationButton;

    public CreateDonationFragment() {
        // Required empty public constructor
    }

    public static CreateDonationFragment newInstance() {
        CreateDonationFragment fragment = new CreateDonationFragment();
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
        rootView = inflater.inflate(R.layout.fragment_create_donation, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeUi();
        setOnClickListeners();
    }

    private void initializeUi() {
        this.createDonationButton = rootView.findViewById(R.id.btn_createDonation_create);
    }

    private void setOnClickListeners() {
        this.createDonationButton.setOnClickListener(this);
    }

        @Override
    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_createDonation_create:
                createDonation();
                break;

            default:
                break;
        }
    }

    private void createDonation() {
        Intent newIntent = new Intent(this.requireContext(), CreateDonationActivity.class);
        this.requireActivity().startActivity(newIntent);
    }
}