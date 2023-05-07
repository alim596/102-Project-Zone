package com.example.navogation_with_pages;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ZonesRecViewAdapter extends RecyclerView.Adapter<ZonesRecViewAdapter.ViewHolder> {

    private ArrayList<Zone> zones = new ArrayList<>();

    public ZonesRecViewAdapter() {
    }

    //This method creates an instance of the ViewHolder class for every item in our recyclerView.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*
        * The first parameter gets the xml file to inflate, the parent we want to attach the view to, and a boolean.
        * The boolean checks if we want to attach the view to the parent. Making it on would be redundant since we
        * already passed the parent. If we are not sure what the parent will be, we can pass null and true instead.
        * */

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zones_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(zones.get(position).getName());
        holder.quota.setText(zones.get(position).getQuota());
        holder.date.setText(zones.get(position).getDateAndTime());
        holder.details.setText(zones.get(position).getDetails());
        //holder.image.set(zones.get(position).getImageUrl());
    }

    @Override
    public int getItemCount() {
        return zones.size();
    }

    public void setZones(ArrayList<Zone> zones) {
        this.zones = zones;
        notifyDataSetChanged();
    }


    //This class gives objects that can hold CardView items to add to the home page
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView quota;
        private TextView date;
        private TextView details;
        private ImageView image;
        private CardView zoneCrdView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            zoneCrdView = itemView.findViewById(R.id.zoneCrdView);
            name = itemView.findViewById(R.id.name);
            quota = itemView.findViewById(R.id.quota);
            date = itemView.findViewById(R.id.date);
            details = itemView.findViewById(R.id.details);
            image = itemView.findViewById(R.id.image);
        }
    }
}
