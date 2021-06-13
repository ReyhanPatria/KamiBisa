package com.example.kamibisa.ui.view.fragment.createDonation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.kamibisa.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;

public class DonationDataFragment extends Fragment implements View.OnClickListener {
    private static String TAG = "DonationDataFragment";

    private View rootView;

    private EditText titleEditText;
    private EditText linkEditText;
    private EditText targetAmountEditText;
    private EditText descriptionEditText;
    private EditText endDateEditText;
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
        this.endDateEditText = rootView.findViewById(R.id.edt_donationData_endDate);
        this.phoneEditText = rootView.findViewById(R.id.edt_donationData_phone);

        this.nextButton = rootView.findViewById(R.id.btn_donationData_next);
        this.backButton = rootView.findViewById(R.id.btn_donationData_back);

        this.endDatePicker = createDatePicker();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_donationData_next:
                // TODO gotoDonationPictureFragment()
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
        this.endDateEditText.setOnClickListener(this);
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
        datePicker.addOnPositiveButtonClickListener(v -> endDateEditText.setText(datePicker.getHeaderText()));

        return datePicker;
    }
}