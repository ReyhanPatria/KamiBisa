package com.example.kamibisa.ui.view.fragment.createDonation;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kamibisa.R;
import com.example.kamibisa.data.model.Donation;
import com.example.kamibisa.ui.view.activity.CreateDonationActivity;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class PersonalDataFragment extends Fragment implements View.OnClickListener {
    private static String TAG = "PersonalDataFragment";

    private View rootView;

    private EditText creatorNameEditText;
    private EditText institutionEditText;
    private EditText socialMediaEditText;
    private EditText locationEditText;
    private EditText creatorDescriptionEditText;

    private Spinner jobSpinner;

    private MaterialButton nextButton;

    public PersonalDataFragment() {
        // Required empty public constructor
    }

    public static PersonalDataFragment newInstance() {
        PersonalDataFragment fragment = new PersonalDataFragment();
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
        rootView = inflater.inflate(R.layout.fragment_personal_data, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeUi();
        setOnTouchListeners();
        setOnClickListeners();
    }



    public void initializeUi() {
        this.creatorNameEditText = rootView.findViewById(R.id.edt_personalData_fullname);
        this.institutionEditText = rootView.findViewById(R.id.edt_personalData_institution);
        this.socialMediaEditText = rootView.findViewById(R.id.edt_personalData_socialMedia);
        this.locationEditText = rootView.findViewById(R.id.edt_personalData_location);
        this.creatorDescriptionEditText = rootView.findViewById(R.id.edt_personalData_userDescription);

        this.jobSpinner = rootView.findViewById(R.id.spin_personalData_job);

        this.nextButton = rootView.findViewById(R.id.btn_personalData_next);

        // Fill job spinner with options
        ArrayAdapter<CharSequence> jobSpinnerAdapter = ArrayAdapter
                .createFromResource(this.requireContext(),
                        R.array.jobs, R.layout.custom_spinner_item);
        jobSpinnerAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        jobSpinner.setAdapter(jobSpinnerAdapter);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setOnTouchListeners() {
        // This onTouchListener is used for allowing EditText to scroll while touched
        // This is necessary because if this isn't implemented
        // then ScrollView will take scroll priority
        View.OnTouchListener touchScrollPriority = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(socialMediaEditText.hasFocus()) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);

                    if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_SCROLL) {
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }

                return false;
            }
        };

        this.socialMediaEditText.setOnTouchListener(touchScrollPriority);
        this.creatorDescriptionEditText.setOnTouchListener(touchScrollPriority);
    }

    public void setOnClickListeners() {
        this.nextButton.setOnClickListener(this);
    }

    @Override
    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {
        if(v.getId() == this.nextButton.getId()) {
            setDonationPersonalData();
        }
    }

    private void gotoBeneficiaryDataFragment() {
        Fragment f = ((CreateDonationActivity) this.requireActivity()).getBeneficiaryDataFragment();
        ((CreateDonationActivity) this.requireActivity()).changeMenu(f, Boolean.TRUE);
    }

    public Donation setDonationPersonalData() {
        Donation newDonation = ((CreateDonationActivity) this.requireActivity()).getNewDonation();

        String creatorId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String creatorName = creatorNameEditText.getText().toString();
        String institution = institutionEditText.getText().toString();
        String socialMedia = socialMediaEditText.getText().toString();
        String location = locationEditText.getText().toString();
        String creatorDescription = creatorDescriptionEditText.getText().toString();

        String warningMessage = "";

        if(!Donation.isCreatorNameValid(creatorName)) {
            warningMessage = "Name cannot be empty";
        }
        else if(!Donation.isInstitutionValid(institution)) {
            warningMessage = "Institution cannot be empty";
        }
        else if(!Donation.isSocialMediaValid(socialMedia)) {
            warningMessage = "Social media cannot be empty";
        }
        else if(!Donation.isLocationValid(location)) {
            warningMessage = "Location cannot be empty";
        }
        else if(!Donation.isCreatorDescriptionValid(creatorDescription)) {
            warningMessage = "Creator description cannot be empty";
        }
        else {
            newDonation.setCreatorId(creatorId);
            newDonation.setCreatorName(creatorName);
            newDonation.setInstitution(institution);
            newDonation.setSocialMedia(socialMedia);
            newDonation.setLocation(location);
            newDonation.setCreatorDescription(creatorDescription);

            gotoBeneficiaryDataFragment();

            return newDonation;
        }

        Toast.makeText(requireContext(), warningMessage, Toast.LENGTH_LONG).show();
        return newDonation;
    }
}