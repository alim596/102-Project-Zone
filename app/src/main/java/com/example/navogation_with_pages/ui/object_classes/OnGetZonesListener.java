package com.example.navogation_with_pages.ui.object_classes;

import java.util.ArrayList;

/**
 * An interface for a listener to be invoked when an ArrayList of Zone objects is successfully retrieved.
 */
public interface OnGetZonesListener {

    /**
     * Callback method to be invoked when an ArrayList of Zone objects is successfully retrieved.
     *
     * @param zones The ArrayList of Zone objects that was retrieved.
     */
    void onSuccess(ArrayList<Zone> zones);
}
