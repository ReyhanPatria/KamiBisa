package com.example.kamibisa.data.model;

import android.util.Log;
import android.util.Patterns;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.Exclude;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

public class User {
    public static String TAG = "User";

    // NON-STATIC ATTRIBUTES
    @Exclude
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
    @Exclude
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

    @Exclude
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





    // VALIDATION FUNCTIONS
    public static Boolean validatePasswordLength(String password) {
        int len = password.length();
        return (10 <= len && len <= 14);
    }

    public static Boolean validatePasswordHasDigit(String password) {
        for(char c: password.toCharArray()) {
            if(Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    public static Boolean verifyPassword(String password, String verifyPassword) {
        return password.equals(verifyPassword);
    }

    public static Boolean validateEmail(String email) {
        return (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public static Boolean validateDob(Date dob) {
        boolean isValid = false;
        try {
            String todayString = DateFormat.getDateInstance().format(Date.from(Instant.now()));
            Date today = DateFormat.getDateInstance().parse(todayString);
            isValid = dob.before(today);
        }
        catch(ParseException e) {
            Log.e(TAG, e.getMessage());
        }

        return isValid;
    }

    public static Boolean validatePhone(String phone) {
        return (!phone.isEmpty() && Patterns.PHONE.matcher(phone).matches());
    }
}
