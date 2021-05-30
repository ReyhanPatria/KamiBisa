package com.example.kamibisa.data.database.dao;

import com.example.kamibisa.data.model.Charity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.Instant;
import java.util.Date;

public class CharityDao {
    private FirebaseFirestore firestore;

    public CharityDao() {
        this.firestore = FirebaseFirestore.getInstance();
    }

    public Task<DocumentReference> insertCharity(Charity charity) {
        return firestore.collection("charities")
                .add(charity);
    }

    public Task<QuerySnapshot> getAllCharity() {
        return firestore.collection("charities").get();
    }

    public Task<QuerySnapshot> getCurrentCharity() {
        return firestore.collection("charities")
                .whereGreaterThan("finishedAt", Date.from(Instant.now()))
                .get();
    }
}
