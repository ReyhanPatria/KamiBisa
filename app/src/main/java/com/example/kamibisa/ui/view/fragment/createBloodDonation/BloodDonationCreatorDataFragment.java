package com.example.kamibisa.ui.view.fragment.createBloodDonation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kamibisa.R;
import com.example.kamibisa.data.model.BloodDonation;
import com.example.kamibisa.ui.view.activity.CreateBloodDonationActivity;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;

public class BloodDonationCreatorDataFragment extends Fragment implements View.OnClickListener {
    private static String TAG = "CreatorDataFragment";

    private View rootView;

    private EditText creatorNameEditText;
    private EditText phoneEditText;
    private EditText socialMediaEditText;

    private Spinner creatorJobSpinner;

    private MaterialButton nextButton;

    public BloodDonationCreatorDataFragment() {
        // Required empty public constructor
    }

    public static BloodDonationCreatorDataFragment newInstance() {
        BloodDonationCreatorDataFragment fragment = new BloodDonationCreatorDataFragment();
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
        this.rootView = inflater.inflate(R.layout.fragment_blood_donation_creator_data, container, false);
        return this.rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeUi();
        setOnClickListeners();
    }

    private void initializeUi() {
        this.creatorNameEditText = rootView.findViewById(R.id.edt_bloodDonation_creatorData_name);
        this.phoneEditText = rootView.findViewById(R.id.edt_bloodDonation_creatorData_phone);
        this.socialMediaEditText = rootView.findViewById(R.id.edt_bloodDonation_creatorData_socialMedia);

        creatorJobSpinner = rootView.findViewById(R.id.spin_bloodDonation_creatorData_job);

        this.nextButton = rootView.findViewById(R.id.btn_bloodDonation_creatorData_next);

        // Fill job spinner with options
        ArrayAdapter<CharSequence> jobSpinnerAdapter = ArrayAdapter
                .createFromResource(this.requireContext(),
                        R.array.jobs, R.layout.custom_spinner_item);
        jobSpinnerAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        this.creatorJobSpinner.setAdapter(jobSpinnerAdapter);
    }

    private void setOnClickListeners() {
        this.nextButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == this.nextButton.getId()) {
            setCreatorData();
        }
    }

    private void setCreatorData() {
        String creatorId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String creatorName = this.creatorNameEditText.getText().toString();
        String phone = this.phoneEditText.getText().toString();
        String socialMedia = this.socialMediaEditText.getText().toString();
        String creatorJob = this.creatorJobSpinner.getSelectedItem().toString();

        String warningMessage = "";

        if(!BloodDonation.isCreatorNameValid(creatorName)) {
            warningMessage = "Name cannot be empty";
        }
        else if(!BloodDonation.isPhoneValid(phone)) {
            warningMessage = "Phone number is not valid";
        }
        else if(!BloodDonation.isSocialMediaValid(socialMedia)) {
            warningMessage = "Social media cannot be empty";
        }
        else {
            BloodDonation newBloodDonation = ((CreateBloodDonationActivity) this.requireActivity())
                    .getNewBloodDonation();

            newBloodDonation.setCreatorId(creatorId);
            newBloodDonation.setCreatorName(creatorName);
            newBloodDonation.setPhone(phone);
            newBloodDonation.setSocialMedia(socialMedia);
            newBloodDonation.setCreatorJob(creatorJob);

            gotoBeneficiaryDataFragment();

            return;
        }

        Toast.makeText(requireContext(), warningMessage, Toast.LENGTH_LONG).show();
    }

    private void gotoBeneficiaryDataFragment() {
        Fragment f = ((CreateBloodDonationActivity) this.requireActivity()).getBeneficiaryDataFragment();
        ((CreateBloodDonationActivity) this.requireActivity()).changeMenu(f, Boolean.TRUE);
    }
}