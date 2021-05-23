package com.example.kamibisa.ui.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kamibisa.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class LandingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        // If a user is already logged in, switch to HomeActivity
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent newIntent = new Intent(this, HomeActivity.class);
            newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(newIntent);
            finish();
        }
        
        MaterialButton registerButton = this.findViewById(R.id.btn_landing_register);
        registerButton.setOnClickListener(v ->
                startActivity(new Intent(LandingActivity.this, RegisterActivity.class)));

        TextView loginLink = this.findViewById(R.id.tv_landing_login);
        loginLink.setOnClickListener(v ->
                startActivity(new Intent(LandingActivity.this, LoginActivity.class)));
    }
}