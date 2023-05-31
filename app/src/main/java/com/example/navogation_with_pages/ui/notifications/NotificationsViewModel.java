package com.example.navogation_with_pages.ui.notifications;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.navogation_with_pages.ui.object_classes.Notification;

import com.example.navogation_with_pages.ui.object_classes.User;
import com.example.navogation_with_pages.ui.object_classes.Zone;

import java.util.ArrayList;
import java.util.List;

public class NotificationsViewModel extends ViewModel
{
    MutableLiveData<ArrayList<Notification>> notifications;

    public NotificationsViewModel()
    {
        notifications = new MutableLiveData<>();
    }
    public LiveData<ArrayList<Notification>> getNotifications() { return notifications; };
    public void setNotifications(ArrayList<Notification> notifications) { this.notifications.setValue(notifications);}
}