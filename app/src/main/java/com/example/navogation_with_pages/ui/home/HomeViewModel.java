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
}
