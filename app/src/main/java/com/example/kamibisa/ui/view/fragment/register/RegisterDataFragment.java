package com.example.kamibisa.ui.view.fragment.register;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import com.example.kamibisa.R;
import com.example.kamibisa.data.model.User;
import com.example.kamibisa.ui.view.activity.RegisterActivity;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterDataFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "RegisterDataFragment";

    private View rootView;

    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText dobEditText;
    private EditText phoneEditText;
    private Button nextButton;
    private ImageButton backButton;

    private MaterialDatePicker dobPicker;

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
        setOnClickListeners();
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
        dobPicker = createDobPicker();
    }

    private void setOnClickListeners() {
        nextButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        dobEditText.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_register_back:
                this.requireActivity().finish();
                break;

            case R.id.btn_register_next:
                setUserData();
                break;

            case R.id.edt_register_dob:
                dobPicker.show(
                        requireActivity().getSupportFragmentManager(),
                        dobPicker.toString()
                );
                break;

            default:
                break;
        }
    }

    private void setUserData() {
        // Get user data
        String username = usernameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        Date dob = Date.from(Instant.now());
        String phone = phoneEditText.getText().toString();

        Log.d(TAG, dob.toString());

        // Try to parse dob into Date object
        try {
            dob = DateFormat.getDateInstance(DateFormat.MEDIUM)
                    .parse(dobEditText.getText().toString());
        }
        catch(ParseException e) {
            Log.e(TAG, e.getMessage());
        }

        Log.d(TAG, dob.toString());

        String warningMessage = "";

        if(!User.validateEmail(email)) {
            warningMessage = "Email is not valid";
        }
        else if(!User.validateDob(dob)) {
            warningMessage = "Date of birth is not valid";
        }
        else if(!User.validatePhone(phone)) {
            warningMessage = "Phone number is not valid";
        }
        else {
            // Create new user object
            User newUser = new User(username, email, dob, phone);

            // Saves user data into RegisterActivity
            ((RegisterActivity) this.requireActivity()).setNewUser(newUser);

            // Switch to RegisterPasswordActivity
            this.requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_register_fragment,
                            ((RegisterActivity) requireActivity()).getRegisterPasswordFragment())
                    .addToBackStack(this.getClass().getName())
                    .commit();

            return;
        }

        Toast.makeText(requireContext(), warningMessage, Toast.LENGTH_SHORT).show();
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