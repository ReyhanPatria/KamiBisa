package com.example.kamibisa.ui.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.kamibisa.data.repository.DonationRepository;
import com.example.kamibisa.ui.viewmodel.HomeViewModel;

public class HomeViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private DonationRepository donationRepository;

    public HomeViewModelFactory(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        @SuppressWarnings("unchecked")
        final T t = (T) HomeViewModel.getInstance(donationRepository);
        return t;
    }
}
