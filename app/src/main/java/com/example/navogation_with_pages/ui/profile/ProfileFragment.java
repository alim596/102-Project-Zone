package com.example.navogation_with_pages.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.navogation_with_pages.R;
import com.example.navogation_with_pages.databinding.FragmentProfileBinding;
import com.example.navogation_with_pages.ui.object_classes.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

/**
 * A fragment that represents the user's profile.
 */
public class ProfileFragment extends Fragment {

    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private User user;
    private FragmentProfileBinding binding;

    private TextView biography;
    private TextView name;

    /**
     * Inflates the layout for the fragment and initializes the necessary components.
     *
     * @param inflater           The layout inflater object that can be used to inflate any views in the fragment.
     * @param container          The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState The previously saved state of the fragment.
     * @return The root view of the fragment layout.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        String userID = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this.getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && value.exists()) {
                    user = new User(value.getString("username"), value.getString("password"), value.getString("email"), value.getString("ID"));
                    user.setBiography(value.getString("biography"));
                    user.setAverageRating(value.getDouble("averageRating"));
                    user.setRatingCount(value.getDouble("ratingCount"));
                    user.setFriends((ArrayList<User>) value.get("friends"));

                    biography.setText(user.getBiography());
                    name.setText(user.getUsername());
                }
            }
        });

        biography = root.findViewById(R.id.biographyDisplay);
        name = root.findViewById(R.id.OtherUsernameDisplay);

        ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);

        return root;
    }

    /**
     * Clears the binding object when the fragment's view is destroyed.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
