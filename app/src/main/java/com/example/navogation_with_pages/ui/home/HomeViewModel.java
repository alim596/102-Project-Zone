package com.example.navogation_with_pages.ui.home;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.navogation_with_pages.Zone;
import com.example.navogation_with_pages.ui.add.AddFragment;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EventListener;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Zone>> mZones;

    private MutableLiveData<FirebaseFirestore> db = new MutableLiveData<>(FirebaseFirestore.getInstance());
    private CollectionReference zoneCollection;


    public HomeViewModel() {
        mZones = new MutableLiveData<>();
        zoneCollection = db.getValue().collection("zones");
        loadZones();
    }

    public LiveData<FirebaseFirestore> getDB() {
        return db;
    }
    public LiveData<ArrayList<Zone>> getZones() {
        return mZones;
    }

    private void loadZones() {
        zoneCollection.get().addOnSuccessListener(queryDocumentSnapshots -> {
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
}



//Probably not Necessary
    /*public void addZone(Zone zoneToAdd) {
        ArrayList<Zone> zones = mZones.getValue();
        if (zones == null) {
            zones = new ArrayList<>();
        }
        for (Zone zone : zones) {
            if (zone.getName().equals(zoneToAdd.getName())) {
                // A zone with the same name already exists, don't add it
                return;
            }
        }
        zones.add(zoneToAdd);
        mZones.setValue(zones);
    }*/


    /*private void loadZones() {
        ArrayList<Zone> zones = new ArrayList<Zone>();
        zones.add(new Zone("Abdulaleem", 5, "Thursday 05/06", "Lets go to the Mall together and have some fun!!",
                "Kent Park", 0000, "mall.png"));
        zones.add(new Zone("Orhun", 3, "Thursday 05/06", "We are going to play Basketball and we need 5 more people. You can join if you have ever touched a basketball before.",
                "Main Campus Sports Hall", 0000, "basketball.png"));
        zones.add(new Zone("Irmak", 7, "Thursday 05/06", "We will be drinking coffee together in Coffee break. Only for CS students!",
                "Coffee Break", 0000, "coffeeBreak.png"));
        zones.add(new Zone("Fazlı", 10, "Thursday 05/06", "We are planning a football game and anyone is welcomed!",
                "Main Campus Football Field", 0000, "football.png"));
        mZones.setValue(zones);
    }*/

