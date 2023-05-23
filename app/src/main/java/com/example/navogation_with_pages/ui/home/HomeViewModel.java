package com.example.navogation_with_pages.ui.home;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.navogation_with_pages.Zone;
import com.example.navogation_with_pages.ui.add.AddFragment;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EventListener;
import java.util.Objects;


public class HomeViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Zone>> mZones;
    private final FirebaseFirestore DB =  FirebaseFirestore.getInstance();
    private ListenerRegistration zoneListener;

    public HomeViewModel() {
        mZones = new MutableLiveData<>();
        loadZones();
        listenForZoneChanges();
    }

    public FirebaseFirestore getDB() {
        return DB;
    }

    public LiveData<ArrayList<Zone>> getZones() {
        return mZones;
    }

    private void loadZones() {
        DB.collection("zone").get().addOnSuccessListener(queryDocumentSnapshots -> {
            ArrayList<Zone> zones = new ArrayList<>();
            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                Zone zone = documentSnapshot.toObject(Zone.class);
                zones.add(zone);
            }
            mZones.setValue(zones);
        }).addOnFailureListener(e -> {
            Log.e("HomeViewModel", "Error loading zones", e);
        });
    }

    //Checks if a new zone is added to the database and reads it so that zones will be added in real time
    private void listenForZoneChanges() {
        zoneListener = DB.collection("zone").addSnapshotListener((queryDocumentSnapshots, e) -> {
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
