package com.example.kamibisa.ui.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.kamibisa.R;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView centerText = findViewById(R.id.tv_center);
        centerText.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }
}