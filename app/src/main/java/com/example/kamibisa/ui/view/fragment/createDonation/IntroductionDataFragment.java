package com.example.kamibisa.ui.view.fragment.createDonation;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kamibisa.R;
import com.example.kamibisa.data.model.Donation;
import com.example.kamibisa.ui.view.activity.CreateDonationActivity;
import com.google.android.material.button.MaterialButton;

public class IntroductionDataFragment extends Fragment implements View.OnClickListener {
    private static String TAG = "IntroductionData";

    private View rootView;

    private EditText introductionEditText;
    private EditText motivatorEditText;

    private MaterialButton nextButton;
    private MaterialButton backButton;

    public IntroductionDataFragment() {
        // Required empty public constructor
    }

    public static IntroductionDataFragment newInstance() {
        IntroductionDataFragment fragment = new IntroductionDataFragment();
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
        this.rootView = inflater.inflate(R.layout.fragment_introduction_data, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeUi();
        setOnClickListeners();
    }

    private void initializeUi() {
        this.introductionEditText = rootView.findViewById(R.id.edt_introductionData_introduction);
        this.motivatorEditText = rootView.findViewById(R.id.edt_introductionData_motivator);

        this.nextButton = rootView.findViewById(R.id.btn_introductionData_next);
        this.backButton = rootView.findViewById(R.id.btn_introductionData_back);
    }

    private void setOnClickListeners() {
        this.nextButton.setOnClickListener(this);
        this.backButton.setOnClickListener(this);
    }

    @Override
    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_introductionData_next:
                setDonationIntroductionData();
                insertDonation();
                break;

            case R.id.btn_introductionData_back:
                this.requireActivity().getSupportFragmentManager()
                        .popBackStackImmediate();
                break;

            default:
                break;
        }
    }

    public Donation setDonationIntroductionData() {
        Donation newDonation = ((CreateDonationActivity) this.requireActivity()).getNewDonation();

        String introduction = introductionEditText.getText().toString();
        String motivator = motivatorEditText.getText().toString();

        String warningMessage = "";

        if(!Donation.isIntroductionValid(introduction)) {
            warningMessage = "Introduction cannot be empty";
        }
        else if(!Donation.isMotivatorValid(motivator)) {
            warningMessage = "Motivator cannot be empty";
        }
        else {
            newDonation.setIntroduction(introduction);
            newDonation.setMotivator(motivator);

            return newDonation;
        }

        Toast.makeText(requireContext(), warningMessage, Toast.LENGTH_LONG).show();
        return newDonation;
    }

    public void insertDonation() {
        ((CreateDonationActivity) this.requireActivity()).insertDonation();
    }
}