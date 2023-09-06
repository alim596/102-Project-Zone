package com.example.navogation_with_pages.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

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