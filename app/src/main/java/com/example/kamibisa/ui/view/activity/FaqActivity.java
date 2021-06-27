package com.example.kamibisa.ui.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.kamibisa.R;

public class FaqActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "FaqActivity";

    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        initializeUi();
        setOnClickListeners();
    }

    private void initializeUi() {
        this.backButton = this.findViewById(R.id.btn_faq_back);
    }

    private void setOnClickListeners() {
        this.backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == this.backButton.getId()) {
            this.finish();
        }
    }
}