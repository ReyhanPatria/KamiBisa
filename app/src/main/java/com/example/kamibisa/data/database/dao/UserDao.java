package com.example.kamibisa.data.database.dao;

import android.app.Activity;
import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.example.kamibisa.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserDao {
    private FirebaseAuth firebaseAuth;

    public UserDao() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void insertUserData(User user) {
        String id = user.getFirebaseUser().getUid();
        FirebaseFirestore.getInstance()
                .collection("users")
                .document(id)
                .set(user);
    }

    public Task<AuthResult> registerUser(User user, String password) {
        String email = user.getEmail();
        return firebaseAuth.createUserWithEmailAndPassword(email, password);
    }

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }
}
