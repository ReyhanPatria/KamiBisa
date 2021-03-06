package com.example.kamibisa.data.repository;

import com.example.kamibisa.data.database.dao.DonationDao;
import com.example.kamibisa.data.model.Donation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

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

    public Task<Void> donateToDonation(String donationId, Integer amount) {
        return donationDao.donateToDonation(donationId, amount);
    }

    public Task<DocumentSnapshot> getDonation(String id) {
        return donationDao.getDonation(id);
    }

    public Task<QuerySnapshot> getDonationListBasedOnCreatorId(String creatorId) {
        return donationDao.getDonationListBasedOnCreatorId(creatorId);
    }

    public Task<QuerySnapshot> getDonationListBasedOnCategory(String category) {
        return donationDao.getDonationListBasedOnCategory(category);
    }

    public Task<QuerySnapshot> getDonationListBasedOnTitle(String title) {
        return donationDao.getDonationListBasedOnTitle(title);
    }

    public Task<QuerySnapshot> getDonationsIn(List<String> donationIdList) {
        return donationDao.getDonationsIn(donationIdList);
    }

    public Task<QuerySnapshot> getAllDonation() {
        return donationDao.getAllDonationList();
    }

    public Task<QuerySnapshot> getActiveDonation() {
        return donationDao.getActiveDonationList();
    }
}
