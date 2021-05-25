package com.example.kamibisa.ui.viewmodel;

import android.util.Log;

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
    private MutableLiveData<Boolean> isLoginCompleted;

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
        isLoginCompleted = new MutableLiveData<Boolean>(Boolean.FALSE);
    }

    public void login(String email, String password) {
        isUpdating.setValue(Boolean.TRUE);

        Log.d(TAG, "Login attempt");
        Log.d(TAG, String.format("Email: %s\nPassword: %s", email, password));

        userRepository.login(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        isUpdating.setValue(Boolean.FALSE);

                        if(task.isSuccessful()) {
                            isLoginCompleted.setValue(Boolean.TRUE);

                            Log.d(TAG, "Login successful");
                        }
                        else {
                            Log.d(TAG, "Login failed");
                        }
                    }
                });
    }

    public MutableLiveData<Boolean> getIsUpdating() {
        return isUpdating;
    }

    public MutableLiveData<Boolean> getIsLoginCompleted() {
        return isLoginCompleted;
    }
}
