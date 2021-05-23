package com.example.kamibisa.ui.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kamibisa.R;
import com.google.android.material.button.MaterialButton;

public class LandingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        MaterialButton signInButton = this.findViewById(R.id.btn_landing_sign_in);
        signInButton.setOnClickListener(v -> {
            startActivity(new Intent(LandingActivity.this, RegisterActivity.class));
            finish();
        });

        TextView loginLink = this.findViewById(R.id.tv_landing_login);
        loginLink.setOnClickListener(v -> {
            startActivity(new Intent(LandingActivity.this, LoginActivity.class));
            finish();
        });
    }
}