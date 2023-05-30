package com.example.navogation_with_pages.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navogation_with_pages.R;
import com.example.navogation_with_pages.ui.object_classes.Zone;
import com.example.navogation_with_pages.ui.adapters.ZonesRecViewAdapter;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ProgressDialog dialog;
    private HomeViewModel homeViewModel;
    private ZonesRecViewAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dialog = new ProgressDialog(this.getContext());
        dialog.setMessage("Loading Home...");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView zonesRecView = root.findViewById(R.id.zonesRecView);
        zonesRecView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ZonesRecViewAdapter();
        zonesRecView.setAdapter(adapter);

        homeViewModel.getZones().observe(getViewLifecycleOwner(), new Observer<ArrayList<Zone>>() {
            @Override
            public void onChanged(ArrayList<Zone> zones) {
                adapter.setZones(zones,dialog);
            }
        });

        return root;
    }
}
