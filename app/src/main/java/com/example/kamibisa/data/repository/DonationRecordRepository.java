package com.example.kamibisa.data.repository;

import com.example.kamibisa.data.database.dao.DonationRecordDao;
import com.example.kamibisa.data.model.DonationRecord;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

public class DonationRecordRepository {
    private static DonationRecordRepository instance;

    private DonationRecordDao donationRecordDao;

    public DonationRecordRepository(DonationRecordDao donationRecordDao) {
        this.donationRecordDao = donationRecordDao;
    }

    public static DonationRecordRepository getInstance(DonationRecordDao donationRecordDao) {
        if(instance == null) {
            instance = new DonationRecordRepository(donationRecordDao);
        }
        return instance;
    }

    public Task<DocumentReference> insertDonationRecord(DonationRecord donationRecord) {
        return this.donationRecordDao.insertDonationRecord(donationRecord);
    }
}
