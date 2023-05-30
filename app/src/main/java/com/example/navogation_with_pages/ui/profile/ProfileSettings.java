package com.example.navogation_with_pages.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.navogation_with_pages.R;
import com.example.navogation_with_pages.ui.entry_pages.SignInPageActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.app.AppCompatActivity;


public class ProfileSettings extends AppCompatActivity {

    private FirebaseFirestore fStore;
    private EditText newUsername;
    private EditText newBiography;
    private Button button;

    private String ID;
    private Button logoutButton;
    private boolean confirmation;

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fStore = FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.profile_editpage);
        confirmation = false;
        newUsername = findViewById(R.id.editUsername);
        newBiography = findViewById(R.id.editBiography);
        button = findViewById(R.id.confirmChanges);
        logoutButton = findViewById(R.id.logoutButton);
        i = new Intent();
        Intent l = getIntent();
        String username = l.getStringExtra("username");
        String biography = l.getStringExtra("biography");
        ID = l.getStringExtra("ID");
        ((EditText)(findViewById(R.id.editUsername))).setText(username);
        ((EditText)(findViewById(R.id.editBiography))).setText(biography);
    }
    //todo: make this edit firebase.
    public void applyChanges(View v) {
        if(String.valueOf(newUsername.getText()).length() > 40 || String.valueOf(newBiography.getText()).length() > 130){
            if(String.valueOf(newUsername.getText()).length() > 40){
                (Toast.makeText(this,"Username over 40 spaces!",Toast.LENGTH_SHORT)).show();
            }
            else if(String.valueOf(newBiography.getText()).length() > 130){
                (Toast.makeText(this,"Biography over 130 spaces!",Toast.LENGTH_SHORT)).show();
            }
        }
        else{
            String newUsernameText = String.valueOf(newUsername.getText());
            String newBiographyText = String.valueOf(newBiography.getText());
            //todo:
            i.putExtra("username", newUsernameText);
            i.putExtra("biography", newBiographyText);
            i.putExtra("ID",ID);
            setResult(RESULT_OK, i);
            finish();
        }

    }

    public void logoutListener(View v){
        if(this.confirmation == false){
            (Toast.makeText(this,"Please click once more to log out.",Toast.LENGTH_SHORT)).show();
            confirmation = true;
        }
        else{
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(this, SignInPageActivity.class);
            startActivity(i);
        }
    }

}