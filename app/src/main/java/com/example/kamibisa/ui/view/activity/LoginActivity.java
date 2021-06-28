package com.example.kamibisa.ui.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.kamibisa.R;
import com.example.kamibisa.ui.viewmodel.LoginViewModel;
import com.example.kamibisa.ui.viewmodel.factory.LoginViewModelFactory;
import com.example.kamibisa.utils.InjectionUtilities;
import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static String TAG = "LoginActivity";

    private LoginViewModel loginViewModel;

    private EditText emailEditText;
    private EditText passwordEditText;

    private ImageButton backButton;

    private MaterialButton loginButton;

    private TextView registerTextView;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeUi();
        setOnClickListeners();
        initializeViewModel();
        observeVariables();
    }

    private void initializeUi() {
        this.emailEditText = this.findViewById(R.id.edt_login_email);
        this.passwordEditText = this.findViewById(R.id.edt_login_password);

        this.backButton = this.findViewById(R.id.btn_login_back);
        this.loginButton = this.findViewById(R.id.btn_login_login);

        this.registerTextView = this.findViewById(R.id.tv_login_register);

        this.progressBar = this.findViewById(R.id.pb_login);
    }

    private void setOnClickListeners() {
        backButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    }

    private void initializeViewModel() {
        LoginViewModelFactory factory = InjectionUtilities.getInstance().provideLoginViewModelFactory();
        loginViewModel = new ViewModelProvider(this, factory).get(LoginViewModel.class);
    }

    private void observeVariables() {
        loginViewModel.getIsUpdating().observe(this, new Observer<Boolean>() {
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

        loginViewModel.getIsLoginCompleted().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean) {
                    Intent newIntent = new Intent(getApplicationContext(), HomeActivity.class);
                    newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(newIntent);
                    finish();
                }
            }
        });
    }

    @Override
    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_login_back:
                this.finish();
                break;

            case R.id.btn_login_login:
                login();
                break;

            case R.id.tv_login_register:
                gotoRegisterActivity();

            default:
                break;
        }
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

    public void login() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if(email.isEmpty()) {
            emailEditText.setError("Email cannot be empty");
        }
        if(password.isEmpty()) {
            passwordEditText.setError("Password cannot be empty");
        }

        if(!email.isEmpty() && !password.isEmpty()) {
            loginViewModel.login(email, password);
        }
    }

    private void gotoRegisterActivity() {
        Intent newIntent = new Intent(this, RegisterActivity.class);
        this.startActivity(newIntent);
        this.finish();
    }
}