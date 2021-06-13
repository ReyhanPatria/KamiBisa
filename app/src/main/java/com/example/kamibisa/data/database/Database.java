package com.example.kamibisa.data.database;

import com.example.kamibisa.data.database.dao.DonationDao;
import com.example.kamibisa.data.database.dao.UserDao;

public class Database {
    private static Database instance;

    private UserDao userDao;
    private DonationDao donationDao;

    public Database() {
        this.userDao = new UserDao();
        this.donationDao = new DonationDao();
    }

    public static Database getInstance() {
        if(instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public DonationDao getDonationDao() {
        return donationDao;
    }
}