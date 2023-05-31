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

public class SearchViewModel extends ViewModel {
    private MutableLiveData<ArrayList<User>> users;
    private MutableLiveData<ArrayList<Zone>> zones;
    private final FirebaseFirestore DB =  FirebaseFirestore.getInstance();
    private final StorageReference storageRef = FirebaseStorage.getInstance().getReference();


    public SearchViewModel()
    {
        users = new MutableLiveData<>();
        zones = new MutableLiveData<>();
    }

    public LiveData<ArrayList<User>> getUsers() { return users; }

    public void setUsers(ArrayList<User> userList) { users.setValue(userList); }
    public LiveData<ArrayList<Zone>> getZones() { return zones; }
    public  void setZones(ArrayList<Zone> zoneList) { zones.setValue(zoneList);}
}
