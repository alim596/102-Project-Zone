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
import com.example.navogation_with_pages.User;
import com.example.navogation_with_pages.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private User user;
    private TextView biography;
    private TextView name;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_profile, null);

        biography = (TextView) root.findViewById(R.id.biographyDisplay);
        name = (TextView) root.findViewById(R.id.usernameDisplay);
        user = new User("Orhun","epicpassword","orhun.guder@ug.bilkent.edu.tr");
        user.setBiography("HELLO I'M ORHUN AND I THINK FRAGMENTS ARE UNNECESSARILY HARD FOR NO REASON");
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
    /*


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user = new User("Orhun","epicpassword","orhun.guder@ug.bilkent.edu.tr");
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment, null);
        TextView but = (TextView) root.findViewById(R.id.text);
    }
    */

}