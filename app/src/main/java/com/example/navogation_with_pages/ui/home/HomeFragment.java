package com.example.navogation_with_pages.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navogation_with_pages.R;
import com.example.navogation_with_pages.Zone;
import com.example.navogation_with_pages.ZonesRecViewAdapter;
import com.example.navogation_with_pages.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Date;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView zonesRecView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        zonesRecView = view.findViewById(R.id.zonesRecView);

        ArrayList<Zone> zones = new ArrayList<Zone>();
        zones.add(new Zone("Abdulaleem", "0/5", new Date(), "Lets go to the Mall together and have some fun!!",
                "Kent Park", "Private", "mall.png"));
        zones.add(new Zone("Orhun", "3/5", new Date(), "We are going to play Basketball and we need 5 more people. You can join if you have ever touched a basketball before.",
                "Main Campus Sports Hall", "public", "basketball.png"));
        zones.add(new Zone("Irmak", "1/5", new Date(), "We will be drinking coffee together in Coffee break. Only for CS students!",
                "Coffee Break", "Private", "coffeeBreak.png"));
        zones.add(new Zone("Fazlı", "4/5", new Date(), "We are planning a football game and anyone is welcomed!",
                "Main Campus Football Field", "public", "football.png"));

        ZonesRecViewAdapter adapter = new ZonesRecViewAdapter();
        adapter.setZones(zones);

        zonesRecView.setAdapter(adapter);
        zonesRecView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}