package com.example.kamibisa.ui.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kamibisa.data.model.Donation;
import com.example.kamibisa.data.repository.DonationRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

public class CreateDonationViewModel extends ViewModel {
    private static String TAG = "CreateDonationViewModel";

    private static CreateDonationViewModel instance;

    private DonationRepository donationRepository;

    private MutableLiveData<Boolean> isUpdating;
    private MutableLiveData<Boolean> isCreateDonationDone;

    public CreateDonationViewModel(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
        initializeVariables();
    }

    public static CreateDonationViewModel getInstance(DonationRepository donationRepository) {
        if(instance == null) {
            instance = new CreateDonationViewModel(donationRepository);
        }
        return instance;
    }

    private void initializeVariables() {
        this.isUpdating = new MutableLiveData<Boolean>(Boolean.FALSE);
        this.isCreateDonationDone = new MutableLiveData<Boolean>(Boolean.FALSE);
    }

    public void insertDonation(Donation donation) {
        this.isUpdating.setValue(Boolean.TRUE);

        donationRepository.insertDonation(donation)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        isUpdating.setValue(Boolean.FALSE);

                        String m = "";
                        if(task.isSuccessful()) {
                            m = "Donation inserted successfully";
                            isCreateDonationDone.setValue(Boolean.TRUE);
                        }
                        else {
                            m = "Donation not inserted";
                        }

                        Log.d(TAG, m);
                        isCreateDonationDone.setValue(Boolean.FALSE);
                    }
                });
    }

    public MutableLiveData<Boolean> getIsUpdating() {
        return isUpdating;
    }

    public MutableLiveData<Boolean> getIsCreateDonationDone() {
        return isCreateDonationDone;
    }
}
