package com.example.kamibisa.data.model;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Donation {
    private String title;
    private Date createdAt;
    private Date finishedAt;
    private Integer targetAmount;
    private Integer gatheredAmount;

    public Donation() {
        // Required empty constructor for Firestore
    }

    public Donation(String title, Date createdAt, Date finishedAt, Integer targetAmount, Integer gatheredAmount) {
        this.title = title;
        this.createdAt = createdAt;
        this.finishedAt = finishedAt;
        this.targetAmount = targetAmount;
        this.gatheredAmount = gatheredAmount;
    }

    public String getTitle() {
        return title;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getFinishedAt() {
        return finishedAt;
    }

    public Integer getTargetAmount() {
        return targetAmount;
    }

    public Integer getGatheredAmount() {
        return gatheredAmount;
    }

    public long getDaysPassed() {
        Date currentDate = Date.from(Instant.now());

        long differenceInMilliseconds = createdAt.getTime() - currentDate.getTime();
        long differenceInDays = TimeUnit.MILLISECONDS.toDays(differenceInMilliseconds);

        return differenceInDays;
    }

    public long getDaysLeft() {
        Date currentDate = Date.from(Instant.now());

        long differenceInMilliseconds = finishedAt.getTime() - currentDate.getTime();
        long differenceInDays = TimeUnit.MILLISECONDS.toDays(differenceInMilliseconds);

        return differenceInDays;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setFinishedAt(Date finishedAt) {
        this.finishedAt = finishedAt;
    }

    public void setGatheredAmount(Integer gatheredAmount) {
        this.gatheredAmount = gatheredAmount;
    }

    public void setTargetAmount(Integer targetAmount) {
        this.targetAmount = targetAmount;
    }
}
