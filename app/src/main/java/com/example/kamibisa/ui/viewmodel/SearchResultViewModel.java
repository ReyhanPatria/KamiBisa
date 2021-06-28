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

public class SearchResultViewModel extends ViewModel {
    private static final String TAG = "SearchResultViewModel";

    private static SearchResultViewModel instance;

    private DonationRepository donationRepository;

    private MutableLiveData<Boolean> isUpdating;

    private MutableLiveData<List<Donation>> donationList;

    public SearchResultViewModel(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;

        initializeVariables();
    }

    public static SearchResultViewModel getInstance(DonationRepository donationRepository) {
        if(instance == null) {
            instance = new SearchResultViewModel(donationRepository);
        }
        return instance;
    }

    private void initializeVariables() {
        this.isUpdating = new MutableLiveData<Boolean>(Boolean.FALSE);
        this.donationList = new MutableLiveData<List<Donation>>(new ArrayList<Donation>());
    }

    public void loadDonationList(String query) {
        this.isUpdating.setValue(Boolean.TRUE);

        donationRepository.getDonationListBasedOnTitle(query)
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            List<Donation> newDonationList = new ArrayList<Donation>();

                            for(QueryDocumentSnapshot document: task.getResult()) {
                                Donation d = document.toObject(Donation.class);
                                d.setId(document.getId());

                                newDonationList.add(d);
                            }

                            donationList.setValue(newDonationList);

                            Log.d(TAG, "Donatin list size = " + newDonationList.size());
                        }

                        isUpdating.setValue(Boolean.FALSE);
                    }
                });
    }

    public LiveData<Boolean> getIsUpdating() {
        return isUpdating;
    }

    public LiveData<List<Donation>> getDonationList() {
        return donationList;
    }
}
