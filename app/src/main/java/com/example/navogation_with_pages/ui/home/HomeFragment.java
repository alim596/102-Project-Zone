package com.example.navogation_with_pages.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navogation_with_pages.R;
import com.example.navogation_with_pages.Zone;
import com.example.navogation_with_pages.ZonesRecViewAdapter;
import com.example.navogation_with_pages.ui.home.HomeViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Observe the LiveData object to get the list of zones
        homeViewModel.getZones().observe(getViewLifecycleOwner(), new Observer<ArrayList<Zone>>() {
            @Override
            public void onChanged(ArrayList<Zone> zones) {
                // Use the list of zones here
                ZonesRecViewAdapter adapter = new ZonesRecViewAdapter();
                adapter.setZones(zones);

                RecyclerView zonesRecView = root.findViewById(R.id.zonesRecView);
                zonesRecView.setAdapter(adapter);
                zonesRecView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });
        return root;
    }
}
