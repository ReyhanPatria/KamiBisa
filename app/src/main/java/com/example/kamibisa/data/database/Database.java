package com.example.kamibisa.data.database;

import com.example.kamibisa.data.database.dao.UserDao;
import com.google.firebase.firestore.FirebaseFirestore;

public class Database {
    private static Database instance;

    private UserDao userDao;

    public Database() {
        userDao = new UserDao();
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
}