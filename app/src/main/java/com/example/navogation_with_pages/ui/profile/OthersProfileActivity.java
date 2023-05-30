package com.example.navogation_with_pages.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navogation_with_pages.ui.object_classes.OnGetUserListener;
import com.example.navogation_with_pages.R;
import com.example.navogation_with_pages.ui.object_classes.OnGetUsersListener;
import com.example.navogation_with_pages.ui.object_classes.User;
import com.example.navogation_with_pages.ui.adapters.ZonesRecViewAdapter2;
import com.example.navogation_with_pages.ui.object_classes.Zone;

import java.util.ArrayList;

public class OthersProfileActivity extends AppCompatActivity {
    private User user;
    private TextView biography;

    private RecyclerView recyclerView;

    private boolean isConfirm = false;
    private ImageView profilePic;

    private TextView averageRatingText;

    private ImageButton rateButton;

    private ImageButton addFriendButton;

    private TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_others_profile);

        recyclerView = (RecyclerView)(findViewById(R.id.OtherPreviousZonesRecView));
        this.biography = findViewById(R.id.OtherBiographyDisplay);
        this.name = findViewById(R.id.OtherUsernameDisplay);
        this.profilePic = findViewById(R.id.OtherProfilePictureDisplay);
        this.addFriendButton = findViewById(R.id.addFriendButton);
        this.rateButton = findViewById(R.id.rateButton);
        this.averageRatingText = findViewById(R.id.averageRatingText);

        Intent i = getIntent();
        String userID = i.getStringExtra("ID");

        User.getUser(userID, new OnGetUserListener() {
            @Override
            public void onSuccess(User userr) {
                OthersProfileActivity.this.setup(userr);
            }
        });


    }

    //TODO: implement logic to check wheteher this user has already rated this user.
    private void rateUser(User userRating, User usertoBeRated, double rating){
        userRating.rateUser(usertoBeRated,rating);
    }

    private void setup(User user){
        name.setText(user.getUsername());
        biography.setText(user.getBiography());
        String avRating = String.format("%.2f", user.getAverageRating());
        averageRatingText.setText("Average Rating: " + avRating + "/5");

        Button friendsButton = (Button) findViewById(R.id.OtherFriendsButton);

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
                                rateUser(currentUser,user,1);
                                dialog.dismiss();
                            }
                        });
                        btn2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                rateUser(currentUser,user,2);
                                dialog.dismiss();
                            }
                        });
                        btn3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                rateUser(currentUser,user,3);
                                dialog.dismiss();
                            }
                        });
                        btn4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                rateUser(currentUser,user,4);
                                dialog.dismiss();
                            }
                        });
                        btn5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                rateUser(currentUser,user,5);
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
                                        currentUser.addFriend(user);
                                        Toast.makeText(OthersProfileActivity.this,"User added to friends!",Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        if(!isConfirm){
                                            isConfirm = true;
                                            Toast.makeText(OthersProfileActivity.this,"User already in friends list! Press again to delete them.",Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            currentUser.removeFriend(user);
                                            Toast.makeText(OthersProfileActivity.this,"User deleted from friends.",Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }
                            });


                        }
                        else{
                            Toast.makeText(OthersProfileActivity.this,"User added to friends!",Toast.LENGTH_SHORT).show();
                            currentUser.addFriend(user);
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
        adapter.setZones(user.getPreviousZones());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}