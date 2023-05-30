package com.example.navogation_with_pages.ui.home;

import android.app.ProgressDialog;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.navogation_with_pages.ui.object_classes.Zone;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class HomeViewModel extends ViewModel {


    private MutableLiveData<ArrayList<Zone>> mZones;
    private final FirebaseFirestore DB =  FirebaseFirestore.getInstance();
    private final StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private ListenerRegistration zoneListener;

    public HomeViewModel() {

        mZones = new MutableLiveData<>();
        loadZones();
        listenForZoneChanges();
    }

    public LiveData<ArrayList<Zone>> getZones() {
        return mZones;
    }

    private void loadZones() {
        DB.collection("zones").get().addOnSuccessListener(queryDocumentSnapshots -> {
            ArrayList<Zone> zones = new ArrayList<>();
            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                Zone zone = documentSnapshot.toObject(Zone.class);
                StorageReference zoneRef = storageRef.child("zones/" + zone.getZoneID() + "/zone.jpg");
                zoneRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    zone.setImageUri(uri);
                    zones.add(zone);
                    // Notify observers about the updated zones list
                    mZones.setValue(zones);
                }).addOnFailureListener(e -> {
                    Log.e("HomeViewModel", "Error getting download URL for zone: " + zone.getZoneID(), e);
                    // Add the zone even if the image URL retrieval failed
                    zones.add(zone);
                    // Notify observers about the updated zones list
                    mZones.setValue(zones);
                });
            }
        }).addOnFailureListener(e -> {
            Log.e("HomeViewModel", "Error loading zones", e);
        });
    }


    //Checks if a new zone is added to the database and reads it so that zones will be added in real time
    private void listenForZoneChanges() {
        zoneListener = DB.collection("zones").addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (e != null) {
                Log.e("HomeViewModel", "Error listening for zone changes", e);
                return;
            }

            if (queryDocumentSnapshots != null) {
                ArrayList<Zone> zones = mZones.getValue();

                // Initialize the zones ArrayList if it's null
                if (zones == null) {
                    zones = new ArrayList<>();
                }

                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                    Zone zone = documentChange.getDocument().toObject(Zone.class);

                    switch (documentChange.getType()) {
                        case ADDED:
                            if (!zones.contains(zone)) {
                                zones.add(zone);
                            }
                            break;
                        case MODIFIED:
                            int index = zones.indexOf(zone);
                            if (index != -1) {
                                zones.set(index, zone);
                            }
                            break;
                        case REMOVED:
                            zones.remove(zone);
                            break;
                    }
                }

                mZones.setValue(zones);
            }
        });
    }




//removes the Firestore Snapshot Listener when the ViewModel is no longer in use to avoid potential memory leaks.
    @Override
    protected void onCleared() {
        if (zoneListener != null) {
            zoneListener.remove();
        }
        super.onCleared();
    }

}
