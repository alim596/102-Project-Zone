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

/**
 * ViewModel for the NotificationsFragment.
 * This ViewModel holds the notifications data that will survive configuration changes.
 */
public class NotificationsViewModel extends ViewModel {

    MutableLiveData<ArrayList<Notification>> notifications;

    /**
     * Default constructor. Initializes the MutableLiveData object that holds the notifications.
     */
    public NotificationsViewModel() {
        notifications = new MutableLiveData<>();
    }

    /**
     * Getter method for the notifications LiveData.
     *
     * @return LiveData that holds the notifications.
     */
    public LiveData<ArrayList<Notification>> getNotifications() { return notifications; };

    /**
     * Setter method for the notifications.
     *
     * @param notifications The new notifications to set.
     */
    public void setNotifications(ArrayList<Notification> notifications) { this.notifications.setValue(notifications);}
}