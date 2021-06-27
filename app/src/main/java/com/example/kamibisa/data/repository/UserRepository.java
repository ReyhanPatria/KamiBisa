package com.example.kamibisa.data.repository;

import com.example.kamibisa.data.database.dao.UserDao;
import com.example.kamibisa.data.model.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentSnapshot;

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

    public Task<DocumentSnapshot> getUserData(String id) {
        return userDao.getUserData(id);
    }

    public Task<Void> setUserData(User user) {
        return userDao.setUserData(user);
    }

    public Task<AuthResult> registerUser(User user, String password) {
        return userDao.registerUser(user, password);
    }

    public Task<AuthResult> login(String email, String password) {
        return userDao.login(email, password);
    }

    public UserDao getUserDao() {
        return userDao;
    }
}
