package com.example.kamibisa.ui.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kamibisa.R;
import com.example.kamibisa.data.model.Donation;
import com.example.kamibisa.ui.viewmodel.DonationViewModel;
import com.example.kamibisa.ui.viewmodel.factory.DonationViewModelFactory;
import com.example.kamibisa.utils.InjectionUtilities;
import com.google.android.material.button.MaterialButton;

import java.util.Locale;

public class DonationActivity extends AppCompatActivity implements View.OnClickListener {
    private static String TAG = "DonationActivity";

    private DonationViewModel donationViewModel;

    private ImageButton backButton;

    private MaterialButton donateButton;

    private TextView titleTextView;
    private TextView gatheredAmountTextView;
    private TextView targetAmountTextView;
    private TextView daysLeftTextView;
    private TextView creatorNameTextView;

    private EditText introductionEditText;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);

        initializeViewModel();
        observeVariables();
        initializeUi();
        setOnClickListeners();

        String donationId = this.getIntent().getStringExtra("donationId");
        donationViewModel.loadDonationData(donationId);
    }

    @Override
    protected void onResume() {
        super.onResume();

        String donationId = this.getIntent().getStringExtra("donationId");
        donationViewModel.loadDonationData(donationId);
    }

    private void initializeUi() {
        this.backButton = this.findViewById(R.id.btn_donation_back);

        this.titleTextView = this.findViewById(R.id.tv_bloodDonation_title);
        this.gatheredAmountTextView = this.findViewById(R.id.tv_donation_gatheredAmount);
        this.targetAmountTextView = this.findViewById(R.id.tv_donation_targetAmount);
        this.daysLeftTextView = this.findViewById(R.id.tv_donation_daysLeft);
        this.creatorNameTextView = this.findViewById(R.id.tv_donation_creatorName);

        this.introductionEditText = this.findViewById(R.id.edt_donation_introduction);

        this.donateButton = this.findViewById(R.id.btn_donation_donate);

        this.progressBar = this.findViewById(R.id.pb_donation);
    }

    private void initializeViewModel() {
        DonationViewModelFactory factory = InjectionUtilities.getInstance()
                .provideDonationViewModelFactory();
        donationViewModel = new ViewModelProvider(this, factory)
                .get(DonationViewModel.class);
    }

    private void observeVariables() {
        donationViewModel.getIsUpdating().observe(this, new Observer<Boolean>() {
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

        donationViewModel.getDonationData().observe(this, new Observer<Donation>() {
            @Override
            public void onChanged(Donation donation) {
                if(donation != null) {
                    setPageData(donation);
                }
                else {
                    Log.e(TAG, "Donation object is null");
                }
            }
        });
    }

    private void setPageData(Donation donation) {
        String title = donation.getTitle();
        String gatheredAmount = formatNumberToRupiah(donation.getGatheredAmount());
        String targetAmount = formatNumberToRupiah(donation.getTargetAmount());
        Long daysLeft = donation.getDaysLeft();
        String creatorName = donation.getCreatorName();
        String introduction = donation.getIntroduction();

        titleTextView.setText(title);
        gatheredAmountTextView.setText(String.valueOf(gatheredAmount));
        targetAmountTextView.setText(String.valueOf(targetAmount));
        daysLeftTextView.setText(String.valueOf(daysLeft));
        creatorNameTextView.setText(creatorName);
        introductionEditText.setText(introduction);
    }

    private void setOnClickListeners() {
        this.backButton.setOnClickListener(this);
        this.donateButton.setOnClickListener(this);
    }

    @Override
    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_donation_back:
                finish();
                break;

            case R.id.btn_donation_donate:
                gotoDonatePage();
                break;

            default:
                break;
        }
    }

    private void gotoDonatePage() {
        String donationId = this.getIntent().getStringExtra("donationId");

        Intent newIntent = new Intent(this, DonateActivity.class);
        newIntent.putExtra("donationId", donationId);
        startActivity(newIntent);
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

    public String formatNumberToRupiah(Integer number) {
        return String.format(Locale.US,"Rp %,d", number).replace(",", ".");
    }
}