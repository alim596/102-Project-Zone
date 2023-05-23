package com.example.navogation_with_pages.ui.profile;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navogation_with_pages.MainActivity;
import com.example.navogation_with_pages.R;
import com.User;
import com.example.navogation_with_pages.ZonesRecViewAdapter2;
import com.example.navogation_with_pages.databinding.FragmentProfileBinding;

public class SelfProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    private RecyclerView recyclerView;

    private User user = MainActivity.allUsers.get(0);
    private TextView biography;
    private TextView name;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_profile, null);

        recyclerView = (RecyclerView)(root.findViewById(R.id.PreviousZonesRecView));
        biography = (TextView) root.findViewById(R.id.biographyDisplay);
        name = (TextView) root.findViewById(R.id.OtherUsernameDisplay);
        biography.setText(user.getBiography());
        name.setText(user.getUsername());

        com.example.navogation_with_pages.Zone zone = new com.example.navogation_with_pages.Zone("asd", 1, "","asdasdasdasd","East Campus",2,"asd", "sport");
        user.addPreviousZone(zone);
        ZonesRecViewAdapter2 adapter = new ZonesRecViewAdapter2();
        recyclerView.setAdapter(adapter);
        adapter.setZones(user.getPreviousZones());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));




        Button settingsButton = (Button) root.findViewById(R.id.button);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProfileSettings.class);
                intent.putExtra("username", (String)SelfProfileFragment.this.name.getText());
                intent.putExtra("biography", (String)SelfProfileFragment.this.biography.getText());
                activityResultLaunch.launch(intent);
            }
        });

        Button friendsButton = (Button) root.findViewById(R.id.friendsButton);
        friendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FriendsListActivity.class);
                intent.putExtra("ID", user.getID());
                getActivity().startActivity(intent);
            }
        });


        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    ActivityResultLauncher<Intent> activityResultLaunch = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK){
                        Intent intent = result.getData();
                        if(intent!=null){
                            String newUsername = intent.getStringExtra("username");
                            String newBiography = intent.getStringExtra("biography");
                            user.setBiography(newBiography);
                            user.setUsername(newUsername);
                            biography.setText(user.getBiography());
                            name.setText(user.getUsername());
                        }
                    }
                }
            });


}