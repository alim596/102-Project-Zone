package com.example.navogation_with_pages.ui.entry_pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.navogation_with_pages.MainActivity;
import com.example.navogation_with_pages.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * An activity for signing in a user in the application.
 */
public class SignInPageActivity extends AppCompatActivity {
    private boolean isPassword3Visible = false;

    private EditText emailField;

    private EditText passwordField;

    private FirebaseAuth mAuth;

    /**
     * Called when the activity is starting.
     * This is where most initialization should go.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_in_page);
        passwordField = findViewById(R.id.passwordTextField3);
        emailField = findViewById(R.id.emailField2);
        emailField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        mAuth = FirebaseAuth.getInstance();
        ((EditText)findViewById(R.id.passwordTextField3)).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    /**
     * Redirects the user to the registration page.
     *
     * @param view The view triggering this method.
     */
    public void registerRedirect(View view){

        Intent myIntent = new Intent(this, RegisterpageActivity.class);
        this.startActivity(myIntent);

    }

    /**
     * Logic for authenticating a user.
     *
     * @param view The view triggering this method.
     */
    public void login(View view){
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        if(email.equals("") || password.equals("")){
            Toast.makeText(getApplicationContext(), "Please make sure you fill your credentials.", Toast.LENGTH_LONG).show();
        }

        else{
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                if(!firebaseUser.isEmailVerified()){
                                    FirebaseAuth.getInstance().signOut();
                                    firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(getApplicationContext(), "This account is not yet verified. Verification E-Mail has been sent.", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@androidx.annotation.NonNull Exception e) {
                                            Log.d("ERRORS","Email not sent: " + e.getMessage());
                                            Toast.makeText(getApplicationContext(), "This account is not verified, but another mail cannot be sent. " +e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                                else{
                                    Intent myIntent = new Intent(SignInPageActivity.this, MainActivity.class);
                                    SignInPageActivity.this.startActivity(myIntent);
                                    Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                                }

                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Login failed! Please make sure your credentials are correct.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    /**
     * Toggles the visibility of the password in the password input field.
     *
     * @param view The view triggering this method.
     */
    public void hideInfo2(View view){
        EditText text;
        text = (EditText) findViewById(R.id.passwordTextField3);
        if (!isPassword3Visible) {
            text.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            isPassword3Visible = true;
        } else {
            text.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            isPassword3Visible = false;
        }
        text.setSelection(text.length());


    }
}