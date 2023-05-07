package com.example.navogation_with_pages;

import java.util.Date;

public class Zone {
    private String name;
    private int quota;
    private String dateAndTime;
    private String details;
    private String location;
    private int securityID;
    private String imageUrl;

    public Zone(String name, int quota, String dateAndTime, String details, String location, int securityID, String imageUrl) {
        this.name = name;
        this.quota = quota;
        this.dateAndTime = dateAndTime;
        this.details = details;
        this.location = location;
        this.securityID = securityID;
        this.imageUrl = imageUrl;
    }
    public Zone(){}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
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

    public int getSecurity() {
        return securityID;
    }

    public void setSecurity(int security) {
        this.securityID = security;
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
                ", security='" + securityID + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}

