package com.example.navogation_with_pages.ui.object_classes;


/**
 * An interface for a listener to be invoked when a Zone object is successfully retrieved.
 */
public interface OnGetZoneListener {

    /**
     * Callback method to be invoked when a Zone object is successfully retrieved.
     *
     * @param zone The Zone object that was retrieved.
     */
    void onSuccess(Zone zone);
}