package com.example.kamibisa.data.database.dao;

import com.example.kamibisa.data.model.DonationRecord;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class DonationRecordDao {
    private static final String TAG = "DonationRecordDao";

    private FirebaseFirestore firestore;

    public DonationRecordDao() {
        this.firestore = FirebaseFirestore.getInstance();
    }

    public Task<DocumentReference> insertDonationRecord(DonationRecord donationRecord) {
        return firestore.collection("donation_records").add(donationRecord);
    }

    public Task<QuerySnapshot> getDonationRecordBasedOnUserId(String userId) {
        return firestore.collection("donation_records")
                .whereEqualTo("userId", userId)
                .get();
    }
}
