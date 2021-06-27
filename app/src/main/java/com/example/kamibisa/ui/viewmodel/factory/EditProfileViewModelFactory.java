package com.example.kamibisa.ui.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.kamibisa.data.repository.UserRepository;
import com.example.kamibisa.ui.viewmodel.EditProfileViewModel;
import com.example.kamibisa.ui.viewmodel.HistoryViewModel;

public class EditProfileViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static final String TAG = "EditProfileViewModelFactory";

    private UserRepository userRepository;

    public EditProfileViewModelFactory(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        @SuppressWarnings("unchecked")
        final T t = (T) EditProfileViewModel.getInstance(userRepository);
        return t;
    }
}
