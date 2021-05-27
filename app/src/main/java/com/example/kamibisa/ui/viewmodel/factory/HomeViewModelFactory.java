package com.example.kamibisa.ui.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.kamibisa.data.repository.CharityRepository;
import com.example.kamibisa.ui.viewmodel.HomeViewModel;
import com.example.kamibisa.ui.viewmodel.LoginViewModel;

public class HomeViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private CharityRepository charityRepository;

    public HomeViewModelFactory(CharityRepository charityRepository) {
        this.charityRepository = charityRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        @SuppressWarnings("unchecked")
        final T t = (T) HomeViewModel.getInstance(charityRepository);
        return t;
    }
}
