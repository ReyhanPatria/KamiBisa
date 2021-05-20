package com.example.kamibisa.data.model;

import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

public class User {
    // NON-STATIC ATTRIBUTES
    private FirebaseUser firebaseUser;
    private String username;
    private String email;
    private Date dob;
    private String phone;





    // NON-STATIC FUNCTIONS
    public User(String username, String email, Date dob, String phone) {
        this.username = username;
        this.email = email;
        this.dob = dob;
        this.phone = phone;
    }





    // SETTER & GETTER
    public FirebaseUser getFirebaseUser() { return firebaseUser; }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Date getDob() {
        return dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setFirebaseUser(FirebaseUser firebaseUser) { this.firebaseUser = firebaseUser; }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
