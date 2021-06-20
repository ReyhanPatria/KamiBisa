package com.example.kamibisa.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kamibisa.data.repository.DonationRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class DonateViewModel extends ViewModel {
    private static String TAG = "DonationViewModel";

    private static DonateViewModel instance;

    private DonationRepository donationRepository;

    private MutableLiveData<Boolean> isUpdating;
    private MutableLiveData<Boolean> isFinished;

    public DonateViewModel(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
        initializeVariables();
    }

    public static DonateViewModel getInstance(DonationRepository donationRepository) {
        if(instance == null) {
            instance = new DonateViewModel(donationRepository);
        }
        return instance;
    }

    private void initializeVariables() {
        this.isUpdating = new MutableLiveData<Boolean>(Boolean.FALSE);
        this.isFinished = new MutableLiveData<Boolean>(Boolean.FALSE);
    }

    public void donateToDonation(String donationId, Integer amount) {
        this.isUpdating.setValue(Boolean.TRUE);

        donationRepository.donateToDonation(donationId, amount)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            isFinished.setValue(Boolean.TRUE);
                            isFinished.setValue(Boolean.FALSE);
                        }

                        isUpdating.setValue(Boolean.FALSE);
                    }
                });
    }

    public MutableLiveData<Boolean> getIsUpdating() {
        return isUpdating;
    }

    public MutableLiveData<Boolean> getIsFinished() {
        return isFinished;
    }
}
