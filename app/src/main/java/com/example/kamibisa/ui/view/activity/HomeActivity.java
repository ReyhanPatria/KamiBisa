package com.example.kamibisa.ui.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.kamibisa.R;
import com.example.kamibisa.data.database.Database;
import com.example.kamibisa.ui.view.fragment.BloodDonationFragment;
import com.example.kamibisa.ui.view.fragment.CreateDonationFragment;
import com.example.kamibisa.ui.view.fragment.HistoryFragment;
import com.example.kamibisa.ui.view.fragment.HomeFragment;
import com.example.kamibisa.ui.view.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    private EditText searchEditText;
    private BottomNavigationView menuNavigationBar;

    private Fragment homeFragment;
    private Fragment createDonationFragment;
    private Fragment historyFragment;
    private Fragment bloodDonationFragment;
    private Fragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initializeUi();
        setOnClickListeners();

        // Set shown fragment when first created
        menuNavigationBar.setSelectedItemId(R.id.ic_create_donation_menu);
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
    }

    private void setOnClickListeners() {
        menuNavigationBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            @SuppressLint("NonConstantResourceId")
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Boolean returnValue;
                Fragment targetMenu;

                switch(item.getItemId()) {
                    // TODO: Test HomeFragment
                    // Suspended because HomeFragment is not ready
//                    case R.id.ic_home_menu:
//                        targetMenu = homeFragment;
//                        returnValue = true;
//                        break;

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
}