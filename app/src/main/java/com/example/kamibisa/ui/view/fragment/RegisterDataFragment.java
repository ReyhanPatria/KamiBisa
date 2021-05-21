package com.example.kamibisa.ui.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private View rootView;

    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText dobEditText;
    private EditText phoneEditText;
    private Button nextButton;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterDataFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterDataFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterDataFragment newInstance(String param1, String param2) {
        RegisterDataFragment fragment = new RegisterDataFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

        initViewWidgets();
    }

    private void initViewWidgets() {
        // Gets all widgets
        usernameEditText = rootView.findViewById(R.id.edt_register_username);
        emailEditText = rootView.findViewById(R.id.edt_register_email);
        dobEditText = rootView.findViewById(R.id.edt_register_dob);
        phoneEditText = rootView.findViewById(R.id.edt_register_phone);
        nextButton = rootView.findViewById(R.id.btn_register_next);

        // Create dob picker
        MaterialDatePicker dobPicker = createDobPicker();

        // Set OnClickListeners
        // Set date picker to show up on click
        dobEditText.setOnClickListener(v -> {
            dobPicker.show(getActivity().getSupportFragmentManager(), dobPicker.toString());
        });

        // Set next button to go to RegisterPasswordActivity
        nextButton.setOnClickListener(v -> {
            // TODO: Move to register password fragment
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_fragment, ((RegisterActivity) getActivity()).getRegisterPasswordFragment())
                    .addToBackStack("registerData")
                    .commit();
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