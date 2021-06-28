package com.example.kamibisa.data.model;

import android.util.Patterns;

import com.example.kamibisa.R;
import com.google.firebase.firestore.Exclude;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Donation {
    @Exclude
    private String id;

    private String title;
    private String link;
    private Date createdDate;
    private Date finishedDate;
    private String category;
    private Integer targetAmount;
    private Integer gatheredAmount;
    private String description;
    private String introduction;
    private String motivator;
    private String creatorId;
    private String creatorName;
    private String creatorDescription;
    private String institution;
    private String socialMedia;
    private String location;
    private String phone;
    private String beneficiaryName;
    private String beneficiaryRelation;

    public Donation() {
        // Required empty constructor for Firestore
    }

    public Donation(String title, String link, Date createdDate, Date finishedDate,
                    String category, Integer targetAmount, Integer gatheredAmount,
                    String description, String introduction, String motivator, String creatorId,
                    String creatorName, String creatorDescription, String institution,
                    String socialMedia, String location, String phone, String beneficiaryName,
                    String beneficiaryRelation) {
        this.title = title;
        this.link = link;
        this.createdDate = createdDate;
        this.finishedDate = finishedDate;
        this.category = category;
        this.targetAmount = targetAmount;
        this.gatheredAmount = gatheredAmount;
        this.description = description;
        this.introduction = introduction;
        this.motivator = motivator;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.creatorDescription = creatorDescription;
        this.institution = institution;
        this.socialMedia = socialMedia;
        this.location = location;
        this.phone = phone;
        this.beneficiaryName = beneficiaryName;
        this.beneficiaryRelation = beneficiaryRelation;
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

    public static Boolean isTitleValid(String title) {
        return !title.isEmpty();
    }

    public static Boolean isLinkValid(String link) {
        return !link.isEmpty();
    }

    public static Boolean isTargetAmopuntValid(String targetAmount) {
        Integer i = 0;
        try {
            i = Integer.parseInt(targetAmount);
        }
        catch(NumberFormatException e) {
            return false;
        }
        return (i > 500000);
    }

    public static Boolean isDescriptionValid(String description) {
        return !description.isEmpty();
    }

    public static Boolean isIntroductionValid(String introduction) {
        return !introduction.isEmpty();
    }

    public static Boolean isMotivatorValid(String motivator) {
        return !motivator.isEmpty();
    }

    public static Boolean isCreatorNameValid(String creatorName) {
        return !creatorName.isEmpty();
    }

    public static Boolean isCreatorDescriptionValid(String creatorDescription) {
        return !creatorDescription.isEmpty();
    }

    public static Boolean isInstitutionValid(String insttitution) {
        return !insttitution.isEmpty();
    }

    public static Boolean isSocialMediaValid(String socialMedia) {
        return !socialMedia.isEmpty();
    }

    public static Boolean isLocationValid(String location) {
        return !location.isEmpty() && location.length() <= 50;
    }

    public static Boolean isPhoneValid(String phone) {
        return (!phone.isEmpty() && Patterns.PHONE.matcher(phone).matches()
                && (phone.length() >= 10 && phone.length() <= 14));
    }

    public static Boolean isBeneficiaryNameValid(String beneficiaryName) {
        return !beneficiaryName.isEmpty();
    }

    @Exclude
    public String getId() {
        return id;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public String getTitle() {
        return title;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getFinishedDate() {
        return finishedDate;
    }

    public Integer getTargetAmount() {
        return targetAmount;
    }

    public Integer getGatheredAmount() {
        return gatheredAmount;
    }

    public String getLink() {
        return link;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getMotivator() {
        return motivator;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public String getCreatorDescription() {
        return creatorDescription;
    }

    public String getInstitution() {
        return institution;
    }

    public String getSocialMedia() {
        return socialMedia;
    }

    public String getLocation() {
        return location;
    }

    public String getPhone() {
        return phone;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public String getBeneficiaryRelation() {
        return beneficiaryRelation;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
    }

    public void setGatheredAmount(Integer gatheredAmount) {
        this.gatheredAmount = gatheredAmount;
    }

    public void setTargetAmount(Integer targetAmount) {
        this.targetAmount = targetAmount;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setMotivator(String motivator) {
        this.motivator = motivator;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public void setCreatorDescription(String creatorDescription) {
        this.creatorDescription = creatorDescription;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public void setSocialMedia(String socialMedia) {
        this.socialMedia = socialMedia;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public void setBeneficiaryRelation(String beneficiaryRelation) {
        this.beneficiaryRelation = beneficiaryRelation;
    }
}
