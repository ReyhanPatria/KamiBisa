package com.example.kamibisa.data.repository;

import com.example.kamibisa.data.database.dao.CharityDao;
import com.example.kamibisa.data.model.Charity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QuerySnapshot;

public class CharityRepository {
    private static CharityRepository instance;

    private CharityDao charityDao;

    public CharityRepository(CharityDao charityDao) {
        this.charityDao = charityDao;
    }

    public static CharityRepository getInstance(CharityDao charityDao) {
        if(instance == null) {
            instance = new CharityRepository(charityDao);
        }
        return instance;
    }

    public Task<DocumentReference> insertCharity(Charity charity) {
        return charityDao.insertCharity(charity);
    }

    public Task<QuerySnapshot> getAllCharityList() {
        return charityDao.getAllCharity();
    }
}
