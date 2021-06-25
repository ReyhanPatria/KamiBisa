package com.example.kamibisa.ui.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.kamibisa.data.repository.UserRepository;
import com.example.kamibisa.ui.viewmodel.ProfileViewModel;

public class ProfileViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private UserRepository userRepository;

    public ProfileViewModelFactory(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        @SuppressWarnings("unchecked")
        final T t = (T) ProfileViewModel.getInstance(userRepository);
        return t;
    }
}
