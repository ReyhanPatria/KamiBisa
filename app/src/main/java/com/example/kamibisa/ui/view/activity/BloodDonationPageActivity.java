package com.example.kamibisa.ui.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kamibisa.R;
import com.example.kamibisa.data.model.BloodDonation;
import com.example.kamibisa.data.model.Donation;
import com.example.kamibisa.ui.viewmodel.BloodDonationPageViewModel;
import com.example.kamibisa.ui.viewmodel.factory.BloodDonationPageViewModelFactory;
import com.example.kamibisa.utils.InjectionUtilities;

import java.text.DateFormat;

public class BloodDonationPageActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "BloodDonationActivity";

    private BloodDonationPageViewModel bloodDonationPageViewModel;

    private TextView beneficiaryNameTextView;
    private TextView bloodTypeTextView;
    private TextView finishedTextView;
    private TextView creatorNameTextView;
    private TextView phoneTextView;
    private TextView locationTextView;

    private ImageButton backButton;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_donation_page);

        initializeViewModel();
        observeVariables();
        initializeUi();
        setOnClickListeners();

        String id = getIntent().getStringExtra("bloodDonationId");
        bloodDonationPageViewModel.loadBloodDonationData(id);
    }

    private void initializeViewModel() {
        BloodDonationPageViewModelFactory factory = InjectionUtilities.getInstance()
                .provideBloodDonationPageViewModelFactory();
        bloodDonationPageViewModel = new ViewModelProvider(this, factory)
                .get(BloodDonationPageViewModel.class);
    }

    private void observeVariables() {
        bloodDonationPageViewModel.getIsUpdating().observe(this, new Observer<Boolean>() {
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

        bloodDonationPageViewModel.getBloodDonationData().observe(this, new Observer<BloodDonation>() {
            @Override
            public void onChanged(BloodDonation bloodDonation) {
                if(bloodDonation != null) {
                    setPageData(bloodDonation);
                }
            }
        });
    }

    private void initializeUi() {
        this.beneficiaryNameTextView = this.findViewById(R.id.tv_bloodDonation_beneficiaryName);
        this.bloodTypeTextView = this.findViewById(R.id.tv_bloodDonation_bloodType);
        this.finishedTextView = this.findViewById(R.id.tv_bloodDonation_finishedDate);
        this.creatorNameTextView = this.findViewById(R.id.tv_bloodDonation_creatorName);
        this.phoneTextView = this.findViewById(R.id.tv_bloodDonation_phone);
        this.locationTextView = this.findViewById(R.id.tv_bloodDonation_location);

        this.backButton = this.findViewById(R.id.btn_bloodDonation_back);

        this.progressBar = this.findViewById(R.id.pb_bloodDonation);
    }

    private void setOnClickListeners() {
        this.backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == backButton.getId()) {
            this.finish();
        }
    }

    private void setPageData(BloodDonation bloodDonation) {
        String beneficiaryName = bloodDonation.getBeneficiaryName();
        String bloodType = bloodDonation.getBeneficiaryBloodType();
        String finishedDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(bloodDonation.getFinishedDate()).toString();
        String creatorName = bloodDonation.getCreatorName();
        String phone = bloodDonation.getPhone();
        String location = bloodDonation.getLocation();


        this.beneficiaryNameTextView.setText(beneficiaryName);
        this.bloodTypeTextView.setText(bloodType);
        this.finishedTextView.setText(finishedDate);
        this.creatorNameTextView.setText(creatorName);
        this.phoneTextView.setText(phone);
        this.locationTextView.setText(location);
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