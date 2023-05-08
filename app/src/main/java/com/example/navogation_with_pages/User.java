package com.example.navogation_with_pages;

import java.io.File;
import java.util.ArrayList;

public class User {
    //Login username
    private String username;

    //Login password
    private String password;

    //E-mail of the user
    private String email;

    //Not sure about what class the profile picture is supposed to be
    private File profilePicture;

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


    public User(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
        this.averageRating = 0;
        this.ratingCount = 0;
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









    // Getters, Setters and Add/Remove methods

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void setEmail(String newEmail){
        this.email = newEmail;
    }

    public File getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(File profilePicture) {
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
}
