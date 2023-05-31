package com.example.navogation_with_pages.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * ViewModel class for the ProfileFragment.
 * Responsible for holding and managing the data for the fragment.
 */
public class ProfileViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    /**
     * Initializes the ProfileViewModel.
     */
    public ProfileViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is profile fragment");
    }

    /**
     * Retrieves the text LiveData object.
     *
     * @return The LiveData object containing the text.
     */
    public LiveData<String> getText() {
        return mText;
    }
}
