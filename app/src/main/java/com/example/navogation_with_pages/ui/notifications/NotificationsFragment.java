package com.example.navogation_with_pages.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navogation_with_pages.R;
import com.example.navogation_with_pages.databinding.FragmentNotificationsBinding;
import com.example.navogation_with_pages.ui.object_classes.NotRecyAdap;
import com.example.navogation_with_pages.ui.object_classes.OnGetUserListener;
import com.example.navogation_with_pages.ui.object_classes.User;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

/**
 * A fragment to display user notifications.
 */
public class NotificationsFragment extends Fragment {

    private final FirebaseFirestore DB =  FirebaseFirestore.getInstance();
    ArrayList<String> userNames;
    NotRecyAdap adapter;
    static NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;
    boolean add = false;

    /**
     * Called to do initial creation of the fragment.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        userNames = new ArrayList<>();
        loadNotifications();
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);

        recyclerView.setLayoutManager(layoutManager);

        adapter = new NotRecyAdap(notificationsViewModel.getNotifications().getValue());
        recyclerView.setAdapter(adapter);

        notificationsViewModel.getNotifications().observe(getViewLifecycleOwner(), notifications -> {
            adapter.setNotifications(notifications);
            adapter.notifyDataSetChanged();
        });

        return root;
    }

    /**
     * Load the notifications from the database and update the ViewModel.
     */
    public void loadNotifications()
    {
        DB.collection("notifications").get().addOnSuccessListener(queryDocumentSnapshots ->
        {
            User.getCurrentUser(new OnGetUserListener() {
                @Override
                public void onSuccess(User user) {
                    ArrayList<Notification> notifications1 = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                    {

                        Notification notification = documentSnapshot.toObject(Notification.class);
                        add = false;
                        if(user.getID().equals(notification.getInteractedUser().getID()))
                            add = true;
                        if(add)
                        {
                            notifications1.add(notification);
                            userNames.add(notification.getInteracorUser().getUsername());
                        }

                    }
                    notificationsViewModel.setNotifications(notifications1); // Update the notifications LiveData in the ViewModel
                }
            });

        }).addOnFailureListener(e -> { Log.e("NotificationsFragment", "Error loading notifications", e); });
    }

    /**
     * Called when the view previously created by {@link #onCreateView} has been detached from the fragment.
     * The next time the fragment needs to be displayed, a new view will be created.
     */
    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        binding = null;
    }
}

