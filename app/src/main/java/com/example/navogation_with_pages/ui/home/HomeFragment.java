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

/**
 * HomeFragment class for displaying the home view in the application.
 *
 * This class extends Fragment and is responsible for creating the Home View and binding the data from the ViewModel
 * to the UI elements. It uses a RecyclerView to display the zones data.
 */
public class HomeFragment extends Fragment {

    /**
     * Dialog for showing the loading progress.
     */
    private ProgressDialog dialog;

    /**
     * ViewModel for holding and managing the UI data for Home View.
     */
    private HomeViewModel homeViewModel;

    /**
     * RecyclerView adapter for displaying the list of zones.
     */
    private ZonesRecViewAdapter adapter;

    /**
     * Method for creating the Home View.
     *
     * This method initializes the ViewModel, inflates the layout for the Home View, sets up the RecyclerView
     * for displaying the list of zones, and observes changes in the LiveData object in the ViewModel
     * to update the UI accordingly.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     *
     * @return Return the View for the fragment's UI, or null.
     */
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
