package com.example.kamibisa.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kamibisa.data.model.DonationRecord;
import com.example.kamibisa.data.repository.DonationRecordRepository;
import com.example.kamibisa.data.repository.DonationRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

public class DonateViewModel extends ViewModel {
    private static String TAG = "DonationViewModel";

    private static DonateViewModel instance;

    private DonationRepository donationRepository;
    private DonationRecordRepository donationRecordRepository;

    private MutableLiveData<Boolean> isUpdating;
    private MutableLiveData<Boolean> isFinished;

    public DonateViewModel(DonationRepository donationRepository,
                           DonationRecordRepository donationRecordRepository) {
        this.donationRepository = donationRepository;
        this.donationRecordRepository = donationRecordRepository;
        initializeVariables();
    }

    public static DonateViewModel getInstance(DonationRepository donationRepository,
                                              DonationRecordRepository donationRecordRepository) {
        if(instance == null) {
            instance = new DonateViewModel(donationRepository, donationRecordRepository);
        }
        return instance;
    }

    private void initializeVariables() {
        this.isUpdating = new MutableLiveData<Boolean>(Boolean.FALSE);
        this.isFinished = new MutableLiveData<Boolean>(Boolean.FALSE);
    }

    public void donateToDonation(DonationRecord donationRecord) {
        this.isUpdating.setValue(Boolean.TRUE);

        String donationId = donationRecord.getDonationId();
        Integer amount = donationRecord.getDonationAmount();

        donationRepository.donateToDonation(donationId, amount)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        isUpdating.setValue(Boolean.FALSE);

                        if(task.isSuccessful()) {
                            insertDonationRecord(donationRecord);
                        }
                    }
                });
    }

    public void insertDonationRecord(DonationRecord donationRecord) {
        this.isUpdating.setValue(Boolean.TRUE);

        donationRecordRepository.insertDonationRecord(donationRecord)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
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
