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

/**
 * ViewModel class for Home View.
 *
 * This class holds the UI data for Home View and allows the communication of HomeFragment
 * with the underlying data sources, following the design principle of separation of concerns
 * and the architectural pattern Model-View-ViewModel (MVVM).
 *
 * It handles querying the Firestore database for the Zone data, listens for changes in the Zone data,
 * and updates the LiveData object that holds the Zone list accordingly.
 */

public class HomeViewModel extends ViewModel {

    /**
     * Live data holding the list of zones.
     */
    private MutableLiveData<ArrayList<Zone>> mZones;

    /**
     * Firestore instance to access Firestore database.
     */
    private final FirebaseFirestore DB =  FirebaseFirestore.getInstance();

    /**
     * Firebase Storage reference to access Firebase Storage.
     */
    private final StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    /**
     * Firestore listener registration for listening to changes in Firestore.
     */
    private ListenerRegistration zoneListener;

    /**
     * ArrayList holding the list of zones.
     */
    ArrayList<Zone> zonesArr;

    /**
     * Constructor of HomeViewModel.
     *
     * Initializes the live data and array list of zones, loads the zones from Firestore, and listens for zone changes.
     */
    public HomeViewModel() {

        mZones = new MutableLiveData<>();
        zonesArr = new ArrayList<>();
        loadZones();
        listenForZoneChanges();
    }

    /**
     * Get method for the live data of zones.
     *
     * @return LiveData of ArrayList of Zones.
     */
    public LiveData<ArrayList<Zone>> getZones() {
        return mZones;
    }

    /**
     * Method for loading zones from Firestore.
     */
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
            zonesArr = zones;
        }).addOnFailureListener(e -> {
            Log.e("HomeViewModel", "Error loading zones", e);
        });
    }

    /**
     * Get method for the array list of zones.
     *
     * @return ArrayList of Zones.
     */
    public ArrayList<Zone> getZonesArr() {
        return zonesArr;
    }

    /**
     * Method for listening for changes in Firestore.
     *
     * This method listens for added, modified, and removed zones in Firestore and updates the live data of zones accordingly.
     */
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




    /**
     * OnCleared method of HomeViewModel.
     *
     * This method removes the Firestore snapshot listener when the ViewModel is no longer in use to avoid potential memory leaks.
     */
    @Override
    protected void onCleared() {
        if (zoneListener != null) {
            zoneListener.remove();
        }
        super.onCleared();
    }

}
