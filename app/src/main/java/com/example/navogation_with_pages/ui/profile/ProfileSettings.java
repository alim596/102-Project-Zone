package com.example.navogation_with_pages.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.navogation_with_pages.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class ProfileSettings extends AppCompatActivity {

    private EditText newUsername;
    private EditText newBiography;
    private Button button;

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_editpage);
        newUsername = findViewById(R.id.editUsername);
        newBiography = findViewById(R.id.editBiography);
        button = findViewById(R.id.confirmChanges);
        i = new Intent();
        Intent l = getIntent();
        String username = l.getStringExtra("username");
        String biography = l.getStringExtra("biography");
        ((EditText)(findViewById(R.id.editUsername))).setText(username);
        ((EditText)(findViewById(R.id.editBiography))).setText(biography);
    }

    public void applyChanges(View v) {
        String newUsernameText = String.valueOf(newUsername.getText());
        String newBiographyText = String.valueOf(newBiography.getText());
        i.putExtra("username", newUsernameText);
        i.putExtra("biography", newBiographyText);
        setResult(RESULT_OK, i);
        finish();
    }

}