package com.example.navogation_with_pages.ui.search;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.navogation_with_pages.ui.object_classes.User;
import com.example.navogation_with_pages.ui.object_classes.Zone;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * ViewModel class for the search functionality.
 * Holds and manages the data related to user and zone search results.
 */
public class SearchViewModel extends ViewModel {
    private MutableLiveData<ArrayList<User>> users;
    private MutableLiveData<ArrayList<Zone>> zones;
    private final FirebaseFirestore DB =  FirebaseFirestore.getInstance();
    private final StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    /**
     * Constructor for the SearchViewModel class.
     * Initializes the MutableLiveData objects for users and zones.
     */
    public SearchViewModel() {
        users = new MutableLiveData<>();
        zones = new MutableLiveData<>();
    }

    /**
     * Returns the LiveData object for user search results.
     *
     * @return LiveData object containing the user search results.
     */
    public LiveData<ArrayList<User>> getUsers() {
        return users;
    }

    /**
     * Sets the user search results.
     *
     * @param userList The list of users to set as the search results.
     */
    public void setUsers(ArrayList<User> userList) {
        users.setValue(userList);
    }

    /**
     * Returns the LiveData object for zone search results.
     *
     * @return LiveData object containing the zone search results.
     */
    public LiveData<ArrayList<Zone>> getZones() {
        return zones;
    }

    /**
     * Sets the zone search results.
     *
     * @param zoneList The list of zones to set as the search results.
     */
    public void setZones(ArrayList<Zone> zoneList) {
        zones.setValue(zoneList);
    }
}
