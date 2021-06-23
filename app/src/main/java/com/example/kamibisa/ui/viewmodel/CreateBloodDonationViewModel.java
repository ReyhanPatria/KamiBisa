package com.example.kamibisa.ui.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kamibisa.data.model.BloodDonation;
import com.example.kamibisa.data.repository.BloodDonationRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

public class CreateBloodDonationViewModel extends ViewModel {
    private static final String TAG = "CreateBloodDonationViewModel";

    private static CreateBloodDonationViewModel instance;

    private BloodDonationRepository bloodDonationRepository;

    private MutableLiveData<Boolean> isUpdating;
    private MutableLiveData<Boolean> isInsertSuccessful;

    public CreateBloodDonationViewModel(BloodDonationRepository bloodDonationRepository) {
        this.bloodDonationRepository = bloodDonationRepository;
        initializeVariables();
    }

    public static CreateBloodDonationViewModel getInstance(BloodDonationRepository bloodDonationRepository) {
        if(instance == null) {
            instance = new CreateBloodDonationViewModel(bloodDonationRepository);
        }
        return instance;
    }

    private void initializeVariables() {
        this.isUpdating = new MutableLiveData<Boolean>(Boolean.FALSE);
        this.isInsertSuccessful = new MutableLiveData<Boolean>(Boolean.FALSE);
    }

    public void insertBloodDonation(BloodDonation bloodDonation) {
        this.isUpdating.setValue(Boolean.TRUE);

        bloodDonationRepository.insertBloodDonation(bloodDonation)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if(task.isSuccessful()) {
                            isInsertSuccessful.setValue(Boolean.TRUE);
                            isInsertSuccessful.setValue(Boolean.FALSE);
                        }
                        else {
                            Log.e(TAG, "Insert blood donation failed");
                        }

                        isUpdating.setValue(Boolean.FALSE);
                    }
                });
    }

    public MutableLiveData<Boolean> getIsUpdating() {
        return isUpdating;
    }

    public MutableLiveData<Boolean> getIsInsertSuccessful() {
        return isInsertSuccessful;
    }
}
