package com.example.navogation_with_pages.ui.add;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.navogation_with_pages.R;
import com.example.navogation_with_pages.Zone;
import com.example.navogation_with_pages.databinding.FragmentAddBinding;
import com.example.navogation_with_pages.ui.home.HomeViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AddFragment extends Fragment {

    private FragmentAddBinding binding;

    //UI elements
    private EditText name;
    private EditText location;
    private EditText details;
    private EditText quota;
    private DatePicker date;
    private RadioGroup publicPrivate;
    private EditText imageUrl;
    private String category;
    private HomeViewModel homeViewModel;
    private String[] categories;
    ArrayAdapter<String> arrayAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // ...
        binding = FragmentAddBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);


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
        publicPrivate = root.findViewById(R.id.radioGroup);

        TextInputLayout imageUrlLayout = root.findViewById(R.id.image_field);
        imageUrl = imageUrlLayout.getEditText();

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

                if (date == null) {
                    // Handle error
                    return;
                }

                if (publicPrivate == null) {
                    // Handle error
                    return;
                }

                // All required fields are filled, create the Zone object
                String dateStr = "" + date.getDayOfMonth() + "/" + (date.getMonth() + 1) + "/" + date.getYear();
                int quotaValue = Integer.parseInt(quota.getText().toString());
                Zone zone = new Zone(name.getText().toString(), quotaValue, dateStr, details.getText().toString(), location.getText().toString(), publicPrivate.getCheckedRadioButtonId(), imageUrl.getText().toString(), category);

                // Save the zone to the database
                homeViewModel.getDB().collection("zone").add(zone)
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
                imageUrl.setText("");
            }
        });

        return root;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}