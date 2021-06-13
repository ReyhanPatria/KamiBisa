package com.example.kamibisa.ui.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.kamibisa.R;
import com.example.kamibisa.ui.view.fragment.createDonation.BeneficiaryDataFragment;
import com.example.kamibisa.ui.view.fragment.createDonation.DonationDataFragment;
import com.example.kamibisa.ui.view.fragment.createDonation.PersonalDataFragment;

public class CreateDonationActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "CreateDonationActivity";

    private FrameLayout createDonationFrameLayout;

    private Fragment personalDataFragment;
    private Fragment beneficiaryDataFragment;
    private Fragment donationDataFragment;

    private TextView cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_donation);
        initializeUi();
        setOnClickListeners();

        changeMenu(personalDataFragment, false);
    }

    public void initializeUi() {
        this.createDonationFrameLayout = this.findViewById(R.id.fl_createDonation_fragment);

        this.personalDataFragment = PersonalDataFragment.newInstance();
        this.beneficiaryDataFragment = BeneficiaryDataFragment.newInstance();
        this.donationDataFragment = DonationDataFragment.newInstance();

        this.cancelButton = this.findViewById(R.id.tv_createDonation_cancel);
    }

    public void changeMenu(Fragment newMenu, Boolean addToBackStack) {
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction()
                .replace(createDonationFrameLayout.getId(), newMenu);

        if(addToBackStack) {
            transaction.addToBackStack(newMenu.getClass().getName());
        }

        Log.d(TAG, "Changing into " + newMenu.getClass().getName());

        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == this.cancelButton.getId()) {
            this.finish();
        }
    }

    public void setOnClickListeners() {
        this.cancelButton.setOnClickListener(this);
    }

    public Fragment getPersonalDataFragment() {
        return personalDataFragment;
    }

    public Fragment getBeneficiaryDataFragment() {
        return beneficiaryDataFragment;
    }

    public Fragment getDonationDataFragment() {
        return donationDataFragment;
    }
}