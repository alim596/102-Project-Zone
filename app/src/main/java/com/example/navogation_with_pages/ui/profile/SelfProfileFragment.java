package com.example.navogation_with_pages.ui.profile;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navogation_with_pages.ui.object_classes.OnGetUserListener;
import com.example.navogation_with_pages.R;
import com.example.navogation_with_pages.ui.object_classes.User;
import com.example.navogation_with_pages.ui.object_classes.Zone;
import com.example.navogation_with_pages.ui.adapters.ZonesRecViewAdapter2;
import com.example.navogation_with_pages.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class SelfProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    private RecyclerView recyclerView;

    private User user;
    private TextView biography;

    private ImageView profilePictureDisplay;

    private TextView ratingText;


    private TextView name;

    private void setPage(User user){
        biography.setText(user.getBiography());
        name.setText(user.getUsername());
        String avRating = String.format("%.2f", user.getAverageRating());
        ratingText.setText("Average Rating: " + avRating + "/5");
        Zone zone = new Zone("asd", 1, "","asdasdasdasd","East Campus",2,"asd", "sport");
        user.addPreviousZone(zone);
        ZonesRecViewAdapter2 adapter = new ZonesRecViewAdapter2();
        recyclerView.setAdapter(adapter);
        adapter.setZones(user.getPreviousZones());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Button settingsButton = (Button) SelfProfileFragment.this.getActivity().findViewById(R.id.button);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProfileSettings.class);
                intent.putExtra("username", (String)SelfProfileFragment.this.name.getText());
                intent.putExtra("biography", (String)SelfProfileFragment.this.biography.getText());
                intent.putExtra("ID",user.getID());
                activityResultLaunch.launch(intent);
            }
        });
        Button friendsButton = (Button) SelfProfileFragment.this.getActivity().findViewById(R.id.friendsButton);
        friendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FriendsListActivity.class);
                intent.putExtra("ID", user.getID());
                getActivity().startActivity(intent);
            }
        });

        profilePictureDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openImagesFolder = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryResultLauncher.launch(openImagesFolder);
            }
        });

    }
    //Edit this method so that it does stuff with the gallery intent
    ActivityResultLauncher<Intent> galleryResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK){
                        Intent intent = result.getData();
                        if(intent!=null){
                            /*
                             String newUsername = intent.getStringExtra("username");
                            String newBiography = intent.getStringExtra("biography");
                            String userID = intent.getStringExtra("ID");

                            FirebaseFirestore fStore = FirebaseFirestore.getInstance();

                            DocumentReference documentReference = fStore.collection("users").document(userID);

                            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                    Map<String, Object> snapshotValue = task.getResult().getData();
                                    snapshotValue.replace("username",newUsername);
                                    snapshotValue.replace("biography",newBiography);
                                    documentReference.update(snapshotValue);
                                }
                            });

                            biography.setText(newBiography);
                            name.setText(newUsername);
                             */
                            Uri imageUri = intent.getData();
                            profilePictureDisplay.setImageURI(imageUri);
                        }
                    }
                }
            });

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_profile, null);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        String ID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        recyclerView = (RecyclerView)(root.findViewById(R.id.PreviousZonesRecView));
        biography = (TextView) root.findViewById(R.id.biographyDisplay);
        name = (TextView) root.findViewById(R.id.UsernameDisplay);
        ratingText = (TextView)root.findViewById(R.id.selfRatingText);
        profilePictureDisplay = (ImageView)root.findViewById(R.id.profilePictureDisplay);

        User.getUser(ID, new OnGetUserListener() {
            @Override
            public void onSuccess(User user) {
                setPage(user);
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
                            String userID = intent.getStringExtra("ID");

                            FirebaseFirestore fStore = FirebaseFirestore.getInstance();

                            DocumentReference documentReference = fStore.collection("users").document(userID);

                            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                    Map<String, Object> snapshotValue = task.getResult().getData();
                                    snapshotValue.replace("username",newUsername);
                                    snapshotValue.replace("biography",newBiography);
                                    documentReference.update(snapshotValue);
                                }
                            });

                            biography.setText(newBiography);
                            name.setText(newUsername);
                        }
                    }
                }
            });


}