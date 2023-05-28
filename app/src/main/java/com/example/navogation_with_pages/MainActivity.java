package com.example.navogation_with_pages;

import android.content.Intent;
import android.os.Bundle;

import android.widget.RelativeLayout;

import com.example.navogation_with_pages.ui.entry_pages.SignInPageActivity;
import com.example.navogation_with_pages.ui.object_classes.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.navogation_with_pages.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth fAuth;

    private FirebaseFirestore fStore;

    private CardView cardView;
    private RelativeLayout hiddenLayout;

    public static User user;
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //Below code retrieves user information from database.
        String userID = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("users").document(userID);
        if(documentReference == null){
            fAuth.signOut();
            Intent i = new Intent(this, SignInPageActivity.class);
            startActivity(i);
        }
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                user = new User(value.getString("username"),value.getString("password"),value.getString("email"),value.getString("ID"));
                user.setBiography(value.getString("biography"));
                user.setAverageRating(value.getDouble("averageRating"));
                user.setRatingCount(value.getDouble("ratingCount"));
                user.setFriends((ArrayList<User>) value.get("friends"));
            }
        });

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