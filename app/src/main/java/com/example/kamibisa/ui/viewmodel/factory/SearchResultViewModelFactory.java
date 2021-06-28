package com.example.kamibisa.ui.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.kamibisa.data.repository.DonationRepository;
import com.example.kamibisa.ui.viewmodel.SearchResultViewModel;

public class SearchResultViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static final String TAG = "SearchResultViewModelFactory";

    private DonationRepository donationRepository;

    public SearchResultViewModelFactory(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        @SuppressWarnings("unchecked")
        final T t = (T) SearchResultViewModel.getInstance(donationRepository);
        return t;
    }
}
