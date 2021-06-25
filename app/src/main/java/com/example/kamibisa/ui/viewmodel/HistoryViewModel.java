package com.example.kamibisa.ui.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kamibisa.data.model.Donation;
import com.example.kamibisa.data.model.DonationRecord;
import com.example.kamibisa.data.repository.DonationRecordRepository;
import com.example.kamibisa.data.repository.DonationRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HistoryViewModel extends ViewModel {
    private static final String TAG = "HistoryViewModel";

    private static HistoryViewModel instance;

    private DonationRepository donationRepository;
    private DonationRecordRepository donationRecordRepository;

    private MutableLiveData<Boolean> isUpdating;

    private MutableLiveData<List<Donation>> ownedDonationList;
    private MutableLiveData<List<Donation>> givenDonationList;

    private MutableLiveData<List<String>> givenDonationIdList;

    public HistoryViewModel(DonationRepository donationRepository,
                            DonationRecordRepository donationRecordRepository) {
        this.donationRepository = donationRepository;
        this.donationRecordRepository = donationRecordRepository;

        initializeVariables();
        setDonationRecordList();
        updateOwnedDonationList();
        updateGivenDonationList();
    }

    public static HistoryViewModel getInstance(DonationRepository donationRepository,
                                                DonationRecordRepository donationRecordRepository) {
        if(instance == null) {
            instance = new HistoryViewModel(donationRepository, donationRecordRepository);
        }
        return instance;
    }

    private void initializeVariables() {
        this.isUpdating = new MutableLiveData<Boolean>(Boolean.FALSE);

        this.ownedDonationList = new MutableLiveData<List<Donation>>(new ArrayList<Donation>());
        this.givenDonationList = new MutableLiveData<List<Donation>>(new ArrayList<Donation>());

        this.givenDonationIdList = new MutableLiveData<List<String>>(new ArrayList<String>());
    }

    public void updateOwnedDonationList() {
        this.isUpdating.setValue(Boolean.TRUE);

        String creatorId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        donationRepository.getDonationListBasedOnCreatorId(creatorId)
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            List<Donation> newOwnedDonationList = new ArrayList<Donation>();

                            // Loop through query result
                            for(QueryDocumentSnapshot document: task.getResult()) {
                                // Create donation object
                                Donation d = document.toObject(Donation.class);
                                d.setId(document.getId());

                                // Add donation object unto donation list
                                newOwnedDonationList.add(d);

                                Log.d(TAG, "Owned donation list updated");
                            }

                            ownedDonationList.setValue(newOwnedDonationList);
                        }
                        else {
                            Log.e(TAG, "Update owned donation list failed");
                        }

                        isUpdating.setValue(Boolean.FALSE);
                    }
                });
    }

    public void updateGivenDonationList() {
        if(!givenDonationIdList.getValue().isEmpty()) {
            this.isUpdating.setValue(Boolean.TRUE);

            donationRepository.getDonationsIn(givenDonationIdList.getValue())
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                List<Donation> newGivenDonationList = new ArrayList<Donation>();

                                for (DocumentSnapshot document : task.getResult()) {
                                    Donation d = document.toObject(Donation.class);
                                    d.setId(document.getId());

                                    newGivenDonationList.add(d);
                                }

                                givenDonationList.setValue(newGivenDonationList);
                            }
                        }
                    });
        }
    }

    public void setDonationRecordList() {
        this.isUpdating.setValue(Boolean.TRUE);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        donationRecordRepository.getDonationRecordBasedOnUserId(userId)
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            List<String> newGivendonationIdList = new ArrayList<String>();

                            for(QueryDocumentSnapshot document: task.getResult()) {
                                String donationId = String.valueOf(document.get("donationId"));
                                newGivendonationIdList.add(donationId);
                            }

                            givenDonationIdList.setValue(newGivendonationIdList);
                        }

                        isUpdating.setValue(Boolean.FALSE);
                    }
                });
    }

    public MutableLiveData<Boolean> getIsUpdating() {
        return isUpdating;
    }

    public MutableLiveData<List<String>> getGivenDonationIdList() {
        return givenDonationIdList;
    }

    public MutableLiveData<List<Donation>> getOwnedDonationList() {
        return ownedDonationList;
    }

    public MutableLiveData<List<Donation>> getGivenDonationList() {
        return givenDonationList;
    }
}
