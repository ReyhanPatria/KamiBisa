package com.example.kamibisa.data.repository;

import com.example.kamibisa.data.database.dao.UserDao;
import com.example.kamibisa.data.model.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class UserRepository {
    private static UserRepository instance;

    private UserDao userDao;

    public UserRepository(UserDao userDao) {
        this.userDao = userDao;
    }

    public static UserRepository getInstance(UserDao userDao) {
        if(instance == null) {
            instance = new UserRepository(userDao);
        }
        return instance;
    }

    public void insertUserData(User user) {
        userDao.insertUserData(user);
    }

    public Task<AuthResult> registerUser(User user, String password) {
        return userDao.registerUser(user, password);
    }

    public UserDao getUserDao() {
        return userDao;
    }
}
