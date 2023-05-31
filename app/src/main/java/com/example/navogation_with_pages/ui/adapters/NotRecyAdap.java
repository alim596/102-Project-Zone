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

/**
 * An adapter for RecyclerView, used for displaying a list of notifications.
 */
public class NotRecyAdap extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    /**
     * The list of Notification objects that will be displayed in the RecyclerView.
     */
    private ArrayList<Notification> notifications;

    /**
     * The constructor for the NotRecyAdap class.
     *
     * @param notifications The list of Notification objects that will be displayed in the RecyclerView.
     */
    public NotRecyAdap(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    /**
     * Set the list of Notification objects that will be displayed in the RecyclerView.
     *
     * @param notifications The list of Notification objects.
     */
    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    /**
     * Create new ViewHolder object whenever RecyclerView needs a new one.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_layout, parent, false);

        final ViewHolder view_holder = new ViewHolder(v);

        return view_holder;
    }

    /**
     * Bind the data with the ViewHolder.
     *
     * @param holder   The ViewHolder to be updated.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        Notification notification = notifications.get(position);
        ((ViewHolder) holder).person_name.setText(notification.getInteracorUser().getUsername());
        ((ViewHolder) holder).small_info.setText(notification.getInfo());
    }

    /**
     * Get the size of the items that are in the notifications list.
     *
     * @return The size of the notifications list.
     */
    @Override
    public int getItemCount() {
        if(notifications != null)
            return notifications.size();
        else
            return 0;
    }

    /**
     * Provides a direct reference to each of the views within a data item. Used to cache the views within the item layout for fast access.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView  person_name;
        public TextView  small_info;
        public ImageView person_img;
        public CardView  card_view;
        public Button    accept_button;
        public Button    delete_button;

        /**
         * The constructor for the ViewHolder class. This constructor also sets the click listener on the accept and delete buttons.
         *
         * @param itemView The view to be managed by this holder.
         */
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

        /**
         * Handles the click events on the accept and delete buttons.
         *
         * @param view The view that was clicked.
         */
        @Override
        public void onClick(View view)
        {
            Notification notification = NotRecyAdap.this.notifications.get(getAbsoluteAdapterPosition());

            if(view.getId() == R.id.accept_button)
            {
                User.getCurrentUser(new OnGetUserListener()
                {
                    @Override
                    public void onSuccess(User user)
                    {
                        if(!NotRecyAdap.this.notifications.get(getAbsoluteAdapterPosition()).isZoneRequest())
                        {
                            user.addFriend(notification.getInteracorUser());
                            Toast.makeText(view.getContext(), "Friends request is accepted", Toast.LENGTH_LONG).show();
                            notification.delete();
                        }
                        else
                        {
                            Zone.getZone(notification.getZoneID(), new OnGetZoneListener() {
                                @Override
                                public void onSuccess(Zone zone) {
                                    zone.addParticipant(notification.getInteracorUser());
                                    notification.getInteracorUser().addPreviousZone(zone);
                                    notification.delete();
                                }
                            });
                            Toast.makeText(view.getContext(), "Zone request is accepted!", Toast.LENGTH_LONG).show();
                        }
                        card_view.setVisibility(View.INVISIBLE);
                    }
                });
            }
            if(view.getId() == R.id.delete_button){
                notification.delete();
            }

        }
    }
}
