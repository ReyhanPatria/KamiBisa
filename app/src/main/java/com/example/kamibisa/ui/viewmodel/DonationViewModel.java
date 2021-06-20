package com.example.kamibisa.ui.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kamibisa.data.model.Donation;
import com.example.kamibisa.data.repository.DonationRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

public class DonationViewModel extends ViewModel {
    private static String TAG = "DonationViewModel";

    private static DonationViewModel instance;

    private DonationRepository donationRepository;

    private MutableLiveData<Boolean> isUpdating;
    private MutableLiveData<Donation> donationData;

    public DonationViewModel(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
        initializeVariables();
    }

    public static DonationViewModel getInstance(DonationRepository donationRepository) {
        if(instance == null) {
            instance = new DonationViewModel(donationRepository);
        }
        return instance;
    }

    public void initializeVariables() {
        this.isUpdating = new MutableLiveData<Boolean>(Boolean.FALSE);
        this.donationData = new MutableLiveData<Donation>();
    }

    public void loadDonationData(String id) {
        this.isUpdating.setValue(Boolean.TRUE);

        donationRepository.getDonation(id)
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            Donation donation = task.getResult().toObject(Donation.class);
                            donationData.setValue(donation);
                        }
                        else {
                            Log.d(TAG, "Get donation failed");
                        }

                        isUpdating.setValue(Boolean.FALSE);
                    }
                });
    }

    public MutableLiveData<Boolean> getIsUpdating() {
        return isUpdating;
    }

    public MutableLiveData<Donation> getDonationData() {
        return donationData;
    }
}
