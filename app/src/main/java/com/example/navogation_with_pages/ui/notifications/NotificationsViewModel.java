package com.example.navogation_with_pages.ui.notifications;

import androidx.lifecycle.ViewModel;
import com.example.navogation_with_pages.ui.object_classes.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationsViewModel extends ViewModel
{
    String friendsRequest = "sent you a friends request!";
    String zoneRequest = "sent you a Zone request!";
    private List<Notification> notification_list;

    public NotificationsViewModel()
    {
        notification_list = new ArrayList<Notification>();
        notification_list.add(new Notification("Lale", friendsRequest,null));
        notification_list.add(new Notification("Lila", zoneRequest, null));
        notification_list.add(new Notification("Irmak", zoneRequest, null));
        notification_list.add(new Notification("Aleem", friendsRequest, null));
        notification_list.add(new Notification("Orhun", zoneRequest, null));
    }

    public void addNotification(Notification ntf)
    {
        notification_list.add(ntf);
    }

    public List<Notification> getNotification_list()
    {
        return this.notification_list;
    }
}