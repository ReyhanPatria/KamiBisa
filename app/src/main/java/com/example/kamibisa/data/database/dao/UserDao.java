package com.example.kamibisa.data.database.dao;

import com.example.kamibisa.data.model.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserDao {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    public UserDao() {
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firestore = FirebaseFirestore.getInstance();
    }

    public Task<Void> setUserData(User user) {
        String id = user.getFirebaseUser().getUid();
        return firestore.collection("users")
                .document(id)
                .set(user);
    }

    public Task<DocumentSnapshot> getUserData(String id) {
        return firestore.collection("users")
                .document(id)
                .get();
    }

    public Task<AuthResult> registerUser(User user, String password) {
        String email = user.getEmail();
        return firebaseAuth.createUserWithEmailAndPassword(email, password);
    }

    public Task<AuthResult> login(String email, String password) {
        return firebaseAuth.signInWithEmailAndPassword(email, password);
    }

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }
}
