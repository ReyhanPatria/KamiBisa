package com.example.kamibisa.ui.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.kamibisa.R;
import com.example.kamibisa.data.model.Donation;
import com.example.kamibisa.ui.view.fragment.createDonation.BeneficiaryDataFragment;
import com.example.kamibisa.ui.view.fragment.createDonation.DonationConfirmationFragment;
import com.example.kamibisa.ui.view.fragment.createDonation.DonationDataFragment;
import com.example.kamibisa.ui.view.fragment.createDonation.DonationSuccessFragment;
import com.example.kamibisa.ui.view.fragment.createDonation.IntroductionDataFragment;
import com.example.kamibisa.ui.view.fragment.createDonation.PersonalDataFragment;
import com.example.kamibisa.ui.viewmodel.CreateDonationViewModel;
import com.example.kamibisa.ui.viewmodel.RegisterViewModel;
import com.example.kamibisa.ui.viewmodel.factory.CreateDonationViewModelFactory;
import com.example.kamibisa.ui.viewmodel.factory.RegisterViewModelFactory;
import com.example.kamibisa.utils.InjectionUtilities;

public class CreateDonationActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "CreateDonationActivity";

    private CreateDonationViewModel createDonationViewModel;

    private SearchView searchView;

    private FrameLayout createDonationFrameLayout;

    private Fragment personalDataFragment;
    private Fragment beneficiaryDataFragment;
    private Fragment donationDataFragment;
    private Fragment introductionDataFragment;
    private Fragment confirmationFragment;

    private TextView cancelButton;

    private ProgressBar progressBarCreateDonation;

    private Donation newDonation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_donation);

        initializeViewModel();
        observeVariables();
        initializeUi();
        setOnClickListeners();
        setOnQueryListeners();

        newDonation = new Donation();

        changeMenu(personalDataFragment, false);
    }

    public void initializeUi() {
        this.searchView = this.findViewById(R.id.search_bar);

        this.createDonationFrameLayout = this.findViewById(R.id.fl_createDonation_fragment);

        this.personalDataFragment = PersonalDataFragment.newInstance();
        this.beneficiaryDataFragment = BeneficiaryDataFragment.newInstance();
        this.donationDataFragment = DonationDataFragment.newInstance();
        this.introductionDataFragment = IntroductionDataFragment.newInstance();
        this.confirmationFragment = DonationConfirmationFragment.newInstance();

        this.cancelButton = this.findViewById(R.id.tv_createDonation_cancel);

        this.progressBarCreateDonation = this.findViewById(R.id.pb_createDonation);
    }

    private void initializeViewModel() {
        CreateDonationViewModelFactory factory = InjectionUtilities.getInstance()
                .provideCreateDonationViewModelFactory();
        createDonationViewModel = new ViewModelProvider(this, factory)
                .get(CreateDonationViewModel.class);
    }

    private void observeVariables() {
        createDonationViewModel.getIsUpdating().observe(this, new Observer<Boolean>() {
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

        createDonationViewModel.getIsCreateDonationDone().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean) {
                    gotoSuccessFragment();
                }
            }
        });
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

    public void insertDonation() {
        createDonationViewModel.insertDonation(newDonation);
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

    private void setOnQueryListeners() {
        this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent newIntent = new Intent(getApplicationContext(), SearchResultActivity.class);
                newIntent.putExtra("query", query);
                startActivity(newIntent);
                finish();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void gotoSuccessFragment() {
        this.cancelButton.setVisibility(View.GONE);

        FragmentManager fm = this.getSupportFragmentManager();
        for(Integer i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }

        String link = newDonation.getLink();
        changeMenu(DonationSuccessFragment.newInstance(link), Boolean.FALSE);
    }

    public void showProgressBar() {
        progressBarCreateDonation.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void hideProgressBar() {
        progressBarCreateDonation.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
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

    public Fragment getIntroductionDataFragment() {
        return introductionDataFragment;
    }

    public Fragment getConfirmationFragment() {
        return confirmationFragment;
    }

    public Donation getNewDonation() {
        return newDonation;
    }
}