package com.example.navogation_with_pages.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.navogation_with_pages.Zone;

import java.util.ArrayList;
import java.util.Date;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Zone>> mZones;

    public HomeViewModel() {
        mZones = new MutableLiveData<>();
        loadZones();
    }

    public LiveData<ArrayList<Zone>> getZones() {
        return mZones;
    }

    private void loadZones() {
        ArrayList<Zone> zones = new ArrayList<Zone>();
        zones.add(new Zone("Abdulaleem", "0/5", "Thursday 05/06", "Lets go to the Mall together and have some fun!!",
                "Kent Park", "Private", "mall.png"));
        zones.add(new Zone("Orhun", "3/5", "Thursday 05/06", "We are going to play Basketball and we need 5 more people. You can join if you have ever touched a basketball before.",
                "Main Campus Sports Hall", "public", "basketball.png"));
        zones.add(new Zone("Irmak", "1/5", "Thursday 05/06", "We will be drinking coffee together in Coffee break. Only for CS students!",
                "Coffee Break", "Private", "coffeeBreak.png"));
        zones.add(new Zone("Fazlı", "4/5", "Thursday 05/06", "We are planning a football game and anyone is welcomed!",
                "Main Campus Football Field", "public", "football.png"));
        mZones.setValue(zones);
    }

}
