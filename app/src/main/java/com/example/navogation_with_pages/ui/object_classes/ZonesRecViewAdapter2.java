package com.example.navogation_with_pages.ui.object_classes;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.navogation_with_pages.R;
import com.example.navogation_with_pages.ui.profile.OthersProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * <h1>ZonesRecViewAdapter Class</h1>
 * This class is responsible for handling the list of Zone objects
 * displayed in a RecyclerView. It inherits from RecyclerView.Adapter.
 * This class provides methods for setting, filtering and managing the displayed zones.
 * <p>
 * <b>Note:</b> Proper usage of this class ensures a dynamic and smooth display of zones in a RecyclerView.
 *
 * @author  Abdulaleem Altarsha
 * @version 1.0
 * @since   2023-05-31
 */

public class ZonesRecViewAdapter2 extends RecyclerView.Adapter<ZonesRecViewAdapter2.ViewHolder> {

    /**
     * This variable is used to hold the list of Zone objects
     * It's an ArrayList of Zone objects.
     */
    private ArrayList<Zone> zones = new ArrayList<>();
    LinearLayout participantsContainer2;

    /**
     * This is the default constructor for ZonesRecViewAdapter.
     */
    public ZonesRecViewAdapter2() {
    }

    /**
     * This method is used to create new views (invoked by the layout manager).
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zones_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    /**
     * This method is used to populate data into the item through holder at the given position.
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Zone currentZone = zones.get(position);
        holder.name.setText(zones.get(position).getName());
        holder.quota.setText(Integer.toString(zones.get(position).getQuota()));
        holder.date.setText(zones.get(position).getDateAndTime());
        holder.details.setText(zones.get(position).getDetails());
        holder.location.setText(zones.get(position).getLocation());
        holder.category.setText(zones.get(position).getCategory());


        //Adding participants into the zones
        holder.participantsContainer.removeAllViews();
        //TODO: presentation fix
        if(false && currentZone.getParticipants() != null){
            for (Object userr : currentZone.getParticipants()) {
                User.getUser((String) ((HashMap<String, Object>) (userr)).get("ID"), new OnGetUserListener() {
                    @Override
                    public void onSuccess(User user) {
                        Button userButton = new Button(holder.itemView.getContext());
                        userButton.setText(user.getUsername());
                        userButton.setBackgroundColor(Color.TRANSPARENT); // remove button background to make it just text
                        userButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(holder.itemView.getContext(), OthersProfileActivity.class);
                                intent.putExtra("ID", user.getID());
                                if(!user.getID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                                    holder.itemView.getContext().startActivity(intent);
                                }

                            }

                        });
                        holder.participantsContainer.addView(userButton);
                    }
                });


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
        Uri imageUri = zones.get(position).getImageUri();
        if (imageUri != null) {
            Picasso.get().load(imageUri).into(holder.image);
        } else {
            // Set default image if imageUrl is empty or null
            holder.image.setImageResource(R.drawable.img);
        }

        holder.requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    /**
     * This method is used to check whether an event represented by a Zone object has expired.
     * @param eventDateString A String representation of the event date.
     * @return boolean Returns true if the event has expired, false otherwise.
     */
    private boolean isEventExpired(String eventDateString) {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate eventDate = LocalDate.parse(eventDateString, dtf);
            return eventDate.isBefore(LocalDate.now());
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method is used to return the number of items in the data set held by the adapter.
     * @return int The number of items in the data set held by the adapter.
     */
    @Override
    public int getItemCount() {
        return zones.size();
    }

    /**
     * This method is used to update the list of zones in the adapter.
     * @param zones The new list of Zone objects to update the existing one.
     * @param dialog A ProgressDialog that indicates loading, it's hidden after zones are set.
     */
    public void setZones(ArrayList<Zone> zones, ProgressDialog dialog) {
        ArrayList<Zone> filteredZones = new ArrayList<>();
        for (Zone zone : zones) {
            if (!isEventExpired(zone.getDateAndTime())) {
                filteredZones.add(zone);
            }
        }
        this.zones = filteredZones;
        notifyDataSetChanged();
        dialog.hide();
    }

    /**
     * This method is used to update the list of zones in the adapter.
     * @param zones The new list of Zone objects to update the existing one.
     */
    public void setZones(ArrayList<Zone> zones) {
        ArrayList<Zone> filteredZones = new ArrayList<>();
        if(zones != null){
            for (Zone zone : zones) {
                if (zone != null && !isEventExpired(zone.getDateAndTime())) {
                    filteredZones.add(zone);
                }
            }
            this.zones = filteredZones;
            notifyDataSetChanged();
        }

    }

    /**
     * This class is used to hold the layout information for one instance of a RecyclerView item.
     * It extends RecyclerView.ViewHolder and is used to store references for child views.
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        /**
         * These variables represent the TextViews for name and quota in the item view layout.
         */
        private TextView name;
        private TextView quota;
        private TextView date;
        private TextView details;
        private TextView location;
        private ImageView image;
        private TextView category;
        private CardView zoneCrdView;
        private RelativeLayout hidden;
        private LinearLayout participantsContainer;
        public Button requestBtn;

        /**
         * This is the constructor for ViewHolder. It is used to initialize the child view references.
         * @param itemView The View used to obtain the references for the child views.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            zoneCrdView = itemView.findViewById(R.id.zoneCrdView);
            name = itemView.findViewById(R.id.name);
            quota = itemView.findViewById(R.id.quota);
            date = itemView.findViewById(R.id.date);
            details = itemView.findViewById(R.id.details);
            image = itemView.findViewById(R.id.image);
            location = itemView.findViewById(R.id.location);
            category = itemView.findViewById(R.id.category);
            hidden = itemView.findViewById(R.id.hidden);
            participantsContainer = itemView.findViewById(R.id.participantsContainer);
            requestBtn = itemView.findViewById(R.id.requestBtn);
            requestBtn.setVisibility(Button.INVISIBLE);
        }
    }
}
