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

