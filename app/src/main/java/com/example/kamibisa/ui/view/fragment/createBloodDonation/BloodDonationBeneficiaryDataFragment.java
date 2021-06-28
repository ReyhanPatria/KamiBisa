package com.example.kamibisa.ui.view.fragment.createBloodDonation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
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
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

public class BloodDonationBeneficiaryDataFragment extends Fragment implements View.OnClickListener {
    private static String TAG = "BloodDonationBeneficiaryDataFragment";

    private View rootView;

    private Spinner relationSpinner;
    private Spinner bloodTypeSpinner;

    private EditText nameEditText;
    private EditText locationEditText;
    private EditText linkEditText;
    private EditText finishedDateEditText;

    private MaterialButton backButton;
    private MaterialButton nextButton;

    private MaterialDatePicker finishedDatePicker;

    public BloodDonationBeneficiaryDataFragment() {
        // Required empty public constructor
    }

    public static BloodDonationBeneficiaryDataFragment newInstance() {
        BloodDonationBeneficiaryDataFragment fragment = new BloodDonationBeneficiaryDataFragment();
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
        this.rootView = inflater.inflate(R.layout.fragment_blood_donation_beneficiary_data, container, false);
        return this.rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeUi();
        setOnClickListeners();
    }

    private void initializeUi() {
        this.relationSpinner = rootView.findViewById(R.id.spin_bloodDonation_beneficiaryData_relation);
        this.bloodTypeSpinner = rootView.findViewById(R.id.spin_bloodDonation_beneficiaryData_bloodType);

        this.nameEditText = rootView.findViewById(R.id.edt_bloodDonation_beneficiaryData_name);
        this.locationEditText = rootView.findViewById(R.id.edt_bloodDonation_beneficiaryData_location);
        this.linkEditText = rootView.findViewById(R.id.edt_bloodDonation_beneficiaryData_link);
        this.finishedDateEditText = rootView.findViewById(R.id.edt_bloodDonation_beneficiaryData_finishedDate);

        this.backButton = rootView.findViewById(R.id.btn_bloodDonation_beneficiaryData_back);
        this.nextButton = rootView.findViewById(R.id.btn_bloodDOnation_beneficiaryData_next);

        this.finishedDatePicker = createDatePicker();

        // Fill job spinner with options
        ArrayAdapter<CharSequence> relationSpinnerAdapter = ArrayAdapter
                .createFromResource(this.requireContext(),
                        R.array.relations, R.layout.custom_spinner_item);
        relationSpinnerAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        this.relationSpinner.setAdapter(relationSpinnerAdapter);

        ArrayAdapter<CharSequence> bloodTypeSpinnerAdapter = ArrayAdapter
                .createFromResource(this.requireContext(),
                        R.array.blood_types, R.layout.custom_spinner_item);
        bloodTypeSpinnerAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        this.bloodTypeSpinner.setAdapter(bloodTypeSpinnerAdapter);
    }

    private void setOnClickListeners() {
        this.backButton.setOnClickListener(this);
        this.nextButton.setOnClickListener(this);
        this.finishedDateEditText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_bloodDonation_beneficiaryData_back:
                this.requireActivity().getSupportFragmentManager()
                        .popBackStackImmediate();
                break;

            case R.id.btn_bloodDOnation_beneficiaryData_next:
                setBeneficiaryData();
                break;

            case R.id.edt_bloodDonation_beneficiaryData_finishedDate:
                finishedDatePicker.show(
                        requireActivity().getSupportFragmentManager(),
                        finishedDatePicker.toString()
                );
                break;

            default:
                break;
        }
    }

    private void setBeneficiaryData() {
        String beneficiaryName = nameEditText.getText().toString();
        String beneficiaryRelation = relationSpinner.getSelectedItem().toString();
        String beneficiaryBloodType = bloodTypeSpinner.getSelectedItem().toString();
        String location = locationEditText.getText().toString();
        String link = linkEditText.getText().toString();
        Date createdDate = Date.from(Instant.now());
        Date finishedDate = Date.from(Instant.now());

        Boolean parseDateSuccessful = Boolean.FALSE;
        try {
            finishedDate = DateFormat.getDateInstance(DateFormat.MEDIUM)
                    .parse(finishedDateEditText.getText().toString());
            parseDateSuccessful = Boolean.TRUE;
        }
        catch(ParseException e) {
            Log.e(TAG, e.getMessage());
            parseDateSuccessful = Boolean.FALSE;
        }

        String warningMessage = "";

        if(!BloodDonation.isBeneficiaryNameValid(beneficiaryName)) {
            warningMessage = "Beneficiary name cannot be empty";

            if(beneficiaryName.length() > 50) {
                warningMessage = "Name maximum length is 50";
            }
        }
        else if(!BloodDonation.isFinishedDateValid(finishedDate) || !parseDateSuccessful) {
            warningMessage = "Finished date is not valid";
        }
        else if(!BloodDonation.isLocationValid(location)) {
            warningMessage = "Location cannot be empty";

            if(location.length() > 50) {
                warningMessage = "Location maximum length is 50";
            }
        }
        else if(!BloodDonation.isLinkValid(link)) {
            warningMessage = "Link cannot be empty";
        }
        else {
            BloodDonation newBloodDonation = ((CreateBloodDonationActivity) this.requireActivity()).getNewBloodDonation();

            newBloodDonation.setBeneficiaryName(beneficiaryName);
            newBloodDonation.setBeneficiaryBloodType(beneficiaryBloodType);
            newBloodDonation.setBeneficiaryRelation(beneficiaryRelation);
            newBloodDonation.setLocation(location);
            newBloodDonation.setLink(link);
            newBloodDonation.setCreatedDate(createdDate);
            newBloodDonation.setFinishedDate(finishedDate);

            gotoConfirmationFragment();

            return;
        }

        Toast.makeText(this.requireContext(), warningMessage, Toast.LENGTH_SHORT).show();
    }

    private void gotoConfirmationFragment() {
        Fragment f = ((CreateBloodDonationActivity) this.requireActivity()).getConfirmationFragment();
        ((CreateBloodDonationActivity) this.requireActivity()).changeMenu(f, Boolean.TRUE);
    }

    private MaterialDatePicker createDatePicker() {
        // Create DOB constraints
        CalendarConstraints dobConstraints = (new CalendarConstraints.Builder())
                .setValidator(DateValidatorPointForward.now())
                .build();

        // Create date picker for choosing DOB
        MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Blood Donation End Date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(dobConstraints)
                .build();

        // If user confirms the date, set the date on the edit text
        datePicker.addOnPositiveButtonClickListener(v -> finishedDateEditText.setText(datePicker.getHeaderText()));

        return datePicker;
    }
}