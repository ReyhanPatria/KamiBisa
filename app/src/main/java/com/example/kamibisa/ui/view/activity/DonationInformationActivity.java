package com.example.kamibisa.ui.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.BulletSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.kamibisa.R;
import com.google.android.material.button.MaterialButton;

import java.lang.reflect.Array;

public class DonationInformationActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "DonationInformationActivity";

    private ImageButton backButton;

    private MaterialButton finishButton;

    private TextView informationListTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_information);

        initializeUi();
        setOnClickListeners();
    }

    private void initializeUi() {
        this.backButton = this.findViewById(R.id.btn_donationInformation_back);
        this.finishButton = this.findViewById(R.id.btn_donationInformation_finish);

        this.informationListTextView = this.findViewById(R.id.tv_donationInformation_list);

        // Set ordered list
        String[] donationInformation = getResources().getStringArray(R.array.donation_information);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        for(String s: donationInformation) {
            builder.append(s + "\n\n", new BulletSpan(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        this.informationListTextView.setText(builder);
    }

    private void setOnClickListeners() {
        this.backButton.setOnClickListener(this);
        this.finishButton.setOnClickListener(this);
    }

    @Override
    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_donationInformation_back:
            case R.id.btn_donationInformation_finish:
                this.finish();
                break;

            default:
                break;
        }
    }
}