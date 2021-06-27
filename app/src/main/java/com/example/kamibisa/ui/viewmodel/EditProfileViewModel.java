package com.example.kamibisa.ui.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kamibisa.data.model.User;
import com.example.kamibisa.data.repository.UserRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

public class EditProfileViewModel extends ViewModel {
    public static String TAG = "EditProfileViewModel";

    private static EditProfileViewModel instance;

    private UserRepository userRepository;

    private MutableLiveData<Boolean> isUpdating;
    private MutableLiveData<Boolean> isFinished;

    private MutableLiveData<User> userData;

    public EditProfileViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;

        initializeVariables();
        loadUserData();
    }

    public static EditProfileViewModel getInstance(UserRepository userRepository) {
        if(instance == null) {
            instance = new EditProfileViewModel(userRepository);
        }
        return instance;
    }

    public void initializeVariables() {
        this.isUpdating = new MutableLiveData<Boolean>(Boolean.FALSE);
        this.isFinished = new MutableLiveData<Boolean>(Boolean.FALSE);
        this.userData = new MutableLiveData<User>();
    }

    public void loadUserData() {
        this.isUpdating.setValue(Boolean.TRUE);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        userRepository.getUserData(userId)
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            User user = task.getResult().toObject(User.class);
                            user.setFirebaseUser(FirebaseAuth.getInstance().getCurrentUser());
                            user.setId(FirebaseAuth.getInstance().getCurrentUser().getUid());

                            userData.setValue(user);

                            Log.d(TAG, "Get user data successful");
                        }
                        else {
                            Log.d(TAG, "Get user data failed");
                        }

                        isUpdating.setValue(Boolean.FALSE);
                    }
                });
    }

    public void updateUserData(User newUserData, String newPassword) {
        this.isUpdating.setValue(Boolean.TRUE);

        userRepository.setUserData(newUserData)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            FirebaseAuth.getInstance().getCurrentUser().updateEmail(newUserData.getEmail());

                            if(!newPassword.isEmpty()) {
                                FirebaseAuth.getInstance().getCurrentUser().updatePassword(newPassword);
                            }

                            isFinished.setValue(Boolean.TRUE);
                            isFinished.setValue(Boolean.FALSE);
                        }

                        isUpdating.setValue(Boolean.FALSE);
                    }
                });
    }

    public LiveData<Boolean> getIsUpdating() {
        return isUpdating;
    }

    public LiveData<Boolean> getIsFinished() {
        return isFinished;
    }

    public LiveData<User> getUserData() {
        return userData;
    }
}
