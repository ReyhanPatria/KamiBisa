package com.example.kamibisa.ui.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kamibisa.data.model.User;
import com.example.kamibisa.data.repository.UserRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class RegisterViewModel extends ViewModel {
    public static String TAG = "RegisterViewModel";

    private static RegisterViewModel instance;

    private UserRepository userRepository;

    private MutableLiveData<Boolean> isUpdating;
    private MutableLiveData<Boolean> isRegisterComplete;
    private MutableLiveData<Boolean> isEmailUsed;

    public RegisterViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.isUpdating = new MutableLiveData<Boolean>(Boolean.FALSE);
        this.isRegisterComplete = new MutableLiveData<Boolean>(Boolean.FALSE);
        this.isEmailUsed = new MutableLiveData<Boolean>(Boolean.FALSE);
    }

    public static RegisterViewModel getInstance(UserRepository userRepository) {
        if(instance == null) {
            instance = new RegisterViewModel(userRepository);
        }

        return instance;
    }

    public void registerUser(User user, String password) {
        this.isUpdating.setValue(Boolean.TRUE);

        userRepository.registerUser(user, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        isUpdating.setValue(Boolean.FALSE);

                        if(task.isSuccessful()) {
                            user.setFirebaseUser(task.getResult().getUser());
                            insertUserData(user);
                        }
                        else {
                            isEmailUsed.setValue(Boolean.TRUE);
                        }
                    }
                });
    }

    public void insertUserData(User user) {
        this.isRegisterComplete.setValue(Boolean.TRUE);

        userRepository.insertUserData(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        isUpdating.setValue(Boolean.FALSE);

                        if(task.isSuccessful()) {
                            isRegisterComplete.setValue(Boolean.TRUE);
                        }
                        else {
                            Log.e(TAG, "Error encouteres when saving user data");
                        }
                    }
                });
    }

    public MutableLiveData<Boolean> getIsUpdating() {
        return isUpdating;
    }

    public MutableLiveData<Boolean> getIsRegisterComplete() {
        return isRegisterComplete;
    }

    public MutableLiveData<Boolean> getIsEmailUsed() {
        return isEmailUsed;
    }
}
