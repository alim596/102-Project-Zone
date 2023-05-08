package com.example.navogation_with_pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

public class SignInPageActivity extends AppCompatActivity {
    private boolean isPassword3Visible = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //A feeble attempt to remove the titlebar...
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_in_page);
        ((EditText)findViewById(R.id.passwordTextField3)).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }
    public void registerRedirect(View view){

        Intent myIntent = new Intent(this, RegisterpageActivity.class);
        this.startActivity(myIntent);
    }

    public void login(View view){
        Intent myIntent = new Intent(this, MainActivity.class);
        this.startActivity(myIntent);
    }
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