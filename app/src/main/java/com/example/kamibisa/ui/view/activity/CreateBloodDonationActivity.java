package com.example.kamibisa.ui.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kamibisa.R;
import com.example.kamibisa.data.model.BloodDonation;
import com.example.kamibisa.ui.view.fragment.createBloodDonation.BloodDonationBeneficiaryDataFragment;
import com.example.kamibisa.ui.view.fragment.createBloodDonation.BloodDonationConfirmationFragment;
import com.example.kamibisa.ui.view.fragment.createBloodDonation.BloodDonationCreatorDataFragment;
import com.example.kamibisa.ui.view.fragment.createBloodDonation.BloodDonationSuccessFragment;
import com.example.kamibisa.ui.viewmodel.CreateBloodDonationViewModel;
import com.example.kamibisa.ui.viewmodel.factory.CreateBloodDonationViewModelFactory;
import com.example.kamibisa.utils.InjectionUtilities;

public class CreateBloodDonationActivity extends AppCompatActivity implements View.OnClickListener {
    private static String TAG = "CreateBloodDonationActivity";

    private CreateBloodDonationViewModel createBloodDonationViewModel;

    private FrameLayout createBloodDonationFrameLayout;

    private Fragment creatorDataFragment;
    private Fragment beneficiaryDataFragment;
    private Fragment confirmationFragment;

    private TextView cancelButton;

    private ProgressBar progressBar;

    private BloodDonation newBloodDonation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_blood_donation);

        initializeViewModel();
        observeVariables();
        initializeUi();
        setOnClickListeners();

        this.newBloodDonation = new BloodDonation();

        changeMenu(getCreatorDataFragment(), Boolean.FALSE);
    }

    private void initializeUi() {
        this.createBloodDonationFrameLayout = this.findViewById(R.id.fl_createBloodDonation_fragment);

        this.creatorDataFragment = BloodDonationCreatorDataFragment.newInstance();
        this.beneficiaryDataFragment = BloodDonationBeneficiaryDataFragment.newInstance();
        this.confirmationFragment = BloodDonationConfirmationFragment.newInstance();

        this.cancelButton = this.findViewById(R.id.tv_createBloodDonation_cancel);

        this.progressBar = this.findViewById(R.id.pb_createBloodDonation);
    }

    private void initializeViewModel() {
        CreateBloodDonationViewModelFactory factory = InjectionUtilities.getInstance()
                .provideCreateBloodDonationViewModelFactory();
        this.createBloodDonationViewModel = new ViewModelProvider(this, factory)
                .get(CreateBloodDonationViewModel.class);
    }

    private void observeVariables() {
        createBloodDonationViewModel.getIsUpdating().observe(this, new Observer<Boolean>() {
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

        createBloodDonationViewModel.getIsInsertSuccessful().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean) {
                    gotoSuccessPage();
                }
            }
        });
    }

    private void setOnClickListeners() {
        this.cancelButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == cancelButton.getId()) {
            finish();
        }
    }

    public void changeMenu(Fragment newMenu, Boolean addToBackStack) {
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction()
                .replace(createBloodDonationFrameLayout.getId(), newMenu);

        if(addToBackStack) {
            transaction.addToBackStack(newMenu.getClass().getName());
        }

        Log.d(TAG, "Changing into " + newMenu.getClass().getName());

        transaction.commit();
    }

    public void insertBloodDonation() {
        createBloodDonationViewModel.insertBloodDonation(newBloodDonation);
    }

    public void gotoSuccessPage() {
        FragmentManager fm = this.getSupportFragmentManager();
        for(Integer i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();

            Log.d(TAG, "Popping backstack " + i);
        }

        String link = newBloodDonation.getLink();
        changeMenu(BloodDonationSuccessFragment.newInstance(link), Boolean.FALSE);
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

    public BloodDonation getNewBloodDonation() {
        return newBloodDonation;
    }

    public Fragment getCreatorDataFragment() {
        return creatorDataFragment;
    }

    public Fragment getBeneficiaryDataFragment() {
        return beneficiaryDataFragment;
    }

    public Fragment getConfirmationFragment() {
        return confirmationFragment;
    }
}