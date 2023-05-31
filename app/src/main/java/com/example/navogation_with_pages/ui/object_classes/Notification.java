package com.example.navogation_with_pages.ui.object_classes;

import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class Notification
{
    User interacorUser;
    User interactedUser;
    String zoneID;
    String info;

    String ID;
    String strImageView;
    boolean zoneRequest;

    public Notification(User interacorUser, User interactedUser, Zone zone)
    {
        this.zoneRequest = true;
        Notification.this.interacorUser = interacorUser;
        Notification.this.strImageView = interacorUser.getProfilePicture();
        Notification.this.info = interacorUser.getUsername() + " sent you a zone request for " + zone.getName();
        Notification.this.ID = "Z" + interacorUser.getID() + interactedUser.getID();
        Notification.this.zoneID = zone.getZoneID();
        Notification.this.interactedUser = interactedUser;
        uploadToFirebase();

    }
    private void uploadToFirebase() {
        if(this.zoneRequest){
            FirebaseFirestore.getInstance().collection("notifications").document(this.ID).set(this).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d("TAG", "Notification added with ID: " + " Z" + interacorUser.getID() + " " +interactedUser.getID());
                }
            });
        }
        else{
            FirebaseFirestore.getInstance().collection("notifications").document(this.ID).set(this).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d("TAG", "Notification added with ID: " + " F" + interacorUser.getID() + " " +  interactedUser.getID());
                }
            });
        }

    }

    public void delete(){
        // Create a reference to the document you want to delete
        String type;
        if(this.isZoneRequest()){
            type = "Z";
        }
        else{
            type = "F";
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("notifications").document(type  + this.interacorUser.getID() + this.interactedUser.getID())
                .delete();
    }

    public Notification(User user, User interactedUser)
    {
        this.zoneRequest = false;
        Notification.this.interacorUser = user;
        Notification.this.strImageView = user.getProfilePicture();
        Notification.this.info = "sent you a friends request!";
        Notification.this.ID = "F" + interacorUser.getID() + interactedUser.getID();
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
