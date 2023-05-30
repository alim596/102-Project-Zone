package com.example.navogation_with_pages.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.navogation_with_pages.ui.object_classes.OnGetUserListener;
import com.example.navogation_with_pages.R;
import com.example.navogation_with_pages.ui.object_classes.User;
import com.example.navogation_with_pages.ui.adapters.ZonesRecViewAdapter2;
import com.example.navogation_with_pages.ui.object_classes.Zone;

public class OthersProfileActivity extends AppCompatActivity {
    private User user;
    private TextView biography;

    private RecyclerView recyclerView;

    private ImageView profilePic;
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


        Intent i = getIntent();
        String userID = i.getStringExtra("ID");

        User.getUser(userID, new OnGetUserListener() {
            @Override
            public void onSuccess(User user) {
                OthersProfileActivity.this.setup(user);
            }
        });


    }
    private void setup(User user){
        name.setText(user.getUsername());
        biography.setText(user.getBiography());

        Button friendsButton = (Button) findViewById(R.id.OtherFriendsButton);
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