package com.example.kamibisa.ui.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kamibisa.data.model.BloodDonation;
import com.example.kamibisa.data.model.Donation;
import com.example.kamibisa.data.repository.BloodDonationRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

public class BloodDonationPageViewModel extends ViewModel {
    private static final String TAG = "BloodDonationPageViewModel";

    private static BloodDonationPageViewModel instance;

    private BloodDonationRepository bloodDonationRepository;

    private MutableLiveData<Boolean> isUpdating;

    private MutableLiveData<BloodDonation> bloodDonationData;

    public BloodDonationPageViewModel(BloodDonationRepository bloodDonationRepository) {
        this.bloodDonationRepository = bloodDonationRepository;
        initializeVariables();
    }

    public static BloodDonationPageViewModel getInstance(BloodDonationRepository bloodDonationRepository) {
        if(instance == null) {
            instance = new BloodDonationPageViewModel(bloodDonationRepository);
        }
        return instance;
    }

    private void initializeVariables() {
        this.isUpdating = new MutableLiveData<Boolean>(Boolean.FALSE);
        this.bloodDonationData = new MutableLiveData<BloodDonation>();
    }

    public void loadBloodDonationData(String id) {
        this.isUpdating.setValue(Boolean.TRUE);

        bloodDonationRepository.getBloodDonation(id)
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            BloodDonation bloodDonation = task.getResult().toObject(BloodDonation.class);
                            bloodDonationData.setValue(bloodDonation);
                        }
                        else {
                            Log.d(TAG, "Get blood donation failed");
                        }

                        isUpdating.setValue(Boolean.FALSE);
                    }
                });
    }

    public MutableLiveData<Boolean> getIsUpdating() {
        return isUpdating;
    }

    public MutableLiveData<BloodDonation> getBloodDonationData() {
        return bloodDonationData;
    }
}
