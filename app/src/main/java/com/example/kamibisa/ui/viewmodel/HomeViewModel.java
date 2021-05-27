package com.example.kamibisa.ui.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.kamibisa.data.repository.CharityRepository;

public class HomeViewModel extends ViewModel {
    private static final String TAG = "HomeViewModel";

    private static HomeViewModel instance;

    private CharityRepository charityRepository;

    public HomeViewModel(CharityRepository charityRepository) {
        this.charityRepository = charityRepository;
    }

    public static HomeViewModel getInstance(CharityRepository charityRepository) {
        if(instance == null) {
            instance = new HomeViewModel(charityRepository);
        }
        return instance;
    }
}
