package com.example.kamibisa.data.model;

import com.google.firebase.firestore.Exclude;

import java.util.Date;

public class DonationRecord {
    private static final String TAG = "DonationRecord";

    @Exclude
    private String id;

    private String userId;
    private String donationId;
    private Integer donationAmount;
    private Date donationMadeDate;

    public DonationRecord() {
        // Required empty constructor
    }

    public DonationRecord(String userId, String donationId, Integer donationAmount,
                          Date donationMadeDate) {
        this.userId = userId;
        this.donationId = donationId;
        this.donationAmount = donationAmount;
        this.donationMadeDate = donationMadeDate;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getDonationId() {
        return donationId;
    }

    public Integer getDonationAmount() {
        return donationAmount;
    }

    public Date getDonationMadeDate() {
        return donationMadeDate;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setDonationId(String donationId) {
        this.donationId = donationId;
    }

    public void setDonationAmount(Integer donationAmount) {
        this.donationAmount = donationAmount;
    }

    public void setDonationMadeDate(Date donationMadeDate) {
        this.donationMadeDate = donationMadeDate;
    }
}
