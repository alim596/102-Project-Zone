package com.example.navogation_with_pages.ui.object_classes;

import java.util.ArrayList;

/**
 * An interface for listener to be invoked when a list of User objects is successfully retrieved.
 */
public interface OnGetUsersListener {

    /**
     * Callback method to be invoked when a list of User objects is successfully retrieved.
     *
     * @param users The list of User objects that were retrieved.
     */
    void onSuccess(ArrayList<User> users);
}