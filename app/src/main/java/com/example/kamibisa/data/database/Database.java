package com.example.kamibisa.data.database;

import com.example.kamibisa.data.database.dao.CharityDao;
import com.example.kamibisa.data.database.dao.UserDao;
import com.google.firebase.firestore.FirebaseFirestore;

public class Database {
    private static Database instance;

    private UserDao userDao;
    private CharityDao charityDao;

    public Database() {
        this.userDao = new UserDao();
        this.charityDao = new CharityDao();
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

    public CharityDao getCharityDao() {
        return charityDao;
    }
}