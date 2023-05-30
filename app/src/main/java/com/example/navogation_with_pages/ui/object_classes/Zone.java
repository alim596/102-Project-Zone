package com.example.navogation_with_pages.ui.object_classes;

import android.net.Uri;

import java.util.ArrayList;
import java.util.UUID;

public class Zone {
    private String name;
    private int quota;
    private String dateAndTime;
    private String details;
    private String location;
    private String imageUriStr;
    private ArrayList<User> participants;
    private String category;
    private String zoneID;

    private ArrayList<String> participantsNames;

    public Zone(String name, int quota, String dateAndTime, String details, String location, String category) {
        this.name = name;
        this.quota = quota;
        this.dateAndTime = dateAndTime;
        this.details = details;
        this.location = location;
        this.category = category;
        this.participants = new ArrayList<>();
        this.participantsNames = new ArrayList<>();
        this.zoneID = UUID.randomUUID().toString();
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

    public Uri getImageUri() {
        if(this.imageUriStr != null && !this.imageUriStr.isEmpty()) {
            return Uri.parse(this.imageUriStr);
        } else {
            return null;
        }
    }


    public String getZoneID(){
        return zoneID;
    }

    public String getLocation() {
        return location;
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

    public void setImageUri(Uri imageUri) {
        if(imageUri != null) {
            this.imageUriStr = imageUri.toString();
        } else {
            this.imageUriStr = null;
        }
    }

    public void setZoneID(String zoneID) {
        this.zoneID = zoneID;
    }

    public void addParticipant(User participant) {
        participants.add(participant);
        participantsNames.add(participant.getUsername());

    }

}

