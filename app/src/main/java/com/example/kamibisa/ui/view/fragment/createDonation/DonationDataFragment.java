package com.example.kamibisa.ui.view.fragment.createDonation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kamibisa.R;
import com.example.kamibisa.data.model.Donation;
import com.example.kamibisa.ui.view.activity.CreateDonationActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

public class DonationDataFragment extends Fragment implements View.OnClickListener {
    private static String TAG = "DonationDataFragment";

    private View rootView;

    private EditText titleEditText;
    private EditText linkEditText;
    private EditText targetAmountEditText;
    private EditText descriptionEditText;
    private EditText finishedDateEditText;
    private EditText phoneEditText;

    private MaterialButton nextButton;
    private MaterialButton backButton;

    private MaterialDatePicker endDatePicker;

    public DonationDataFragment() {
        // Required empty public constructor
    }

    public static DonationDataFragment newInstance() {
        DonationDataFragment fragment = new DonationDataFragment();
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
        this.rootView = inflater.inflate(R.layout.fragment_donation_data, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeUi();
        setOnClickListeners();
    }

    private void initializeUi() {
        this.titleEditText = rootView.findViewById(R.id.edt_donationData_title);
        this.linkEditText = rootView.findViewById(R.id.edt_donationData_link);
        this.targetAmountEditText = rootView.findViewById(R.id.edt_donationData_targetAmount);
        this.descriptionEditText = rootView.findViewById(R.id.edt_donationData_description);
        this.finishedDateEditText = rootView.findViewById(R.id.edt_donationData_endDate);
        this.phoneEditText = rootView.findViewById(R.id.edt_donationData_phone);

        this.nextButton = rootView.findViewById(R.id.btn_donationData_next);
        this.backButton = rootView.findViewById(R.id.btn_donationData_back);

        this.endDatePicker = createDatePicker();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_donationData_next:
                setDonationData();
                break;

            case R.id.btn_donationData_back:
                this.requireActivity().getSupportFragmentManager()
                        .popBackStackImmediate();
                break;

            case R.id.edt_donationData_endDate:
                endDatePicker.show(
                        requireActivity().getSupportFragmentManager(),
                        endDatePicker.toString()
                );
                break;

            default:
                break;
        }
    }

    public void setOnClickListeners() {
        this.finishedDateEditText.setOnClickListener(this);
        this.nextButton.setOnClickListener(this);
        this.backButton.setOnClickListener(this);
    }

    private MaterialDatePicker createDatePicker() {
        // Create DOB constraints
        CalendarConstraints dobConstraints = (new CalendarConstraints.Builder())
                .setValidator(DateValidatorPointForward.now())
                .build();

        // Create date picker for choosing DOB
        MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Donation End Date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(dobConstraints)
                .build();

        // If user confirms the date, set the date on the edit text
        datePicker.addOnPositiveButtonClickListener(v -> finishedDateEditText.setText(datePicker.getHeaderText()));

        return datePicker;
    }

    private void gotoIntroductionDataFragment() {
        Fragment f = ((CreateDonationActivity) this.requireActivity()).getIntroductionDataFragment();
        ((CreateDonationActivity) this.requireActivity()).changeMenu(f, Boolean.TRUE);
    }

    public Donation setDonationData() {
        Donation newDonation = ((CreateDonationActivity) this.requireActivity()).getNewDonation();

        String title = titleEditText.getText().toString();
        String link = linkEditText.getText().toString();
        String targetAmount = targetAmountEditText.getText().toString();
        Integer gatheredAmount = 0;
        String description = descriptionEditText.getText().toString();
        Date finishedDate = Date.from(Instant.now());
        Date createdDate = Date.from(Instant.now());
        String phone = phoneEditText.getText().toString();

        try {
            finishedDate = DateFormat.getDateInstance(DateFormat.MEDIUM)
                    .parse(finishedDateEditText.getText().toString());
        }
        catch (ParseException e) {
            Log.e(TAG, e.getMessage());
        }

        String warningMessage = "";

        if(!Donation.isTitleValid(title)) {
            warningMessage = "Title cannot be empty";
        }
        else if(!Donation.isLinkValid(link)) {
            warningMessage = "Link cannot be empty";
        }
        else if(!Donation.isTargetAmountValid(targetAmount)) {
            warningMessage = "Target amount is not valid";
        }
        else if(!Donation.isDescriptionValid(description)) {
            warningMessage = "Description cannot be empty";
        }
        else if(!Donation.isPhoneValid(phone)) {
            warningMessage = "Phone number is not valid";
        }
        else if(!Donation.isFinishedDateValid(finishedDate)) {
            warningMessage = "Finish date is not valid";
        }
        else {
            Integer actualTargetAmount = Integer.parseInt(targetAmount);

            if(actualTargetAmount < 500000) {
                warningMessage = "Target amount minimum is Rp 500.000,-";

                Toast.makeText(requireContext(), warningMessage, Toast.LENGTH_LONG).show();
                return newDonation;
            }

            newDonation.setTitle(title);
            newDonation.setLink(link);
            newDonation.setTargetAmount(actualTargetAmount);
            newDonation.setGatheredAmount(gatheredAmount);
            newDonation.setDescription(description);
            newDonation.setFinishedDate(finishedDate);
            newDonation.setCreatedDate(createdDate);
            newDonation.setPhone(phone);

            gotoIntroductionDataFragment();

            return newDonation;
        }

        Toast.makeText(requireContext(), warningMessage, Toast.LENGTH_LONG).show();
        return newDonation;
    }
}