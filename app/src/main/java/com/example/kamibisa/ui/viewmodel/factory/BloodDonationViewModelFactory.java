package com.example.kamibisa.ui.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.kamibisa.data.repository.BloodDonationRepository;
import com.example.kamibisa.ui.viewmodel.BloodDonationViewModel;

public class BloodDonationViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private BloodDonationRepository bloodDonationRepository;

    public BloodDonationViewModelFactory(BloodDonationRepository bloodDonationRepository) {
        this.bloodDonationRepository = bloodDonationRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        @SuppressWarnings("unchecked")
        final T t = (T) BloodDonationViewModel.getInstance(bloodDonationRepository);
        return t;
    }
}
