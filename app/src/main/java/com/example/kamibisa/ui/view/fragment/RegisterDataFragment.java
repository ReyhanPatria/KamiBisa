package com.example.kamibisa.ui.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.kamibisa.R;
import com.example.kamibisa.ui.view.activity.RegisterActivity;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.MaterialDatePicker;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterDataFragment extends Fragment {

    public static String TAG = "RegisterDataFragment";

    private View rootView;

    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText dobEditText;
    private EditText phoneEditText;
    private Button nextButton;
    private ImageButton backButton;

    public RegisterDataFragment() {
        // Required empty public constructor
    }

    public static RegisterDataFragment newInstance() {
        RegisterDataFragment fragment = new RegisterDataFragment();
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
        rootView = inflater.inflate(R.layout.fragment_register_data, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViewWidgets();
    }

    private void initializeViewWidgets() {
        // Gets all widgets
        usernameEditText = rootView.findViewById(R.id.edt_register_username);
        emailEditText = rootView.findViewById(R.id.edt_register_email);
        dobEditText = rootView.findViewById(R.id.edt_register_dob);
        phoneEditText = rootView.findViewById(R.id.edt_register_phone);
        nextButton = rootView.findViewById(R.id.btn_register_next);
        backButton = rootView.findViewById(R.id.btn_register_back);

        // Create dob picker
        MaterialDatePicker dobPicker = createDobPicker();

        // Set OnClickListeners
        // Set date picker to show up on click
        dobEditText.setOnClickListener(v -> {
            dobPicker.show(getActivity().getSupportFragmentManager(), dobPicker.toString());
        });

        // Set next button to go to RegisterPasswordActivity
        nextButton.setOnClickListener(v -> {
            this.getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_fragment, ((RegisterActivity) getActivity()).getRegisterPasswordFragment())
                    .addToBackStack(this.getClass().getName())
                    .commit();

            Log.d(TAG, String.valueOf(getActivity().getSupportFragmentManager().getBackStackEntryCount()));
        });

        backButton.setOnClickListener(v -> {
            this.getActivity().finish();
        });
    }

    private MaterialDatePicker createDobPicker() {
        // Create DOB constraints
        CalendarConstraints dobConstraints = (new CalendarConstraints.Builder())
                .setValidator(DateValidatorPointBackward.now())
                .build();

        // Create date picker for choosing DOB
        MaterialDatePicker dobPicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Date of Birth")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(dobConstraints)
                .build();

        // If user confirms the date, set the date on the edit text
        dobPicker.addOnPositiveButtonClickListener(v -> dobEditText.setText(dobPicker.getHeaderText()));

        return dobPicker;
    }
}