package com.example.navogation_with_pages.ui.object_classes;

import androidx.annotation.NonNull;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class User {

    private static int IDCounter = -1;

    //Login username
    private String username;

    //The id of the user, also their position in the all users list.
    private String ID;

    //Login password
    private String password;

    //E-mail of the user
    private String email;

    //Not sure about what class the profile picture is supposed to be
    private String profilePicture;

    //An arraylist of the users all friends
    private ArrayList<User> friends;

    //The user's biography to be displayed in their profile
    private String biography;

    //The user's average rating that is displayed on their profile
    private double averageRating;

    //The user's rating count that is used to calculate average rating
    private double ratingCount;

    //An arrayList of the previous zones of the user
    private ArrayList<Zone> previousZones;


    public User(String username, String password, String email, String ID){
        this.ID = ID;
        this.previousZones = new ArrayList<Zone>();
        this.username = username;
        this.password = password;
        this.email = email;
        this.averageRating = 0;
        this.ratingCount = 0;
        this.biography = "";
        this.friends = new ArrayList<User>();
    }

    public User(){}

    private interface onGetUserReferenceListener{
        void onSuccess(DocumentReference documentReference);
    }

    /**
     * This method is written with inner methods in mind, but is still useful in other classes.
     * please make sure you know what you are doing when using this method.
     * @param key the hash key of the variable that you want to chang in user.
     * @param newValue the new value of the variable.
     */
    public void setComponent(String key, Object newValue){
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();


        String userID = ID;


        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Map<String, Object> snapshotValue = task.getResult().getData();
                snapshotValue.replace(key,newValue);
                documentReference.update(snapshotValue);
            }
        });
    }


    /**
     * This method first finds the user with the uid that you enter here.
     * Then, it executes the onSuccess method in the OnGetUserListener interface that you override.
     * (The parameter in that onSuccess method is the user with the id that you enter.)
     * @param ID the ID of the user you want to use the data of in some way.
     * @param listener
     */
    public static void getUser(String ID, final OnGetUserListener listener){
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();


        String userID = ID;


        DocumentReference documentReference = fStore.collection("users").document(userID);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                Map<String, Object> snapshotValue = task.getResult().getData();
                User user = new User();
                user.setID((String)snapshotValue.get("ID"));
                user.setPassword((String)snapshotValue.get("password"));
                user.setUsername((String) snapshotValue.get("username"));
                user.setBiography((String)snapshotValue.get("biography"));
                user.setPreviousZones((ArrayList<Zone>)snapshotValue.get("previousZones"));
                user.setAverageRating(((Double)snapshotValue.get("averageRating")));
                if(!(snapshotValue.get("ratingCount").getClass() == Long.class)){
                    user.setRatingCount(((Double) snapshotValue.get("ratingCount")));
                }
                else{
                    user.setRatingCount(((Long) snapshotValue.get("ratingCount")).doubleValue());
                }

                user.setEmail((String) snapshotValue.get("email"));
                user.setFriends((ArrayList<User>) snapshotValue.get("friends"));
                listener.onSuccess(user);
            }
        });
        }

        public static void getCurrentUser(final OnGetUserListener listener){
            FirebaseFirestore fStore = FirebaseFirestore.getInstance();


            String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();


            DocumentReference documentReference = fStore.collection("users").document(userID);

            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    Map<String, Object> snapshotValue = task.getResult().getData();
                    User user = new User();
                    user.setID((String)snapshotValue.get("ID"));
                    user.setPassword((String)snapshotValue.get("password"));
                    user.setUsername((String) snapshotValue.get("username"));
                    user.setBiography((String)snapshotValue.get("biography"));
                    user.setPreviousZones((ArrayList<Zone>)snapshotValue.get("previousZones"));
                    user.setAverageRating(((Double)snapshotValue.get("averageRating")));
                    if(!(snapshotValue.get("ratingCount").getClass() == Long.class)){
                        user.setRatingCount(((Double) snapshotValue.get("ratingCount")));
                    }
                    else{
                        user.setRatingCount(((Long) snapshotValue.get("ratingCount")).doubleValue());
                    }

                    user.setEmail((String) snapshotValue.get("email"));
                    user.setFriends((ArrayList<User>) snapshotValue.get("friends"));
                    listener.onSuccess(user);
                }
            });
        }





    /**
     * Adds the user to the zone.
     */
    public void addToZone(Zone zone){
        //Todo
    }

    /**
     * Call this method on User A to rate User B.
     * If you call the method on the same user, then it does nothing.
     * @param userToRate The user that is being rated.
     * @param rating The rating, from 0 to 10 (both inclusive)
     *
     */
    public void rateUser(User userToRate, int rating){
        //Todo
    }

    @Override
    public String toString(){
        return this.username + "\n" + this.biography;
    }


   public void setID(String ID){
        this.ID = ID;
   }


    // Getters, Setters and Add/Remove methods

    public String getUsername() {
        return username;
    }

    public void setRatingCount(double ratingCount) {
        this.ratingCount = ratingCount;

    }

    public double getRatingCount() {
        return ratingCount;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getID(){
        return this.ID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail(){
        return this.email;
    }

    public void setFriends(ArrayList<User> friends){
        this.friends = friends;
    }
    public void setEmail(String newEmail){
        this.email = newEmail;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }


    public ArrayList<User> getFriends() {
        return friends;
    }

    public void addFriend(User user) {
        this.friends.add(user);
    }

    public void removeFriend(User user){
        this.friends.remove(user);
    }

    public String getBiography() {
        return this.biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }


    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }


    public ArrayList<Zone> getPreviousZones() {
        return previousZones;
    }

    public void addPreviousZone(Zone previousZone) {
        this.previousZones.add(previousZone);
    }

    public void setPreviousZones(ArrayList<Zone> prevZones){
        this.previousZones = prevZones;
    }

    public void setDatabaseID(String ID) {
        this.setComponent("ID", ID);
    }

    public void setDatabaseUsername(String username) {
        this.setComponent("username", username);
    }

    public void setDatabasePassword(String password) {
        this.setComponent("password", password);
    }

    public void setDatabaseEmail(String email) {
        this.setComponent("email", email);
    }

    public void setDatabaseFriends(ArrayList<User> friends) {
        this.setComponent("friends", friends);
    }

    public void setDatabaseBiography(String biography) {
        this.setComponent("biography", biography);
    }

    public void setDatabaseAverageRating(double averageRating) {
        this.setComponent("averageRating", averageRating);
    }

    public void setDatabaseRatingCount(double ratingCount) {
        this.setComponent("ratingCount", ratingCount);
    }

    public void setDatabasePreviousZones(ArrayList<Zone> previousZones) {
        this.setComponent("previousZones", previousZones);
    }













}
