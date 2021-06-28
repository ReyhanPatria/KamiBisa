package com.example.kamibisa.ui.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kamibisa.data.model.BloodDonation;
import com.example.kamibisa.data.model.Donation;
import com.example.kamibisa.data.repository.BloodDonationRepository;
import com.example.kamibisa.data.repository.DonationRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BloodDonationViewModel extends ViewModel {
    private static final String TAG = "BloodDonationViewModel";

    private static BloodDonationViewModel instance;

    private BloodDonationRepository bloodDonationRepository;

    // Add variables to be observed
    private MutableLiveData<Boolean> isUpdating;

    private MutableLiveData<List<BloodDonation>> bloodDonationList;

    public BloodDonationViewModel(BloodDonationRepository bloodDonationRepository) {
        this.bloodDonationRepository = bloodDonationRepository;
        initializeVariables();
        updateBloodDonationList();
    }

    public static BloodDonationViewModel getInstance(BloodDonationRepository bloodDonationRepository) {
        if(instance == null) {
            instance = new BloodDonationViewModel(bloodDonationRepository);
        }
        return instance;
    }

    public void initializeVariables() {
        isUpdating = new MutableLiveData<Boolean>(Boolean.FALSE);
        bloodDonationList = new MutableLiveData<List<BloodDonation>>(new ArrayList<BloodDonation>());
    }

    public void updateBloodDonationList() {
        isUpdating.setValue(Boolean.TRUE);

        // Query all charities from Firestore
        bloodDonationRepository.getActiveBloodDonations().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    List<BloodDonation> newBloodDonationList = new ArrayList<BloodDonation>();

                    // Loop through query result
                    for(QueryDocumentSnapshot document: task.getResult()) {
                        // Convert query result into Charity object
                        BloodDonation bloodDonation = document.toObject(BloodDonation.class);
                        bloodDonation.setId(document.getId());

                        // Add Charity object into charityList
                        newBloodDonationList.add(bloodDonation);

                        Log.d(TAG, "Blood donation list query successful");
                    }

                    bloodDonationList.setValue(newBloodDonationList);
                }
                else {
                    Log.e(TAG, "Blood donation list query failed");
                }

                isUpdating.setValue(Boolean.FALSE);
            }
        });
    }

    public MutableLiveData<Boolean> getIsUpdating() {
        return isUpdating;
    }

    public MutableLiveData<List<BloodDonation>> getBloodDonationList() {
        return bloodDonationList;
    }
}
