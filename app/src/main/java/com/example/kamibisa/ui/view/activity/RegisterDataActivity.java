package com.example.kamibisa.ui.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.kamibisa.R;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.MaterialDatePicker;

public class RegisterDataActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText dobEditText;
    private EditText phoneEditText;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_data);

        initViewWidgets();
    }

    private void initViewWidgets() {
        // Gets all widgets
        usernameEditText = this.findViewById(R.id.edt_register_username);
        emailEditText = this.findViewById(R.id.edt_register_email);
        dobEditText = this.findViewById(R.id.edt_register_dob);
        phoneEditText = this.findViewById(R.id.edt_register_phone);
        nextButton = this.findViewById(R.id.btn_register_next);

        // Create dob picker
        MaterialDatePicker dobPicker = createDobPicker();

        // Set OnClickListeners
        // Set date picker to show up on click
        dobEditText.setOnClickListener(v -> {
            dobPicker.show(getSupportFragmentManager(), dobPicker.toString());
        });

        // Set next button to go to RegisterPasswordActivity
        nextButton.setOnClickListener(v -> {
            Intent i = new Intent(RegisterDataActivity.this, RegisterPasswordActivity.class);
            startActivity(i);
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