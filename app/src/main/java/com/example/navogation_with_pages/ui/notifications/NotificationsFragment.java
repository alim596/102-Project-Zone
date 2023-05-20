package com.example.navogation_with_pages.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navogation_with_pages.R;
import com.example.navogation_with_pages.NotRecyAdap;
import com.example.navogation_with_pages.databinding.FragmentNotificationsBinding;


public class NotificationsFragment extends Fragment {

    NotRecyAdap adapter;
    NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);

        recyclerView.setLayoutManager(layoutManager);

        adapter = new NotRecyAdap(notificationsViewModel.getNotification_list());
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}