package com.example.kamibisa.ui.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.kamibisa.R;
import com.example.kamibisa.data.database.Database;
import com.example.kamibisa.ui.view.fragment.home.BloodDonationFragment;
import com.example.kamibisa.ui.view.fragment.home.CreateDonationFragment;
import com.example.kamibisa.ui.view.fragment.home.HistoryFragment;
import com.example.kamibisa.ui.view.fragment.home.HomeFragment;
import com.example.kamibisa.ui.view.fragment.home.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    private EditText searchEditText;
    private BottomNavigationView menuNavigationBar;

    private Fragment homeFragment;
    private Fragment createDonationFragment;
    private Fragment historyFragment;
    private Fragment bloodDonationFragment;
    private Fragment profileFragment;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeUi();
        setOnClickListeners();

        // Set shown fragment when first created
        changeMenu(homeFragment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // TODO: Do checks on keep user logged in or not
        // Signs out user for testing purposes
        Database.getInstance().getUserDao().getFirebaseAuth().signOut();
    }

    private void initializeUi() {
        this.searchEditText = this.findViewById(R.id.edt_home_search);
        this.menuNavigationBar = this.findViewById(R.id.nbr_home_menu);

        this.homeFragment = HomeFragment.newInstance();
        this.createDonationFragment = CreateDonationFragment.newInstance();
        this.historyFragment = HistoryFragment.newInstance();
        this.bloodDonationFragment = BloodDonationFragment.newInstance();
        this.profileFragment = ProfileFragment.newInstance();

        this.progressBar = this.findViewById(R.id.pb_home);
    }

    private void setOnClickListeners() {
        menuNavigationBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            @SuppressLint("NonConstantResourceId")
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Boolean returnValue;
                Fragment targetMenu;

                switch(item.getItemId()) {
                    case R.id.ic_home_menu:
                        targetMenu = homeFragment;
                        returnValue = true;
                        break;

                    case R.id.ic_create_donation_menu:
                        targetMenu = createDonationFragment;
                        returnValue = true;
                        break;

                    case R.id.ic_history_menu:
                        targetMenu = historyFragment;
                        returnValue = true;
                        break;

                    case R.id.ic_blood_donation_menu:
                        targetMenu = bloodDonationFragment;
                        returnValue = true;
                        break;

                    case R.id.ic_profile_menu:
                        targetMenu = profileFragment;
                        returnValue = true;
                        break;

                    default:
                        targetMenu = homeFragment;
                        returnValue = false;
                        break;
                }

                changeMenu(targetMenu);
                return returnValue;
            }
        });
    }

    private void changeMenu(Fragment newMenu) {
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_home_fragment, newMenu)
                .commit();
    }

    public void createCharity() {
        Intent newIntent = new Intent(getApplicationContext(), CreateDonationActivity.class);
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
}