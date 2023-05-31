package com.example.navogation_with_pages.ui.object_classes;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

public class Notification
{
    User interacorUser;
    User interactedUser;
    String zoneID;
    String info;
    String strImageView;
    boolean zoneRequest;

    public Notification(User interacorUser, User interactedUser, Zone zone)
    {
        this.zoneRequest = true;
        Notification.this.interacorUser = interacorUser;
        Notification.this.strImageView = interacorUser.getProfilePicture();
        Notification.this.info = interacorUser.getUsername() + " sent you a zone request for " + zone.getName();
        Notification.this.zoneID = zone.getZoneID();
        Notification.this.interactedUser = interactedUser;
        uploadToFirebase();

    }
    private void uploadToFirebase() {
        FirebaseFirestore.getInstance().collection("notifications").add(this)
                .addOnSuccessListener(documentReference -> {
                    String zoneId = documentReference.getId();
                    Log.d("TAG", "Notification added with ID: " + zoneId);
                })
                .addOnFailureListener(e -> {
                    Log.w("TAG", "Error adding notification", e);
                });
    }

    public Notification(User user, User interactedUser)
    {
        this.zoneRequest = false;
        Notification.this.interacorUser = user;
        Notification.this.strImageView = user.getProfilePicture();
        Notification.this.info = "sent you a friends request!";
        Notification.this.interactedUser = interactedUser;
        uploadToFirebase();
    }

    public Notification() {}

    public String getZoneID(){
        return this.zoneID;
    }
    public String getInfo() { return this.info; }
    public User getInteracorUser() { return this.interacorUser; }
    public User getInteractedUser() { return  this.interactedUser; }

    public String getStrImageView() { return this.strImageView; }
    public boolean isZoneRequest() {return zoneRequest; }
}
