package com.example.navogation_with_pages.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.User;
import com.example.navogation_with_pages.MainActivity;
import com.example.navogation_with_pages.R;


import java.util.ArrayList;

public class FriendsListActivity extends AppCompatActivity {
    private ListView friendsListView;
    private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.friends_recylclerview);

        text = findViewById(R.id.friendsOfText);
        friendsListView = findViewById(R.id.FriendsListView);

        Intent i = getIntent();
        int ID = i.getIntExtra("ID",0);
        ArrayList<User> friends = MainActivity.allUsers.get(ID).getFriends();
        if(friends.size() == 0){
            text.setText(MainActivity.allUsers.get(ID).getUsername() + " has no friends.");
        }
        else {
            text.setText("Friends of " + MainActivity.allUsers.get(ID).getUsername());
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