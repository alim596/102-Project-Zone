package com.example.navogation_with_pages.ui.object_classes;

import java.util.ArrayList;

public class Zone {
    private String name;
    private int quota;
    private String dateAndTime;
    private String details;
    private String location;
    private int securityID;
    private String imageUrl;
    private ArrayList<User> participants;
    private String category;
    private ArrayList<String> participantsNames;

    public Zone(String name, int quota, String dateAndTime, String details, String location, int securityID, String imageUrl, String category) {
        this.name = name;
        this.quota = quota;
        this.dateAndTime = dateAndTime;
        this.details = details;
        this.location = location;
        this.securityID = securityID;
        this.imageUrl = imageUrl;
        this.category = category;
        this.participants = new ArrayList<>();
        this.participantsNames = new ArrayList<>();
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

    public String getDateAndTime() {
        return dateAndTime;
    }

    public String getDetails() {
        return details;
    }
    public String getCategory(){return category;}
    public ArrayList<User> getParticipants(){ return participants; }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLocation() {
        return location;
    }

    public int getSecurity() {
        return securityID;
    }

    public void setQuota(int quota) { this.quota = quota; }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSecurity(int security) {
        this.securityID = security;
    }


    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void addParticipant(User participant) {
        participants.add(participant);
        participantsNames.add(participant.getUsername());

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

