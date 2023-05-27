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
import com.example.navogation_with_pages.ui.object_classes.User;
import com.example.navogation_with_pages.ui.object_classes.Zone;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterpageActivity extends AppCompatActivity {

    private FirebaseAuth fAuth;

    private FirebaseFirestore fStore;

    EditText password1;

    EditText password2;

    EditText emailField;

    EditText usernameField;
    private boolean isPassword1Visible = false;
    private boolean isPassword2Visible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        fAuth = FirebaseAuth.getInstance();

        fStore = FirebaseFirestore.getInstance();



        if(fAuth.getCurrentUser() != null){
            Intent myIntent = new Intent(this, MainActivity.class);
            this.startActivity(myIntent);
        }

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.registerpage);

        //set the password Input types
        password1 = ((EditText)findViewById(R.id.passwordTextField));
        password2 = ((EditText)findViewById(R.id.passwordTextField2));
        emailField = ((EditText)findViewById(R.id.e_mailTextField));
        usernameField = ((EditText)findViewById(R.id.usernameTextField));
        ((EditText)findViewById(R.id.passwordTextField)).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        ((EditText)findViewById(R.id.passwordTextField2)).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    // todo Signup logic is not fully implemented
    public void signUp(View v){
        String email = emailField.getText().toString();
        String usernameString = usernameField.getText().toString();
        String password1string = password1.getText().toString();
        String password2string = password2.getText().toString();
        if(!password1string.equals(password2string)){
            (Toast.makeText(this,"Passwords are different from each other!",Toast.LENGTH_SHORT)).show();
        }
        else if(password1string.equals("") || usernameString.equals("") || email.equals("")){
            (Toast.makeText(this,"Input fields cannot be empty!",Toast.LENGTH_SHORT)).show();
        }
        else if(password1string.length() < 6){
            (Toast.makeText(this,"Password length should be at least 6 characters!",Toast.LENGTH_SHORT)).show();
        }
        else{
            String password = password1string;
            fAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RegisterpageActivity.this, SignInPageActivity.class);
                                String userID = fAuth.getUid();
                                (Toast.makeText(RegisterpageActivity.this,"Account successfully created!",Toast.LENGTH_SHORT)).show();
                                Map<String, Object> user = new HashMap<>();
                                user.put("username",usernameString);
                                user.put("email", email);
                                user.put("password", password);
                                user.put("friends", new ArrayList<User>());
                                user.put("biography","");
                                user.put("averageRating", 0.0);
                                user.put("ratingCount", 0.0);
                                user.put("previousZones", new ArrayList<Zone>());
                                user.put("ID", userID);
                                DocumentReference documentReference = fStore.collection("users").document(userID);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d("TAG","SUCCESS," + userID);
                                    }
                                });
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Registration failed! Please try again later", Toast.LENGTH_LONG).show();

                            }
                        }
                    });

        }
    }

    /**
     * Redirects the user to the sign in page.
     * @param view
     */
    public void signInRedirect(View view){

        Intent myIntent = new Intent(this, SignInPageActivity.class);
        this.startActivity(myIntent);
    }

    /**
     * Hides the password information of passwordField1 or 2 according to the button
     * @param view
     */
    public void hideInfo(View view){
        EditText text;
        if(view.getId() == R.id.hidebutton1){
            text = (EditText)findViewById(R.id.passwordTextField);
            if(!isPassword1Visible){
                text.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                isPassword1Visible = true;
            }
            else{
                text.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                isPassword1Visible = false;
            }
            text.setSelection(text.length());
        }
        else if(view.getId() == R.id.hidebutton2){
            text = (EditText)findViewById(R.id.passwordTextField2);
            if(!isPassword2Visible){
                text.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                isPassword2Visible = true;
            }
            else{
                text.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                isPassword2Visible = false;
            }
            text.setSelection(text.length());
        }

    }
}