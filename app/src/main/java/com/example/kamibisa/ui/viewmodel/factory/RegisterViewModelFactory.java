package com.example.kamibisa.ui.viewmodel.factory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.kamibisa.data.repository.UserRepository;
import com.example.kamibisa.ui.viewmodel.RegisterViewModel;

public class RegisterViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private UserRepository userRepository;

    public RegisterViewModelFactory(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        @SuppressWarnings("unchecked")
        final T t = (T) RegisterViewModel.getInstance(userRepository);
        return t;
    }
}
