package com.example.navogation_with_pages.ui.object_classes;

import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * This class represents a Notification in the application.
 * It contains information about the user who interacted (interactor),
 * the user who was interacted with, the zoneID if it was a zone request,
 * and the notification details.
 */
public class Notification
{
    User interacorUser;
    User interactedUser;
    String zoneID;
    String info;

    String ID;
    String strImageView;
    boolean zoneRequest;

    /**
     * Constructs a Notification object for a zone request.
     *
     * @param interacorUser   The user who is sending the request.
     * @param interactedUser  The user who the request is sent to.
     * @param zone            The zone for which the request is made.
     */
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

    /**
     * Uploads the Notification data to the Firebase Firestore database.
     */
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

    /**
     * Deletes the Notification from the Firebase Firestore database.
     */
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

    /**
     * Constructs a Notification object for a friend request.
     *
     * @param user            The user who is sending the request.
     * @param interactedUser  The user who the request is sent to.
     */
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

    /**
     * Default constructor for Notification.
     */
    public Notification() {}

    /**
     * Gets the ID of the zone associated with this Notification.
     *
     * @return The ID of the zone.
     */
    public String getZoneID(){
        return this.zoneID;
    }

    /**
     * Gets the info associated with this Notification.
     *
     * @return The info associated with this Notification.
     */
    public String getInfo() { return this.info; }

    /**
     * Gets the User object of the interactor.
     *
     * @return The User object of the interactor.
     */
    public User getInteracorUser() { return this.interacorUser; }

    /**
     * Gets the User object of the user who was interacted with.
     *
     * @return The User object of the user who was interacted with.
     */
    public User getInteractedUser() { return  this.interactedUser; }

    /**
     * Gets the URL of the profile picture of the interactor.
     *
     * @return The URL of the profile picture of the interactor.
     */
    public String getStrImageView() { return this.strImageView; }

    /**
     * Checks if this Notification is a zone request.
     *
     * @return true if it is a zone request, false if it's not.
     */
    public boolean isZoneRequest() {return zoneRequest; }
}