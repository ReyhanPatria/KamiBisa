package com.example.kamibisa.ui.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kamibisa.data.model.Charity;
import com.example.kamibisa.data.repository.CharityRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private static final String TAG = "HomeViewModel";

    private static HomeViewModel instance;

    private CharityRepository charityRepository;

    // Add variables to be observed
    private MutableLiveData<Boolean> isUpdating;
    private MutableLiveData<List<Charity>> urgentCharityList;
    private MutableLiveData<List<Charity>> selectedCharityList;


    public HomeViewModel(CharityRepository charityRepository) {
        this.charityRepository = charityRepository;
        initializeVariables();
    }

    public static HomeViewModel getInstance(CharityRepository charityRepository) {
        if(instance == null) {
            instance = new HomeViewModel(charityRepository);
        }
        return instance;
    }

    public void initializeVariables() {
        isUpdating = new MutableLiveData<Boolean>(Boolean.FALSE);
        urgentCharityList = new MutableLiveData<List<Charity>>(new ArrayList<Charity>());
        selectedCharityList = new MutableLiveData<List<Charity>>(new ArrayList<Charity>());
    }

    public void updateCharityList() {
        updateUrgentCharityList();
        updateSelectedCharityList();
    }

    public void updateUrgentCharityList() {
        isUpdating.setValue(Boolean.TRUE);

        // Query all charities from Firestore
        charityRepository.getAllCharityList().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    List<Charity> newUrgentCharityList = urgentCharityList.getValue();

                    // Loop through query result
                    for(QueryDocumentSnapshot document: task.getResult()) {
                        // Convert query result into Charity object
                        Charity charity = document.toObject(Charity.class);
                        // Add Charity object into charityList
                        newUrgentCharityList.add(charity);
                    }

                    urgentCharityList.setValue(newUrgentCharityList);
                }
                else {
                    Log.e(TAG, "Charity list query failed");
                }

                isUpdating.setValue(Boolean.FALSE);
            }
        });
    }

    public void updateSelectedCharityList() {
        isUpdating.setValue(Boolean.TRUE);

        // Query all charities from Firestore
        charityRepository.getAllCharityList().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    List<Charity> newSelectedCharityList = selectedCharityList.getValue();

                    // Loop through query result
                    for(QueryDocumentSnapshot document: task.getResult()) {
                        // Convert query result into Charity object
                        Charity charity = document.toObject(Charity.class);
                        // Add Charity object into charityList
                        newSelectedCharityList.add(charity);
                    }

                    selectedCharityList.setValue(newSelectedCharityList);
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

    public MutableLiveData<List<Charity>> getUrgentCharityList() {
        return urgentCharityList;
    }
}
