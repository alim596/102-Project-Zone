package com.example.navogation_with_pages.ui.object_classes;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

/**
 * Zone class represents a Zone in the application.
 *
 * A Zone is a defined area or region that is used in this context to identify a certain location
 * for an event or activity. It contains details such as name, quota, date and time,
 * details of the event, location, image URI, list of participants, category, and a unique zone ID.
 */
public class Zone {

    /**
     * Name of the Zone.
     */
    private String name;

    /**
     * Quota of the Zone, represents the maximum number of participants allowed.
     */
    private int quota;

    /**
     * Date and time of the event in the Zone.
     */
    private String dateAndTime;

    /**
     * Detailed information about the Zone.
     */
    private String details;

    /**
     * The geographical location of the Zone.
     */
    private String location;

    /**
     * URI of the image representing the Zone.
     */
    private String imageUriStr;

    /**
     * List of users participating in the Zone.
     */
    private ArrayList<User> participants;

    /**
     * Category of the Zone.
     */
    private String category;

    /**
     * Unique identifier of the Zone.
     */
    private String zoneID;

    /**
     * List of the names of participants.
     */
    private ArrayList<String> participantsNames;

    /**
     * Constructor for Zone with parameters.
     *
     * @param name Name of the Zone.
     * @param quota Quota of the Zone.
     * @param dateAndTime Date and time of the event in the Zone.
     * @param details Detailed information about the Zone.
     * @param location The geographical location of the Zone.
     * @param category Category of the Zone.
     */
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

    /**
     * Default constructor for the Zone class.
     */
    public Zone(){}

    /**
     * Static method to get a Zone by its ID.
     *
     * @param ID The ID of the Zone.
     * @param listener Callback listener for when the Zone is retrieved.
     */
    public static void getZone(String ID, final OnGetZoneListener listener){
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();


        String userID = ID;


        DocumentReference documentReference = fStore.collection("zones").document(userID);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                Map<String, Object> snapshotValue = task.getResult().getData();
                Zone zone = new Zone();
                if(snapshotValue == null){
                    listener.onSuccess(null);
                }
                else{
                    zone.location = (String) snapshotValue.get("location");
                    zone.dateAndTime = (String) snapshotValue.get("dateAndTime");
                    zone.details = (String) snapshotValue.get("details");
                    zone.category = (String) snapshotValue.get("category");
                    zone.imageUriStr = null;
                    zone.name = (String) snapshotValue.get("name");
                    zone.participants = (ArrayList<User>)snapshotValue.get("participants");
                    if(snapshotValue.get("quota").getClass().equals(Long.class)){
                        zone.quota = ((Long)snapshotValue.get("quota")).intValue();
                    }
                    else {
                        zone.quota = (int)snapshotValue.get("quota");
                    }

                    zone.zoneID = (String)(snapshotValue.get("zoneID"));
                    listener.onSuccess(zone);
                }

            }
        });
    }

    /**
     * Gets the name of the Zone.
     * @return String representing the name of the Zone.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the Zone.
     * @param name The new name of the Zone.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the quota of the Zone.
     * @return int representing the quota of the Zone.
     */
    public int getQuota() {
        return quota;
    }

    /**
     * Retrieves the date and time of the event in the Zone.
     * @return String representing the date and time of the event in the Zone.
     */
    public String getDateAndTime() {
        return dateAndTime;
    }

    /**
     * Retrieves the detailed information about the Zone.
     * @return String representing the details about the Zone.
     */
    public String getDetails() {
        return details;
    }

    /**
     * Retrieves the category of the Zone.
     * @return String representing the category of the Zone.
     */
    public String getCategory(){
        return category;
    }

    /**
     * Retrieves the list of users participating in the Zone.
     * @return ArrayList of User objects representing the participants of the Zone.
     */
    public ArrayList<User> getParticipants(){
        return participants;
    }

    /**
     * Retrieves the URI of the image representing the Zone.
     * @return Uri object representing the URI of the Zone's image.
     */
    public Uri getImageUri() {
        if(this.imageUriStr != null && !this.imageUriStr.isEmpty()) {
            return Uri.parse(this.imageUriStr);
        } else {
            return null;
        }
    }

    /**
     * Retrieves the unique identifier of the Zone.
     * @return String representing the unique identifier of the Zone.
     */
    public String getZoneID(){
        return zoneID;
    }

    /**
     * Retrieves the geographical location of the Zone.
     * @return String representing the geographical location of the Zone.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the URI of the image representing the Zone.
     * @param imageUri Uri object representing the URI of the image to be set.
     */
    public void setImageUri(Uri imageUri) {
        if(imageUri != null) {
            this.imageUriStr = imageUri.toString();
        } else {
            this.imageUriStr = null;
        }
    }


    /**
     * Updates the participants list of a specific zone by adding a new participant.
     *
     * @param zoneID The ID of the Zone.
     * @param newParticipant The new participant to be added.
     */
    public void updateParticipants(String zoneID, User newParticipant) {
        DocumentReference zoneRef = FirebaseFirestore.getInstance().collection("zones").document(zoneID);

        zoneRef.update("participants", FieldValue.arrayUnion(newParticipant))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("TAG", "Participant successfully added!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding participant", e);
                    }
                });
    }

    /**
     * Adds a new participant to the Zone.
     *
     * @param participant The participant to be added.
     */
    public void addParticipant(User participant) {
        if(participants == null){
            participants = new ArrayList<>();
            participants.add(participant);
        }
        else{
            participants.add(participant);
        }
        if(participantsNames == null){
            participantsNames = new ArrayList<>();
            participantsNames.add(participant.getUsername());
        }
        else{
            participantsNames.add(participant.getUsername());
        }


        updateParticipants(this.getZoneID(),participant);
    }

}

