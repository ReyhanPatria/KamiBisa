package com.example.kamibisa.ui.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.kamibisa.R;
import com.example.kamibisa.data.model.User;
import com.example.kamibisa.ui.viewmodel.EditProfileViewModel;
import com.example.kamibisa.ui.viewmodel.factory.EditProfileViewModelFactory;
import com.example.kamibisa.utils.InjectionUtilities;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "EditProfileActivity";

    private EditProfileViewModel editProfileViewModel;

    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private EditText dobEditText;
    private EditText passwordEditText;

    private ImageButton backButton;

    private MaterialButton applyButton;

    private TextView resetTextView;

    private MaterialDatePicker dobPicker;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        initializeViewModel();
        observeVariables();
        initializeUi();
        setOnClickListeners();

        editProfileViewModel.loadUserData();
    }

    private void initializeViewModel() {
        EditProfileViewModelFactory factory = InjectionUtilities.getInstance()
                .provideEditProfileViewModelFactory();
        editProfileViewModel = new ViewModelProvider(this, factory)
                .get(EditProfileViewModel.class);
    }

    private void observeVariables() {
        editProfileViewModel.getIsUpdating()
                .observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if(aBoolean) {
                            showProgressBar();
                        }
                        else {
                            hideProgressBar();
                        }
                    }
                });

        editProfileViewModel.getIsFinished()
                .observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if(aBoolean) {
                            finish();
                        }
                    }
                });

        editProfileViewModel.getUserData()
                .observe(this, new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        if(user != null) {
                            setPageData(user);
                        }
                    }
                });
    }

    private void initializeUi() {
        this.nameEditText = this.findViewById(R.id.edt_editProfile_name);
        this.phoneEditText = this.findViewById(R.id.edt_editProfile_phone);
        this.emailEditText = this.findViewById(R.id.edt_editProfile_email);
        this.dobEditText = this.findViewById(R.id.edt_editProfile_dob);
        this.passwordEditText = this.findViewById(R.id.edt_editProfile_password);

        this.backButton = this.findViewById(R.id.btn_editProfile_back);

        this.applyButton = this.findViewById(R.id.btn_editProfile_apply);

        this.resetTextView = this.findViewById(R.id.tv_editProfile_reset);

        this.progressBar = this.findViewById(R.id.pb_editProfile);

        // Create dob picker
        dobPicker = createDatePicker();
    }

    private void setOnClickListeners() {
        this.dobEditText.setOnClickListener(this);
        this.backButton.setOnClickListener(this);
        this.applyButton.setOnClickListener(this);
        this.resetTextView.setOnClickListener(this);
    }

    @Override
    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.edt_editProfile_dob:
                this.dobPicker.show(
                        this.getSupportFragmentManager(),
                        dobPicker.toString()
                );
                break;

            case R.id.btn_editProfile_back:
                this.finish();
                break;

            case R.id.btn_editProfile_apply:
                this.updateUserData();
                break;

            case R.id.tv_editProfile_reset:
                this.editProfileViewModel.loadUserData();
                break;

            default:
                break;
        }
    }

    private void setPageData(User userData) {
        // Get user data
        String name = userData.getUsername();
        String phone = userData.getPhone();
        String email = userData.getEmail();
        String dob = DateFormat.getDateInstance(DateFormat.MEDIUM)
                .format(userData.getDob()).toString();

        this.nameEditText.setText(name);
        this.phoneEditText.setText(phone);
        this.emailEditText.setText(email);
        this.dobEditText.setText(dob);
    }

    private void updateUserData() {
        // Get user data
        String name = nameEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String email = emailEditText.getText().toString();
        Date dob = Date.from(Instant.now());
        String newPassword = passwordEditText.getText().toString();

        // Try to parse dob into Date object
        try {
            dob = DateFormat.getDateInstance(DateFormat.MEDIUM)
                    .parse(dobEditText.getText().toString());
        }
        catch(ParseException e) {
            Log.e(TAG, e.getMessage());
        }

        String warningMessage = "";

        if(!User.isNameValid(name)) {
            warningMessage = "User's name cannot be empty";
        }
        else if(!User.validatePhone(phone)) {
            warningMessage = "Phone number is not valid";
        }
        else if(!User.validateEmail(email)) {
            warningMessage = "Email is not valid";
        }
        else if(!User.validateDob(dob)) {
            warningMessage = "Date of birth is not valid";
        }
        else if(!newPassword.isEmpty() &&
                (!User.validatePasswordHasDigit(newPassword) || !User.validatePasswordLength(newPassword))) {
            warningMessage = "Password must be 10 - 14 characters and includes a number";
        }
        else {
            // Create new user object
            User newUserData = new User(name, email, dob, phone);
            newUserData.setFirebaseUser(FirebaseAuth.getInstance().getCurrentUser());
            newUserData.setId(FirebaseAuth.getInstance().getCurrentUser().getUid());

            editProfileViewModel.updateUserData(newUserData, newPassword);

            return;
        }

        Toast.makeText(this, warningMessage, Toast.LENGTH_SHORT).show();
    }

    private MaterialDatePicker createDatePicker() {
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

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}