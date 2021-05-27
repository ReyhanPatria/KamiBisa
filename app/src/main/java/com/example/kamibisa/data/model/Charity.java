package com.example.kamibisa.data.model;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Charity {
    private String title;
    private Date createdAt;
    private Integer targetAmount;
    private Integer gatheredAmount;

    public Charity(String title, Date createdAt, Integer targetAmount, Integer gatheredAmount) {
        this.title = title;
        this.createdAt = createdAt;
        this.targetAmount = targetAmount;
        this.gatheredAmount = gatheredAmount;
    }

    public String getTitle() {
        return title;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Integer getTargetAmount() {
        return targetAmount;
    }

    public Integer getGatheredAmount() {
        return gatheredAmount;
    }

    public long getDaysLeft() {
        Date currentDate = Date.from(Instant.now());

        long differenceInMilliseconds = createdAt.getTime() - currentDate.getTime();
        long differenceInDays = TimeUnit.MILLISECONDS.toDays(differenceInMilliseconds);

        return differenceInDays;
    }
}
