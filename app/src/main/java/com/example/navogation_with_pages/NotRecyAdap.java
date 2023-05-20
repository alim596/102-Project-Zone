package com.example.navogation_with_pages;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotRecyAdap extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    public static class ViewHolder extends RecyclerView.ViewHolder
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

            card_view = (CardView)itemView.findViewById(R.id.card_view);
            person_name = (TextView)itemView.findViewById(R.id.person_name);
            small_info = (TextView)itemView.findViewById(R.id.small_info);
            person_img = (ImageView)itemView.findViewById(R.id.person_photo);
            accept_button = (Button)itemView.findViewById(R.id.accept_button);
            delete_button = (Button)itemView.findViewById(R.id.delete_button);
        }
    }

    List<Notification> notifications;

    public NotRecyAdap(List<Notification> notifications)
    {
        this.notifications = notifications;
    }

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
        ((ViewHolder) holder).person_name.setText(notifications.get(position).getName());
        ((ViewHolder) holder).small_info.setText(notifications.get(position).getInfo());
        //((ViewHolder) holder).person_img.setImageResource(notifications.get(position).getImageView());
    }

    @Override
    public int getItemCount()
    {
        return notifications.size();
    }
}
