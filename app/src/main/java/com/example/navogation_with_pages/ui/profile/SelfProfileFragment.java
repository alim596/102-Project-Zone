package com.example.navogation_with_pages.ui.profile;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navogation_with_pages.R;
import com.example.navogation_with_pages.ui.object_classes.OnGetUserListener;
import com.example.navogation_with_pages.ui.object_classes.OnGetZonesListener;
import com.example.navogation_with_pages.ui.object_classes.User;
import com.example.navogation_with_pages.ui.object_classes.Zone;
import com.example.navogation_with_pages.ui.object_classes.ZonesRecViewAdapter2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

/**
 * Fragment class for the self profile screen.
 * Displays the user's profile information and allows editing and image uploading.
 */
public class SelfProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private RecyclerView recyclerView;
    private ProgressDialog dialog;

    private User user;
    private TextView biography;
    private ImageView profilePictureDisplay;
    private TextView ratingText;
    private StorageReference firebaseStorage;
    private TextView name;

    /**
     * Sets up the self profile fragment with the user's information and handles click events.
     *
     * @param user The User object representing the current user.
     */
    private void setPage(User user){
        biography.setText(user.getBiography());
        name.setText(user.getUsername());
        StorageReference fileRef = firebaseStorage.child("users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/profile.jpg");
        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profilePictureDisplay);
                try {
                    Thread.sleep(1000);
                    dialog.hide();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                try {
                    Thread.sleep(1000);
                    dialog.hide();
                } catch (InterruptedException ef) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        String avRating = String.format("%.2f", user.getAverageRating());
        ratingText.setText("Average Rating: " + avRating + "/5");
        ZonesRecViewAdapter2 adapter = new ZonesRecViewAdapter2();
        recyclerView.setAdapter(adapter);
        user.getPreviousZones(new OnGetZonesListener() {
            @Override
            public void onSuccess(ArrayList<Zone> zones) {
                adapter.setZones(zones);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });

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

    /**
     * Activity result launcher for handling the gallery intent result.
     */
    ActivityResultLauncher<Intent> galleryResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK){
                        Intent intent = result.getData();
                        if(intent!=null){
                            Toast.makeText(SelfProfileFragment.this.getContext(),"Uploading image...", Toast.LENGTH_SHORT).show();
                            Uri imageUri = intent.getData();
                            StorageReference fileRef = firebaseStorage.child("users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/profile.jpg");
                            fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Picasso.get().load(uri).into(profilePictureDisplay);
                                            Toast.makeText(SelfProfileFragment.this.getContext(),"Image uploaded successfully.",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(SelfProfileFragment.this.getContext(),"Exception: " + e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
            });

    /**
     * Uploads the image to Firebase storage.
     */
    private void uploadImagetoFireBase() {
        // Code for image upload to Firebase storage
    }

    /**
     * Creates and inflates the view for the self profile fragment.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container          The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState The previously saved state of the fragment.
     * @return The view for the self profile fragment.
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        dialog=new ProgressDialog(this.getContext());
        dialog.setMessage("Loading Profile...");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_profile, null);

        String ID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        firebaseStorage = FirebaseStorage.getInstance().getReference();
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

    /**
     * Activity result launcher for handling the result of the ProfileSettings activity.
     */
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
