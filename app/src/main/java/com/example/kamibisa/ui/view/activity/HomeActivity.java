package com.example.kamibisa.ui.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.kamibisa.R;
import com.example.kamibisa.data.database.Database;
import com.example.kamibisa.data.database.dao.UserDao;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        // TODO
        // Show current user's email for testing purposes
        TextView centerText = findViewById(R.id.tv_center);
        centerText.setText(
                Database.getInstance()
                        .getUserDao()
                        .getFirebaseAuth()
                        .getCurrentUser()
                        .getEmail()
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // TODO
        // Signs out user for testing purposes
        Database.getInstance().getUserDao().getFirebaseAuth().signOut();
    }
}