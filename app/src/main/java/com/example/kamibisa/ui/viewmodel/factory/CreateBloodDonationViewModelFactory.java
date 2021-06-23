package com.example.kamibisa.ui.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.kamibisa.data.repository.BloodDonationRepository;
import com.example.kamibisa.ui.viewmodel.CreateBloodDonationViewModel;

public class CreateBloodDonationViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private BloodDonationRepository bloodDonationRepository;

    public CreateBloodDonationViewModelFactory(BloodDonationRepository bloodDonationRepository) {
        this.bloodDonationRepository = bloodDonationRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        @SuppressWarnings("unchecked")
        final T t = (T) CreateBloodDonationViewModel.getInstance(bloodDonationRepository);
        return t;
    }
}
