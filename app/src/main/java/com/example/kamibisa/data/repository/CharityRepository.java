package com.example.kamibisa.data.repository;

import com.example.kamibisa.data.database.dao.CharityDao;

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
}
