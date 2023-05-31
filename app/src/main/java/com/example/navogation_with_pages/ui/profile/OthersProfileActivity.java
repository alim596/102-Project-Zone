package com.example.navogation_with_pages.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navogation_with_pages.ui.object_classes.Notification;
import com.example.navogation_with_pages.ui.object_classes.OnGetUserListener;
import com.example.navogation_with_pages.R;
import com.example.navogation_with_pages.ui.object_classes.OnGetUsersListener;
import com.example.navogation_with_pages.ui.object_classes.OnGetZoneListener;
import com.example.navogation_with_pages.ui.object_classes.OnGetZonesListener;
import com.example.navogation_with_pages.ui.object_classes.User;
import com.example.navogation_with_pages.ui.adapters.ZonesRecViewAdapter2;
import com.example.navogation_with_pages.ui.object_classes.Zone;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * The OthersProfileActivity displays the profile of another user.
 * It retrieves the user's information from Firebase Firestore and displays their profile picture, username, biography, average rating, and previous zones.
 * The user can rate the profile owner, add them as a friend, view their friends list, and view their previous zones.
 */
public class OthersProfileActivity extends AppCompatActivity {
    private User user;
    private TextView biography;
    private ProgressDialog dialog;
    private StorageReference firebaseStorage;
    private RecyclerView recyclerView;
    private boolean isConfirm = false;
    private ImageView profilePic;
    private TextView averageRatingText;
    private ImageButton rateButton;
    private ImageButton addFriendButton;
    private TextView name;

    /**
     * onCreate method called when the activity is being created.
     * Initializes the activity layout, retrieves the user ID from the intent, and fetches the user's information from Firestore.
     * If the user ID matches the current user's ID, it displays a toast message and finishes the activity.
     * @param savedInstanceState The saved instance state bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_others_profile);
        Intent i = getIntent();
        String userID = i.getStringExtra("ID");
        if(userID.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            Toast.makeText(OthersProfileActivity.this,"This is your own profile!",Toast.LENGTH_SHORT).show();
            onBackPressed();
        }

        firebaseStorage = FirebaseStorage.getInstance().getReference();
        recyclerView = (RecyclerView)(findViewById(R.id.OtherPreviousZonesRecView));
        this.biography = findViewById(R.id.OtherBiographyDisplay);
        this.name = findViewById(R.id.OtherUsernameDisplay);
        this.profilePic = findViewById(R.id.OtherProfilePictureDisplay);
        this.addFriendButton = findViewById(R.id.addFriendButton);
        this.rateButton = findViewById(R.id.rateButton);
        this.averageRatingText = findViewById(R.id.averageRatingText);

        User.getUser(userID, new OnGetUserListener() {
            @Override
            public void onSuccess(User userr) {
                setup(userr);
            }
        });
    }

    /**
     * Sets up the user profile by retrieving user information and populating the views.
     * @param user The User object representing the profile owner.
     */
    private void setup(User user) {
        StorageReference fileRef = firebaseStorage.child("users/" + user.getID() + "/profile.jpg");
        name.setText(user.getUsername());
        biography.setText(user.getBiography());
        String avRating = String.format("%.2f", user.getAverageRating());
        averageRatingText.setText("Average Rating: " + avRating + "/5");

        Button friendsButton = (Button) findViewById(R.id.OtherFriendsButton);

        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profilePic);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        User.getCurrentUser(new OnGetUserListener() {
            @Override
            public void onSuccess(User currentUser) {
                rateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dialog dialog = new Dialog(OthersProfileActivity.this);
                        dialog.setContentView(R.layout.dialog_layout);
                        dialog.show();
                        ImageView btnClose = dialog.findViewById(R.id.btn_close);
                        Button btn1 = dialog.findViewById(R.id.btn_1);
                        Button btn2 = dialog.findViewById(R.id.btn_2);
                        Button btn3 = dialog.findViewById(R.id.btn_3);
                        Button btn4 = dialog.findViewById(R.id.btn_4);
                        Button btn5 = dialog.findViewById(R.id.btn_5);

                        btn1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                rateUser(currentUser, user, 1);
                                dialog.dismiss();
                            }
                        });
                        btn2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                rateUser(currentUser, user, 2);
                                dialog.dismiss();
                            }
                        });
                        btn3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                rateUser(currentUser, user, 3);
                                dialog.dismiss();
                            }
                        });
                        btn4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                rateUser(currentUser, user, 4);
                                dialog.dismiss();
                            }
                        });
                        btn5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                rateUser(currentUser, user, 5);
                                dialog.dismiss();
                            }
                        });
                        btnClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                    }
                });
            }
        });

        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.getCurrentUser(new OnGetUserListener() {
                    @Override
                    public void onSuccess(User currentUser) {
                        if(currentUser.getFriendIDs() != null && currentUser.getFriendIDs().size() != 0){
                            currentUser.getFriends(new OnGetUsersListener() {
                                @Override
                                public void onSuccess(ArrayList<User> friendsList) {
                                    boolean isFriend = false;
                                    for(User userr : friendsList){
                                        if(userr.getID().equals(user.getID())){
                                            isFriend = true;
                                        }
                                    }
                                    if(!isFriend){
                                        Toast.makeText(OthersProfileActivity.this,"User added to friends!",Toast.LENGTH_SHORT).show();
                                        Notification not = new Notification(currentUser,user);
                                    } else {
                                        if(!isConfirm){
                                            isConfirm = true;
                                            Toast.makeText(OthersProfileActivity.this,"User already in friends list! Press again to delete them.",Toast.LENGTH_SHORT).show();
                                        } else {
                                            currentUser.removeFriend(user);
                                            user.removeFriend(currentUser);
                                            Toast.makeText(OthersProfileActivity.this,"User deleted from friends.",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(OthersProfileActivity.this,"Friend request sent!",Toast.LENGTH_SHORT).show();
                            Notification not = new Notification(currentUser,user);
                        }
                    }
                });
            }
        });

        friendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OthersProfileActivity.this, FriendsListActivity.class);
                intent.putExtra("ID", user.getID());
                startActivity(intent);
            }
        });

        ZonesRecViewAdapter2 adapter = new ZonesRecViewAdapter2();
        recyclerView.setAdapter(adapter);
        Zone newZone = new Zone("String name", 3, "String dateAndTime", "String details", "String location", "String category");
        newZone.addParticipant(user);
        user.addPreviousZone(newZone);
        /*
        user.previousZoneIDs.add("c31e53a7-9030-43ff-98dd-ec4b45eb2c55") ;
        user.getPreviousZones(new OnGetZonesListener() {
            @Override
            public void onSuccess(ArrayList<Zone> zones) {
                adapter.setZones(zones);
                recyclerView.setLayoutManager(new LinearLayoutManager(OthersProfileActivity.this));
            }
        });
         */
        ArrayList<Zone> zones = new ArrayList<>();
        //TODO: presentation fix
        Zone.getZone("a4ec1568-7f8c-43a8-b6dd-efbb9174677b", new OnGetZoneListener() {
            @Override
            public void onSuccess(Zone zone) {
                zones.add(zone);
                adapter.setZones(zones);
                recyclerView.setLayoutManager(new LinearLayoutManager(OthersProfileActivity.this));
            }
        });
    }

    /**
     * Rates the user by calling the rateUser method on the rating user.
     * Updates the average rating text view with the new average rating.
     * @param userRating The User object representing the rating user.
     * @param userToBeRated The User object representing the user to be rated.
     * @param rating The rating value.
     */
    private void rateUser(User userRating, User userToBeRated, double rating){
        userRating.rateUser(userToBeRated, rating);
        String avRating = String.format("%.2f", userToBeRated.getAverageRating());
        averageRatingText.setText("Average Rating: " + avRating + "/5");
    }
}
