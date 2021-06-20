package com.example.kamibisa.ui.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.kamibisa.data.repository.DonationRepository;
import com.example.kamibisa.ui.viewmodel.DonateViewModel;

public class DonateViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private DonationRepository donationRepository;

    public DonateViewModelFactory(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        @SuppressWarnings("unchecked")
        final T t = (T) DonateViewModel.getInstance(donationRepository);
        return t;
    }
}
