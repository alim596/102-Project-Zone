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

/**
 * An activity for managing user profile settings and applying changes.
 */
public class ProfileSettings extends AppCompatActivity {

    private FirebaseFirestore fStore;
    private EditText newUsername;
    private EditText newBiography;
    private Button button;

    private String ID;
    private Button logoutButton;
    private boolean confirmation;

    Intent i;

    /**
     * Initializes the activity and sets up the necessary components.
     *
     * @param savedInstanceState The previously saved state of the activity.
     */
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
        newUsername.setText(username);
        newBiography.setText(biography);
    }

    /**
     * Applies the changes made by the user to their profile.
     *
     * @param v The button view that was clicked to apply the changes.
     */
    public void applyChanges(View v) {
        if (newUsername.getText().length() > 40 || newBiography.getText().length() > 130) {
            if (newUsername.getText().length() > 40) {
                Toast.makeText(this, "Username is too long (maximum 40 characters).", Toast.LENGTH_SHORT).show();
            } else if (newBiography.getText().length() > 130) {
                Toast.makeText(this, "Biography is too long (maximum 130 characters).", Toast.LENGTH_SHORT).show();
            }
        } else {
            String newUsernameText = newUsername.getText().toString();
            String newBiographyText = newBiography.getText().toString();
            i.putExtra("username", newUsernameText);
            i.putExtra("biography", newBiographyText);
            i.putExtra("ID", ID);
            setResult(RESULT_OK, i);
            finish();
        }
    }

    /**
     * Handles the logout button click and signs the user out.
     *
     * @param v The button view that was clicked to log out.
     */
    public void logoutListener(View v) {
        if (!confirmation) {
            Toast.makeText(this, "Please click once more to log out.", Toast.LENGTH_SHORT).show();
            confirmation = true;
        } else {
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(this, SignInPageActivity.class);
            startActivity(i);
        }
    }

}
