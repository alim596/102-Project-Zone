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

import com.example.navogation_with_pages.ui.object_classes.User;

import com.example.navogation_with_pages.R;
import com.example.navogation_with_pages.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private FirebaseAuth fAuth;

    private FirebaseFirestore fStore;

    private User user;
    private FragmentProfileBinding binding;

    private TextView biography;
    private TextView name;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_profile, null);


        FirebaseFirestore fStore = FirebaseFirestore.getInstance();

        final User[] user1 = new User[1];

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this.getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                user1[0] = new User(value.getString("username"), value.getString("password"), value.getString("email"), value.getString("ID"));
                user1[0].setBiography(value.getString("biography"));
                user1[0].setAverageRating(value.getDouble("averageRating"));
                user1[0].setRatingCount(value.getDouble("ratingCount"));
                user1[0].setFriends((ArrayList<User>) value.get("friends"));
            }
        });


        user =user1[0];

        biography = (TextView) root.findViewById(R.id.biographyDisplay);
        name = (TextView) root.findViewById(R.id.OtherUsernameDisplay);
        biography.setText(user.getBiography());
        name.setText(user.getUsername());
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}