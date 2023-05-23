package com.example.navogation_with_pages;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.User;
import com.example.navogation_with_pages.ui.profile.OthersProfileActivity;
import com.squareup.picasso.Picasso;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.navogation_with_pages.R;
import com.example.navogation_with_pages.Zone;
import java.util.ArrayList;
import java.util.List;

public class ZonesRecViewAdapter extends RecyclerView.Adapter<ZonesRecViewAdapter.ViewHolder> {

    private ArrayList<Zone> zones = new ArrayList<>();
    LinearLayout participantsContainer2;

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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Zone currentZone = zones.get(position);
        holder.name.setText(zones.get(position).getName());
        holder.quota.setText(Integer.toString(zones.get(position).getQuota()));
        holder.date.setText(zones.get(position).getDateAndTime());
        holder.details.setText(zones.get(position).getDetails());
        holder.category.setText(zones.get(position).getCategory());

        //Adding participants into the zones
        holder.participantsContainer.removeAllViews();
        if(currentZone.getParticipants() != null){
            for (User user : currentZone.getParticipants()) {
                Button userButton = new Button(holder.itemView.getContext());
                userButton.setText(user.getUsername());
                userButton.setBackgroundColor(Color.TRANSPARENT); // remove button background to make it just text
                userButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(holder.itemView.getContext(), OthersProfileActivity.class);
                        intent.putExtra("ID", user.getID());
                        holder.itemView.getContext().startActivity(intent);
                    }
                });
                holder.participantsContainer.addView(userButton);
            }
        }


        //Expanding on click
        holder.zoneCrdView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewGroup cardParent = (ViewGroup) view.getParent();
                ViewGroup layout = (ViewGroup) cardParent.getParent();
                View hiddenLayout = view.findViewById(R.id.hidden);

                TransitionManager.beginDelayedTransition(layout);

                if (hiddenLayout.getVisibility() == View.GONE) {
                    hiddenLayout.setVisibility(View.VISIBLE);
                } else {
                    hiddenLayout.setVisibility(View.GONE);
                }
            }
        });
        // Load image into ImageView if imageUrl is not empty or null
        String imageUrl = zones.get(position).getImageUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get().load(imageUrl).into(holder.image);
        } else {
            // Set default image if imageUrl is empty or null
            holder.image.setImageResource(R.drawable.img);
        }

        holder.requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO set the implementation for the request button
            }
        });
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
        private TextView category;
        private CardView zoneCrdView;
        private RelativeLayout hidden;
        private LinearLayout participantsContainer;
        private Button requestBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            zoneCrdView = itemView.findViewById(R.id.zoneCrdView);
            name = itemView.findViewById(R.id.name);
            quota = itemView.findViewById(R.id.quota);
            date = itemView.findViewById(R.id.date);
            details = itemView.findViewById(R.id.details);
            image = itemView.findViewById(R.id.image);
            category = itemView.findViewById(R.id.category);
            hidden = itemView.findViewById(R.id.hidden);
            participantsContainer = itemView.findViewById(R.id.participantsContainer);
            requestBtn = itemView.findViewById(R.id.requestBtn);

        }
    }
}
