package com.example.kamibisa.ui.viewmodel;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kamibisa.data.model.User;
import com.example.kamibisa.data.repository.UserRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class RegisterViewModel extends ViewModel {
    private static RegisterViewModel instance;

    private UserRepository userRepository;

    private MutableLiveData<Boolean> isUpdating;
    private MutableLiveData<Boolean> isRegisterComplete;

    public RegisterViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.isUpdating = new MutableLiveData<Boolean>(Boolean.FALSE);
        this.isRegisterComplete = new MutableLiveData<Boolean>(Boolean.FALSE);
    }

    public static RegisterViewModel getInstance(UserRepository userRepository) {
        if(instance == null) {
            instance = new RegisterViewModel(userRepository);
        }

        return instance;
    }

    public void insertUserData(User user) {
        userRepository.insertUserData(user);
    }

    public void registerUser(User user, String password) {
        this.isUpdating.setValue(Boolean.TRUE);

        userRepository.registerUser(user, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        isUpdating.setValue(Boolean.FALSE);
                        isRegisterComplete.setValue(Boolean.TRUE);
                    }
                });
    }

    public MutableLiveData<Boolean> getIsUpdating() {
        return isUpdating;
    }

    public MutableLiveData<Boolean> getIsRegisterComplete() {
        return isRegisterComplete;
    }
}
