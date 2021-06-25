package com.example.kamibisa.ui.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kamibisa.data.model.User;
import com.example.kamibisa.data.repository.UserRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

public class ProfileViewModel extends ViewModel {
    private static final String TAG = "ProfileViewModel";

    private static ProfileViewModel instance;

    private UserRepository userRepository;

    private MutableLiveData<Boolean> isUpdating;
    private MutableLiveData<User> userData;

    public ProfileViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;

        initializeVariables();
        updateUserData();
    }

    public static ProfileViewModel getInstance(UserRepository userRepository) {
        if(instance == null) {
            instance = new ProfileViewModel(userRepository);
        }
        return instance;
    }

    private void initializeVariables() {
        this.isUpdating = new MutableLiveData<Boolean>(Boolean.FALSE);
        this.userData = new MutableLiveData<User>(new User());
    }

    public void updateUserData() {
        this.isUpdating.setValue(Boolean.TRUE);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userRepository.getUserData(userId)
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            User user = task.getResult().toObject(User.class);
                            user.setId(userId);

                            userData.setValue(user);

                            Log.d(TAG, "Get user data successful");
                        }
                        else {
                            Log.e(TAG, "Get user data failed");
                        }

                        isUpdating.setValue(Boolean.FALSE);
                    }
                });
    }

    public MutableLiveData<Boolean> getIsUpdating() {
        return isUpdating;
    }

    public MutableLiveData<User> getUserData() {
        return userData;
    }
}
