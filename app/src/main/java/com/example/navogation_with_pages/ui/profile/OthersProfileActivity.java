package com.example.navogation_with_pages.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.navogation_with_pages.MainActivity;
import com.example.navogation_with_pages.R;
import com.User;
import com.example.navogation_with_pages.ZonesRecViewAdapter2;

public class OthersProfileActivity extends AppCompatActivity {
    private User user;
    private TextView biography;

    private RecyclerView recyclerView;

    private ImageView profilePic;
    private TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others_profile);

        recyclerView = (RecyclerView)(findViewById(R.id.OtherPreviousZonesRecView));
        this.biography = findViewById(R.id.OtherBiographyDisplay);
        this.name = findViewById(R.id.OtherUsernameDisplay);
        this.profilePic = findViewById(R.id.OtherProfilePictureDisplay);



        Intent i = getIntent();
        int userID = i.getIntExtra("ID",0);

        user = MainActivity.allUsers.get(userID);
        name.setText(user.getUsername());
        biography.setText(user.getBiography());
        Icon icon = Icon.createWithFilePath(user.getProfilePicture());
        profilePic.setImageIcon(icon);
        Button friendsButton = (Button) findViewById(R.id.OtherFriendsButton);
        friendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OthersProfileActivity.this, FriendsListActivity.class);
                intent.putExtra("ID", user.getID());
                startActivity(intent);
            }
        });

        com.example.navogation_with_pages.Zone zone = new com.example.navogation_with_pages.Zone("OtherProfiles", 1, "","asdasdasdasd","East Campus",2,"asd", "sport");
        user.addPreviousZone(zone);
        ZonesRecViewAdapter2 adapter = new ZonesRecViewAdapter2();
        recyclerView.setAdapter(adapter);
        adapter.setZones(user.getPreviousZones());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}