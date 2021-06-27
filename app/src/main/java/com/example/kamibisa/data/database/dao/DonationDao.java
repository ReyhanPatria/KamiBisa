package com.example.kamibisa.data.database.dao;

import androidx.annotation.NonNull;

import com.example.kamibisa.data.model.Donation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class DonationDao {
    private FirebaseFirestore firestore;

    public DonationDao() {
        this.firestore = FirebaseFirestore.getInstance();
    }

    public Task<DocumentReference> insertDonation(Donation donation) {
        return firestore.collection("donations")
                .add(donation);
    }

    public Task<DocumentSnapshot> getDonation(String id) {
        return firestore.collection("donations").document(id).get();
    }

    public Task<QuerySnapshot> getAllDonationList() {
        return firestore.collection("donations").get();
    }

    public Task<QuerySnapshot> getActiveDonationList() {
        return firestore.collection("donations")
                .whereGreaterThan("finishedDate", Date.from(Instant.now()))
                .get();
    }

    public Task<Void> donateToDonation(String donationId, Integer amount) {
        return firestore.collection("donations").document(donationId)
                .update("gatheredAmount", FieldValue.increment(amount));
    }

    public Task<QuerySnapshot> getDonationListBasedOnCreatorId(String creatorId) {
        return firestore.collection("donations")
                .whereEqualTo("creatorId", creatorId)
                .get();
    }

    public Task<QuerySnapshot> getDonationListBasedOnCategory(String category) {
        return firestore.collection("donations")
                .whereEqualTo("category", category)
                .get();
    }

    public Task<QuerySnapshot> getDonationsIn(List<String> donationIdList) {
        return firestore.collection("donations")
                .whereIn("__name__", donationIdList)
                .get();
    }
}
