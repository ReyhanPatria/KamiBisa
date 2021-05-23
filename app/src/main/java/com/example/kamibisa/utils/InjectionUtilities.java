package com.example.kamibisa.utils;

import com.example.kamibisa.data.database.Database;
import com.example.kamibisa.data.repository.UserRepository;
import com.example.kamibisa.ui.viewmodel.RegisterViewModel;
import com.example.kamibisa.ui.viewmodel.factory.RegisterViewModelFactory;

public class InjectionUtilities {
    public static InjectionUtilities instance;

    public InjectionUtilities() { }

    public static InjectionUtilities getInstance() {
        if(instance == null) {
            instance = new InjectionUtilities();
        }
        return instance;
    }

    public RegisterViewModelFactory provideRegisterViewModelFactory() {
        UserRepository userRepository = UserRepository.getInstance(Database.getInstance().getUserDao());
        return new RegisterViewModelFactory(userRepository);
    }
}
