package com.example.navogation_with_pages.ui.search;

import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navogation_with_pages.R;
import com.example.navogation_with_pages.ui.home.HomeViewModel;
import com.example.navogation_with_pages.ui.object_classes.Zone;
import com.example.navogation_with_pages.ui.adapters.ZonesRecViewAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

/**
 * This activity allows the user to search for zones.
 * It displays a list of zones and provides a search functionality to filter the list based on the entered text.
 */
public class SearchZoneAct extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private final StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private final FirebaseFirestore DB = FirebaseFirestore.getInstance();
    private ZonesRecViewAdapter adapter;
    ArrayList<String> zoneNames;
    SearchView searchView;
    HomeViewModel homeViewModel;
    ArrayList<Zone> zonesM;

    /**
     * Called when the activity is created. Sets up the layout, initializes the views, and observes the zones data.
     *
     * @param savedInstanceState The saved instance state bundle.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_zone);
        getSupportActionBar().setTitle("Zone search");

        searchView = findViewById(R.id.search_widget);
        searchView.setOnQueryTextListener(this);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        RecyclerView zonesRecView = findViewById(R.id.zonesRecViewZone);
        zonesRecView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ZonesRecViewAdapter();
        zonesRecView.setAdapter(adapter);

        homeViewModel.getZones().observe(this, new Observer<ArrayList<Zone>>() {
            @Override
            public void onChanged(ArrayList<Zone> zones) {
                adapter.setZones(zones);
            }
        });
    }

    /**
     * Called when the user submits the search query.
     *
     * @param query The search query.
     * @return True if the query was handled, false otherwise.
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    /**
     * Called when the user changes the search query.
     *
     * @param newText The new search query.
     * @return True if the query was handled, false otherwise.
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        filterList(newText);
        return false;
    }

    /**
     * Filters the list of zones based on the entered text and updates the adapter.
     *
     * @param newText The new search query.
     */
    private void filterList(String newText) {
        ArrayList<Zone> filteredList = new ArrayList<>();
        for (Zone zone : homeViewModel.getZonesArr()) {
            if (zone.getName().toLowerCase().contains(newText.toLowerCase()))
                filteredList.add(zone);
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No data found", Toast.LENGTH_LONG).show();
        } else {
            adapter.setZones(filteredList);
        }
    }
}
