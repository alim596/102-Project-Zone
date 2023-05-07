package com.example.navogation_with_pages;

import java.util.Date;

public class Zone {
    private String name;
    private String quota;
    private Date dateAndTime;
    private String details;
    private String location;
    private String security;
    private String imageUrl;

    public Zone(String name, String quota, Date dateAndTime, String details, String location, String security, String imageUrl) {
        this.name = name;
        this.quota = quota;
        this.dateAndTime = dateAndTime;
        this.details = details;
        this.location = location;
        this.security = security;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuota() {
        return quota;
    }

    public void setQuota(String quota) {
        this.quota = quota;
    }

    public Date getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Date dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Zone{" +
                "name='" + name + '\'' +
                ", quota='" + quota + '\'' +
                ", dateAndTime=" + dateAndTime +
                ", details='" + details + '\'' +
                ", location='" + location + '\'' +
                ", security='" + security + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}

