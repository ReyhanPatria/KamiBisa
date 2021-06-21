package com.example.kamibisa.data.model;

import com.google.firebase.firestore.Exclude;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BloodDonation {
    @Exclude
    private String id;

    private String creatorName;
    private String creatorJob;
    private String phone;
    private String socialMedia;
    private String beneficiaryRelation;
    private String beneficiaryName;
    private String beneficiaryBloodType;
    private String location;
    private String link;
    private Date createdDate;
    private Date finishedDate;

    public BloodDonation() {
        // Required empty constructor
    }

    public BloodDonation(String creatorName, String creatorJob, String phone, String socialMedia,
                         String beneficiaryRelation, String beneficiaryName, String beneficiaryBloodType,
                         String location, String link, Date createdDate, Date finishedDate) {
        this.creatorName = creatorName;
        this.creatorJob = creatorJob;
        this.phone = phone;
        this.socialMedia = socialMedia;
        this.beneficiaryRelation = beneficiaryRelation;
        this.beneficiaryName = beneficiaryName;
        this.beneficiaryBloodType = beneficiaryBloodType;
        this.location = location;
        this.link = link;
        this.createdDate = createdDate;
        this.finishedDate = finishedDate;
    }

    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
    public long getDaysPassed() {
        Date currentDate = Date.from(Instant.now());

        long differenceInMilliseconds = createdDate.getTime() - currentDate.getTime();
        long differenceInDays = TimeUnit.MILLISECONDS.toDays(differenceInMilliseconds);

        return differenceInDays;
    }

    @Exclude
    public long getDaysLeft() {
        Date currentDate = Date.from(Instant.now());

        long differenceInMilliseconds = finishedDate.getTime() - currentDate.getTime();
        long differenceInDays = TimeUnit.MILLISECONDS.toDays(differenceInMilliseconds);

        return differenceInDays;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public String getCreatorJob() {
        return creatorJob;
    }

    public String getPhone() {
        return phone;
    }

    public String getSocialMedia() {
        return socialMedia;
    }

    public String getBeneficiaryRelation() {
        return beneficiaryRelation;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public String getBeneficiaryBloodType() {
        return beneficiaryBloodType;
    }

    public String getLocation() {
        return location;
    }

    public String getLink() {
        return link;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getFinishedDate() {
        return finishedDate;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public void setCreatorJob(String creatorJob) {
        this.creatorJob = creatorJob;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSocialMedia(String socialMedia) {
        this.socialMedia = socialMedia;
    }

    public void setBeneficiaryRelation(String beneficiaryRelation) {
        this.beneficiaryRelation = beneficiaryRelation;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public void setBeneficiaryBloodType(String beneficiaryBloodType) {
        this.beneficiaryBloodType = beneficiaryBloodType;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
    }
}
