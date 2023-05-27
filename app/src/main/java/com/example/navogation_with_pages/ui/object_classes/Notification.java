package com.example.navogation_with_pages.ui.object_classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.navogation_with_pages.ui.notifications.NotificationsFragment;

public class Notification
{
    String name;
    String info;
    ImageView imageView;

    public Notification(String name, String info, ImageView imageView)
    {
        this.name      = name;
        this.info      = info;
        this.imageView = imageView;
    }

    public String getName() { return this.name; }

    public String getInfo() { return this.info; }

    public ImageView getImageView() { return this.imageView; }
}
