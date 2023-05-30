package com.example.navogation_with_pages.ui.add;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.navogation_with_pages.MainActivity;
import com.example.navogation_with_pages.R;
import com.example.navogation_with_pages.ui.object_classes.OnGetUserListener;
import com.example.navogation_with_pages.ui.object_classes.User;
import com.example.navogation_with_pages.ui.object_classes.Zone;
import com.example.navogation_with_pages.databinding.FragmentAddBinding;
import com.example.navogation_with_pages.ui.home.HomeViewModel;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.UUID;

public class AddFragment extends Fragment {

    private FragmentAddBinding binding;
    private FirebaseFirestore firestore;
    private StorageReference storageRef;
    private Uri imageChosen;

    //UI elements
    private EditText name;
    private EditText location;
    private EditText details;
    private EditText quota;
    private DatePicker date;
    private String category;
    private String[] categories;
    private ImageView image;

    ArrayAdapter<String> arrayAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // ...
        binding = FragmentAddBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        firestore = FirebaseFirestore.getInstance();

        // Create a StorageReference pointing to the root directory of your Firebase Storage
        storageRef = FirebaseStorage.getInstance().getReference();

        //Categories initialization
        categories = getResources().getStringArray(R.array.categories);
        arrayAdapter = new ArrayAdapter<>(requireContext(), R.layout.drop_down_category_item, categories);
        binding.selection.setAdapter(arrayAdapter);

        // Find UI elements
        TextInputLayout nameLayout = root.findViewById(R.id.name_field);
        name = nameLayout.getEditText();

        TextInputLayout locationLayout = root.findViewById(R.id.location_field);
        location = locationLayout.getEditText();

        TextInputLayout detailsLayout = root.findViewById(R.id.details_field);
        details = detailsLayout.getEditText();

        TextInputLayout quotaLayout = root.findViewById(R.id.quota_field);
        quota = quotaLayout.getEditText();

        date = root.findViewById(R.id.date_picker);
        // Get the current date.
        Calendar calendar = Calendar.getInstance();
        // Set the minimum date of the DatePicker to be today.
        date.setMinDate(calendar.getTimeInMillis());

        image = root.findViewById(R.id.image_field);
        imageChosen = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + getResources().getResourcePackageName(R.drawable.img)
                + '/' + getResources().getResourceTypeName(R.drawable.img)
                + '/' + getResources().getResourceEntryName(R.drawable.img));
        //Setting onClickListener for the image allowing the User to choose an image for their gallery
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
            }
        });

        // Set click listener for the post button
        binding.postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if required fields are empty
                if (TextUtils.isEmpty(name.getText())) {
                    name.setError("Required");
                    name.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(location.getText())) {
                    location.setError("Required");
                    location.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(details.getText())) {
                    details.setError("Required");
                    details.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(quota.getText())) {
                    quota.setError("Required");
                    quota.requestFocus();
                    return;
                }

                // All required fields are filled, create the Zone object
                String dateStr = "" + date.getDayOfMonth() + "/" + (date.getMonth() + 1) + "/" + date.getYear();
                int quotaValue = Integer.parseInt(quota.getText().toString());
                Zone zone = new Zone(name.getText().toString(), quotaValue, dateStr, details.getText().toString(),
                        location.getText().toString(), category);
                uploadImage(imageChosen, zone);
                FirebaseAuth fAuth = FirebaseAuth.getInstance();

                User.getUser(fAuth.getCurrentUser().getUid(), new OnGetUserListener() {
                    @Override
                    public void onSuccess(User user) {
                        zone.addParticipant(user);
                        // Save the zone to the database
                        firestore.collection("zones").add(zone)
                                .addOnSuccessListener(documentReference -> {
                                    String zoneId = documentReference.getId();
                                    Log.d("TAG", "Zone added with ID: " + zoneId);
                                })
                                .addOnFailureListener(e -> {
                                    Log.w("TAG", "Error adding zone", e);
                                });

                        // Show success message
                        Toast.makeText(getContext(), "Zone added successfully", Toast.LENGTH_SHORT).show();

                        // Clear the input fields
                        name.setText("");
                        location.setText("");
                        details.setText("");
                        quota.setText("");
                    }
                });

            }
        });

        return root;
    }

    public ImageView getImageView(){
        return image;
    }

    //Setting Category list field elements
    @Override
    public void onResume() {
        super.onResume();
        categories = getResources().getStringArray(R.array.categories);
        arrayAdapter = new ArrayAdapter<>(requireContext(), R.layout.drop_down_category_item, categories);
        binding.selection.setAdapter(arrayAdapter);
        binding.selection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                category  = parent.getItemAtPosition(position).toString();
            }
        });
    }

    //Uploading the Uri image chosen by the user to the database
    public void uploadImage(Uri image, Zone zone) {
        // Create a reference to the location where you want to store the image
        final StorageReference zoneRef = storageRef.child("zones/" + zone.getZoneID() + "/zone.jpg");
        zoneRef.putFile(image)
                .addOnSuccessListener(taskSnapshot -> {
                    zoneRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        zone.setImageUri(uri);
                        // Call a method to handle the success of image upload
                        handleImageUploadSuccess(uri, zone);

                    });
                })
                .addOnFailureListener(e -> {
                    // Handle the failure of image upload
                    Uri defUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                            "://" + getResources().getResourcePackageName(R.drawable.img)
                            + '/' + getResources().getResourceTypeName(R.drawable.img)
                            + '/' + getResources().getResourceEntryName(R.drawable.img));
                    zone.setImageUri(defUri);
                    Toast.makeText(getContext(), "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    // Method to handle the success of image upload
    private void handleImageUploadSuccess(Uri downloadUri, Zone zone) {
        // Set the download URL to the Zone object
        zone.setImageUri(downloadUri);
        // Show success message
        Toast.makeText(getContext(), "Zone added successfully", Toast.LENGTH_SHORT).show();

    }


    //Setting the imageView to the image chosen by the user before posting the zone
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000 && resultCode == RESULT_OK && data != null) {
            // Get the selected image Uri
            imageChosen = data.getData();
            // Set the image URI to the ImageView
            image.setImageURI(imageChosen);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

