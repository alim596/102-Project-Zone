package com.example.navogation_with_pages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.navogation_with_pages.ui.notifications.NotificationsFragment;

public class Notification extends AppCompatActivity
{
    Button accept_button;
    Button delete_button;
    String name;
    String info;
    ImageView imageView;
    boolean choosable = false;

    public Notification(String name, String info, ImageView imageView, boolean choosable)
    {
        this.choosable = choosable;
        this.name      = name;
        this.info      = info;
        this.imageView = imageView;

        if(choosable)
        {

        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_view_layout);
    }

    public String getName() { return this.name; }

    public String getInfo() { return this.info; }

    public ImageView getImageView() { return this.imageView; }
}
