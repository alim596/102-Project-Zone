package com.example.navogation_with_pages.ui.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.navogation_with_pages.ui.adapters.FriendsAdapter;
import com.example.navogation_with_pages.ui.object_classes.User;
import com.example.navogation_with_pages.ui.object_classes.OnGetUserListener;
import com.example.navogation_with_pages.R;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;

public class FriendsListActivity extends AppCompatActivity {
    private ListView friendsListView;

    private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.friends_recylclerview);

        FirebaseFirestore fStore = FirebaseFirestore.getInstance();



        text = findViewById(R.id.friendsOfText);
        friendsListView = findViewById(R.id.FriendsListView);

        Intent i = getIntent();
        String ID = i.getStringExtra("ID");

        User.getUser(ID, new OnGetUserListener() {
            @Override
            public void onSuccess(User user) {
                FriendsListActivity.this.setup(user);
            }
        });



    }
    private void setup(User user1){
        ArrayList<User> friends = user1.getFriends();
        if(friends == null || friends.size() == 0 ) {
            text.setText(user1.getUsername() + " has no friends.");
        }

        else {
            text.setText("Friends of " + user1.getUsername());
        }


        FriendsAdapter adapter = new FriendsAdapter(this,friends);
        friendsListView.setAdapter(adapter);
        friendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent k = new Intent(FriendsListActivity.this,OthersProfileActivity.class);
                k.putExtra("ID", friends.get(i).getID());
                startActivity(k);
            }
        });
    }
}