package com.example.kamibisa.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kamibisa.data.model.Charity;
import com.example.kamibisa.data.repository.CharityRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private static final String TAG = "HomeViewModel";

    private static HomeViewModel instance;

    private CharityRepository charityRepository;

    // Add variables to be observed
    private MutableLiveData<List<Charity>> charityList;

    public HomeViewModel(CharityRepository charityRepository) {
        this.charityRepository = charityRepository;
    }

    public static HomeViewModel getInstance(CharityRepository charityRepository) {
        if(instance == null) {
            instance = new HomeViewModel(charityRepository);
        }
        return instance;
    }

    public MutableLiveData<List<Charity>> getCharityList() {
        return charityList;
    }
}
