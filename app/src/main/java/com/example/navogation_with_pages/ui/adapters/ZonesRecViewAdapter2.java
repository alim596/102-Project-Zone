package com.example.navogation_with_pages.ui.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navogation_with_pages.R;
import com.example.navogation_with_pages.ui.object_classes.Zone;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ZonesRecViewAdapter2 extends RecyclerView.Adapter<ZonesRecViewAdapter2.ViewHolder> {

    private ArrayList<Zone> zones = new ArrayList<>();

    public ZonesRecViewAdapter2() {
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
        holder.quota.setText(Integer.toString(zones.get(position).getQuota()));
        holder.date.setText(zones.get(position).getDateAndTime());
        holder.details.setText(zones.get(position).getDetails());
        holder.category.setText(zones.get(position).getCategory());


        //Expanding on click
        holder.zoneCrdView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        // Load image into ImageView if imageUrl is not empty or null
        Uri imageUri = zones.get(position).getImageUri();
        if (imageUri != null) {
            Picasso.get().load(imageUri).into(holder.image);
        } else {
            // Set default image if imageUrl is empty or null
            holder.image.setImageResource(R.drawable.img);
        }
    }

    @Override
    public int getItemCount() {
        return zones.size();
    }

    public void setZones(ArrayList<Zone> zones) {
        this.zones = zones;
        notifyDataSetChanged();
    }

    public void updateZones(ArrayList<Zone> zones) {
        this.zones.clear();
        this.zones.addAll(zones);
        notifyDataSetChanged();
    }


    //This class gives objects that can hold CardView items to add to the home page
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView quota;
        private TextView date;
        private TextView details;
        private TextView category;

        private ImageView image;
        private CardView zoneCrdView;
        private RelativeLayout hidden;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            zoneCrdView = itemView.findViewById(R.id.zoneCrdView);
            name = itemView.findViewById(R.id.name);
            quota = itemView.findViewById(R.id.quota);
            date = itemView.findViewById(R.id.date);
            details = itemView.findViewById(R.id.details);
            category = itemView.findViewById(R.id.category);
            image = itemView.findViewById(R.id.image);
            hidden = itemView.findViewById(R.id.hidden);

        }
    }
}
