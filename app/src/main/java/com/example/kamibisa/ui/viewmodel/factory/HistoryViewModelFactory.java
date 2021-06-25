package com.example.kamibisa.ui.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.kamibisa.data.repository.DonationRecordRepository;
import com.example.kamibisa.data.repository.DonationRepository;
import com.example.kamibisa.ui.viewmodel.HistoryViewModel;

public class HistoryViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private DonationRepository donationRepository;
    private DonationRecordRepository donationRecordRepository;

    public HistoryViewModelFactory(DonationRepository donationRepository,
                                   DonationRecordRepository donationRecordRepository) {
        this.donationRepository = donationRepository;
        this.donationRecordRepository = donationRecordRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        @SuppressWarnings("unchecked")
        final T t = (T) HistoryViewModel.getInstance(donationRepository, donationRecordRepository);
        return t;
    }
}
