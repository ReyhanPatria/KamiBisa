package com.example.kamibisa.data.database.dao;

import com.example.kamibisa.data.model.BloodDonation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class BloodDonationDao {
    private FirebaseFirestore firestore;

    public BloodDonationDao() {
        this.firestore = FirebaseFirestore.getInstance();
    }

    public Task<DocumentReference> insertBloodDonation(BloodDonation bloodDonation) {
        return firestore.collection("blood_donations").add(bloodDonation);
    }

    public Task<QuerySnapshot> getAllBloodDonation() {
        return firestore.collection("blood_donations").get();
    }

    public Task<DocumentSnapshot> getBloodDonation(String id) {
        return firestore.collection("blood_donations").document(id).get();
    }
}
