package com.example.navogation_with_pages.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.navogation_with_pages.Zone;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Zone>> mZones;

    public HomeViewModel() {
        mZones = new MutableLiveData<>();
        mZones.setValue(new ArrayList<Zone>());
    }

    public LiveData<ArrayList<Zone>> getZones() {
        return mZones;
    }

    public void loadZones(ArrayList<Zone> zonesToAdd) {
        ArrayList<Zone> zones = mZones.getValue();
        if (zones == null) {
            zones = new ArrayList<>();
        }
        zones.addAll(zonesToAdd);
        mZones.setValue(zones);
    }
    public void addZone(Zone zoneToAdd) {
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
    }
}


