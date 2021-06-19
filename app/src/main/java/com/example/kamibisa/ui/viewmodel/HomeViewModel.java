package com.example.kamibisa.ui.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kamibisa.data.model.Donation;
import com.example.kamibisa.data.repository.DonationRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private static final String TAG = "HomeViewModel";

    private static HomeViewModel instance;

    private DonationRepository donationRepository;

    // Add variables to be observed
    private MutableLiveData<Boolean> isUpdating;
    private MutableLiveData<List<Donation>> urgentDonationList;
    private MutableLiveData<List<Donation>> selectedDonationList;


    public HomeViewModel(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
        initializeVariables();
        updateDonationList();
    }

    public static HomeViewModel getInstance(DonationRepository donationRepository) {
        if(instance == null) {
            instance = new HomeViewModel(donationRepository);
        }
        return instance;
    }

    public void initializeVariables() {
        isUpdating = new MutableLiveData<Boolean>(Boolean.FALSE);
        urgentDonationList = new MutableLiveData<List<Donation>>(new ArrayList<Donation>());
        selectedDonationList = new MutableLiveData<List<Donation>>(new ArrayList<Donation>());
    }

    public void updateDonationList() {
        updateUrgentDonationList();
        updateSelectedDonationList();
    }

    public void updateUrgentDonationList() {
        isUpdating.setValue(Boolean.TRUE);

        // Query all charities from Firestore
        donationRepository.getCurrentDonation().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    List<Donation> newUrgentDonationList = new ArrayList<Donation>();

                    // Loop through query result
                    for(QueryDocumentSnapshot document: task.getResult()) {
                        // Convert query result into Charity object
                        Donation donation = document.toObject(Donation.class);
                        // Add Charity object into charityList
                        newUrgentDonationList.add(donation);

                        Log.d("HomeFragment", "Charity list query successful");
                    }

                    urgentDonationList.setValue(newUrgentDonationList);
                }
                else {
                    Log.e(TAG, "Charity list query failed");
                }

                isUpdating.setValue(Boolean.FALSE);
            }
        });
    }

    public void updateSelectedDonationList() {
        isUpdating.setValue(Boolean.TRUE);

        // Query all charities from Firestore
        donationRepository.getAllDonation().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    List<Donation> newSelectedDonationList = new ArrayList<Donation>();

                    // Loop through query result
                    for(QueryDocumentSnapshot document: task.getResult()) {
                        // Convert query result into Charity object
                        Donation donation = document.toObject(Donation.class);
                        // Add Charity object into charityList
                        newSelectedDonationList.add(donation);
                    }

                    selectedDonationList.setValue(newSelectedDonationList);
                }
                else {
                    Log.e(TAG, "Charity list query failed");
                }

                isUpdating.setValue(Boolean.FALSE);
            }
        });
    }

    public MutableLiveData<Boolean> getIsUpdating() {
        return isUpdating;
    }

    public LiveData<List<Donation>> getUrgentDonationList() {
        return urgentDonationList;
    }

    public LiveData<List<Donation>> getSelectedDonationList() {
        return selectedDonationList;
    }
}
