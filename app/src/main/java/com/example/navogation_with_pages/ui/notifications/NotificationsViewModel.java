package com.example.navogation_with_pages.ui.notifications;

import android.widget.Button;

import androidx.lifecycle.ViewModel;
import com.example.navogation_with_pages.Notification;
import com.example.navogation_with_pages.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationsViewModel extends ViewModel
{
    Button accept_button;
    Button delete_button;
    private List<Notification> notification_list;

    public NotificationsViewModel()
    {
        notification_list = new ArrayList<Notification>();
        notification_list.add(new Notification("Lale", "We are gaming...",null , false));
        notification_list.add(new Notification("Lila", "wants to be friends with you", null, false));
        notification_list.add(new Notification("Fatih", "In our trip to Capadocia...", null, false));
        notification_list.add(new Notification("Irmak", "A lil trip to Muğla, for details you can contact 05...", null,false));
        notification_list.add(new Notification("Aleem", "This might sound toxic but trust me, it's not, lol night.", null, false));
        notification_list.add(new Notification("Orhun", "Yes fellas, it finally happened...", null, false));
    }

    public void addNotification()
    {
        //if this is a zone notification, the zone must be either public or users must be friends in order to create the notification
    }

    public List<Notification> getNotification_list()
    {
        return this.notification_list;
    }
}