package com.example.kamibisa.ui.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kamibisa.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class LandingActivity extends AppCompatActivity implements View.OnClickListener {
    private static String TAG = "LandingActivity";

    private MaterialButton registerButton;
    private TextView loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        initializeUiWidgets();
        setOnClickListeners();

        // If a user is already logged in, switch to HomeActivity
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent newIntent = new Intent(this, HomeActivity.class);
            newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(newIntent);
            finish();
        }
    }

    private void initializeUiWidgets() {
        this.registerButton = this.findViewById(R.id.btn_landing_register);
        this.loginLink = this.findViewById(R.id.tv_landing_login);
    }

    private void setOnClickListeners() {
        registerButton.setOnClickListener(this);
        loginLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_landing_register:
                // Start register activity
                startActivity(new Intent(LandingActivity.this, RegisterActivity.class));
                break;

            case R.id.tv_landing_login:
                // Start login activity
                startActivity(new Intent(LandingActivity.this, LoginActivity.class));
                break;

            default:
                break;
        }
    }
}