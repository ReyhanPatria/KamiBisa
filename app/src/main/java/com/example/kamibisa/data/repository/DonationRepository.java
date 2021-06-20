package com.example.kamibisa.data.repository;

import com.example.kamibisa.data.database.dao.DonationDao;
import com.example.kamibisa.data.model.Donation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DonationRepository {
    private static DonationRepository instance;

    private DonationDao donationDao;

    public DonationRepository(DonationDao donationDao) {
        this.donationDao = donationDao;
    }

    public static DonationRepository getInstance(DonationDao donationDao) {
        if(instance == null) {
            instance = new DonationRepository(donationDao);
        }
        return instance;
    }

    public Task<DocumentReference> insertDonation(Donation donation) {
        return donationDao.insertDonation(donation);
    }

    public Task<DocumentSnapshot> getDonation(String id) {
        return donationDao.getDonation(id);
    }

    public Task<QuerySnapshot> getAllDonation() {
        return donationDao.getAllDonation();
    }

    public Task<QuerySnapshot> getCurrentDonation() {
        return donationDao.getCurrentDonation();
    }
}
