package com.example.navogation_with_pages.ui.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.navogation_with_pages.ui.adapters.FriendsAdapter;
import com.example.navogation_with_pages.ui.object_classes.OnGetUsersListener;
import com.example.navogation_with_pages.ui.object_classes.User;
import com.example.navogation_with_pages.ui.object_classes.OnGetUserListener;
import com.example.navogation_with_pages.R;
import com.example.navogation_with_pages.ui.object_classes.Zone;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The FriendsListActivity displays a list of friends for a specific user.
 * It retrieves the user's friends from Firebase Firestore and populates them in a ListView using a custom adapter (FriendsAdapter).
 * Clicking on a friend in the list starts the OthersProfileActivity and passes the friend's ID to display their profile.
 */
public class FriendsListActivity extends AppCompatActivity {
    private ListView friendsListView;
    private TextView text;

    /**
     * onCreate method called when the activity is being created.
     * Initializes the activity layout, retrieves the user ID from the intent, and fetches the user's friends from Firestore.
     * @param savedInstanceState The saved instance state bundle.
     */
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
                setup(user);
            }
        });
    }

    /**
     * Sets up the friends list view with the user's friends.
     * If the user has no friends, a message is displayed indicating so.
     * @param user1 The User object representing the current user.
     */
    private void setup(User user1) {
        user1.getFriends(new OnGetUsersListener() {
            @Override
            public void onSuccess(ArrayList<User> users) {
                if (users == null || users.size() == 0) {
                    text.setText(user1.getUsername() + " has no friends.");
                } else {
                    text.setText("Friends of " + user1.getUsername());
                }

                FriendsAdapter adapter = new FriendsAdapter(FriendsListActivity.this, users);
                friendsListView.setAdapter(adapter);
                friendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent k = new Intent(FriendsListActivity.this, OthersProfileActivity.class);
                        k.putExtra("ID", users.get(i).getID());
                        startActivity(k);
                    }
                });
            }
        });
    }
}
