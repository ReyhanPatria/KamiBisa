package com.example.kamibisa.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kamibisa.data.repository.UserRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class LoginViewModel extends ViewModel {
    public static String TAG = "LoginViewModel";

    private static LoginViewModel instance;

    private UserRepository userRepository;

    private MutableLiveData<Boolean> isUpdating;

    public LoginViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
        initializeVariables();
    }

    public static LoginViewModel getInstance(UserRepository userRepository) {
        if(instance == null) {
            instance = new LoginViewModel(userRepository);
        }
        return instance;
    }

    public void initializeVariables() {
        isUpdating = new MutableLiveData<Boolean>(Boolean.FALSE);
    }

    public void login(String email, String password) {
        isUpdating.setValue(Boolean.FALSE);

        userRepository.login(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        isUpdating.setValue(Boolean.TRUE);
                    }
                });
    }
}
