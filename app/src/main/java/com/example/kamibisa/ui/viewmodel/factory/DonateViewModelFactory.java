package com.example.kamibisa.ui.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.kamibisa.data.repository.DonationRecordRepository;
import com.example.kamibisa.data.repository.DonationRepository;
import com.example.kamibisa.ui.viewmodel.DonateViewModel;

public class DonateViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private DonationRepository donationRepository;
    private DonationRecordRepository donationRecordRepository;

    public DonateViewModelFactory(DonationRepository donationRepository,
                                  DonationRecordRepository donationRecordRepository) {
        this.donationRepository = donationRepository;
        this.donationRecordRepository = donationRecordRepository;
    }

    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        @SuppressWarnings("unchecked")
        final T t = (T) DonateViewModel.getInstance(donationRepository, donationRecordRepository);
        return t;
    }
}
