package com.example.navogation_with_pages.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navogation_with_pages.R;
import com.example.navogation_with_pages.ui.object_classes.Notification;
import com.example.navogation_with_pages.ui.object_classes.OnGetUserListener;
import com.example.navogation_with_pages.ui.object_classes.OnGetZoneListener;
import com.example.navogation_with_pages.ui.object_classes.User;
import com.example.navogation_with_pages.ui.object_classes.Zone;

import java.util.ArrayList;
import java.util.List;

public class NotRecyAdap extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    ArrayList<Notification> notifications;

    public NotRecyAdap(ArrayList<Notification> notifications)
    {
        this.notifications = notifications;
    }

    public void setNotifications(ArrayList<Notification> notifications) { this.notifications = notifications; }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_layout, parent, false);

        final ViewHolder view_holder = new ViewHolder(v);

        return view_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        Notification notification = notifications.get(position);
        ((ViewHolder) holder).person_name.setText(notification.getInteracorUser().getUsername());
        ((ViewHolder) holder).small_info.setText(notification.getInfo());
    }

    @Override
    public int getItemCount() {
        if(notifications != null)
            return notifications.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView  person_name;
        public TextView  small_info;
        public ImageView person_img;
        public CardView  card_view;
        public Button    accept_button;
        public Button    delete_button;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);


            card_view = itemView.findViewById(R.id.card_view);
            person_name = itemView.findViewById(R.id.person_name);
            small_info = itemView.findViewById(R.id.small_info);
            person_img = itemView.findViewById(R.id.person_photo);
            accept_button = itemView.findViewById(R.id.accept_button);
            delete_button = itemView.findViewById(R.id.delete_button);

            accept_button.setOnClickListener(this);
            delete_button.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            if(view.getId() == R.id.accept_button)
            {
                User.getCurrentUser(new OnGetUserListener()
                {
                    @Override
                    public void onSuccess(User user)
                    {
                        Notification notification = NotRecyAdap.this.notifications.get(getAbsoluteAdapterPosition());

                        if(!NotRecyAdap.this.notifications.get(getAbsoluteAdapterPosition()).isZoneRequest())
                        {
                            user.addFriend(notification.getInteracorUser());
                            Toast.makeText(view.getContext(), "Friends request is accepted", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Zone.getZone(notification.getZoneID(), new OnGetZoneListener() {
                                @Override
                                public void onSuccess(Zone zone) {
                                    zone.addParticipant(notification.getInteracorUser());
                                }
                            });
                            Toast.makeText(view.getContext(), "Zone request is accepted!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
            if(view.getId() == R.id.delete_button)
                card_view.setVisibility(View.INVISIBLE);
        }
    }
}
