package com.example.kamibisa.data.repository;

import com.example.kamibisa.data.database.dao.BloodDonationDao;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class BloodDonationRepository {
    private static BloodDonationRepository instance;

    private BloodDonationDao bloodDonationDao;

    public BloodDonationRepository(BloodDonationDao bloodDonationDao) {
        this.bloodDonationDao = bloodDonationDao;
    }

    public static BloodDonationRepository getInstance(BloodDonationDao bloodDonationDao) {
        if(instance == null) {
            instance = new BloodDonationRepository(bloodDonationDao);
        }
        return instance;
    }

    public Task<QuerySnapshot> getAllBloodDonation() {
        return bloodDonationDao.getAllBloodDonation();
    }

    public Task<DocumentSnapshot> getBloodDonation(String id) {
        return bloodDonationDao.getBloodDonation(id);
    }
}
