package com.example.navogation_with_pages;

import android.content.Context;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ExpandableListAdapter;import com.google.firebase.FirebaseApp;
import android.widget.RelativeLayout;

import com.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.navogation_with_pages.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private CardView cardView;
    private RelativeLayout hiddenLayout;

    public static ArrayList<User> allUsers = new ArrayList<User>();
    static User user1 = new User("Ada Güder","asdasd","asdasdasdasd");
    static User user2 = new User("Toprak Kekin","asdasd","asdasdasdasd");
    static User user3 = new User("Orhun Güder","epicpassword","orhun.guder@ug.bilkent.edu.tr");
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        allUsers.add(user1);
        allUsers.add(user2);
        allUsers.add(user3);
        user1.addFriend(user2);
        user1.addFriend(user3);
        user2.addFriend(user1);
        user1.setBiography("The most perfect biography");
        user2.setBiography("I LOVE GAMING I LOVE GAMING I LOVE GAMING I LOVE GAMING I LOVE GAMING I LOVE GAMING I LOVE GAMING I LOVE GAMING ");
        user3.setBiography("This is a relatively normal biography compared to the one above.");
        user1.setProfilePicture("user1.jpg");
        user2.setProfilePicture("user2.jpg");
        user3.setProfilePicture("user3.png");

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_add, R.id.navigation_notifications, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

    }
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }


}