package com.example.kamibisa.data.database.dao;

import com.example.kamibisa.data.model.Donation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.Instant;
import java.util.Date;

public class DonationDao {
    private FirebaseFirestore firestore;

    public DonationDao() {
        this.firestore = FirebaseFirestore.getInstance();
    }

    public Task<DocumentReference> insertDonation(Donation donation) {
        return firestore.collection("charities")
                .add(donation);
    }

    public Task<QuerySnapshot> getAllDonation() {
        return firestore.collection("charities").get();
    }

    public Task<QuerySnapshot> getCurrentDonation() {
        return firestore.collection("charities")
                .whereGreaterThan("finishedAt", Date.from(Instant.now()))
                .get();
    }
}
