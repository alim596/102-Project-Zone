package com.example.navogation_with_pages.ui.object_classes;


/**
 * An interface for listener to be invoked when a User object is successfully retrieved.
 */
public interface OnGetUserListener {

    /**
     * Callback method to be invoked when a User object is successfully retrieved.
     *
     * @param user The User object that was retrieved.
     */
    void onSuccess(User user);
}
