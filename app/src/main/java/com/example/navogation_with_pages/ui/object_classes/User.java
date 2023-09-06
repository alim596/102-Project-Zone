    package com.example.navogation_with_pages.ui.object_classes;

    import androidx.annotation.NonNull;


    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.firestore.DocumentReference;
    import com.google.firebase.firestore.DocumentSnapshot;
    import com.google.firebase.firestore.FirebaseFirestore;

    import java.util.ArrayList;
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

        private ArrayList<String> friendIDs;

        //The user's biography to be displayed in their profile
        private String biography;

        //The user's average rating that is displayed on their profile
        private double averageRating;

        //The user's rating count that is used to calculate average rating
        private double ratingCount;

        //An arrayList of the previous zones of the user
        private ArrayList<Zone> previousZones;

        public ArrayList<String> previousZoneIDs;


        public User(String username, String password, String email, String ID){
            this.ID = ID;
            this.previousZones = new ArrayList<Zone>();
            this.previousZoneIDs = new ArrayList<String>();
            this.username = username;
            this.password = password;
            this.email = email;
            this.averageRating = 0;
            this.ratingCount = 0;
            this.biography = "";
            this.friends = new ArrayList<User>();
            this.friendIDs = new ArrayList<String>();
        }

        public User(){}



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
                    if(snapshotValue.get("averageRating").getClass() == Long.class){
                        user.setAverageRating(((Long)snapshotValue.get("averageRating")).doubleValue());
                    }
                    else if(snapshotValue.get("averageRating").getClass() == Double.class){
                        user.setAverageRating(((Double)snapshotValue.get("averageRating")));
                    }
                    if(!(snapshotValue.get("ratingCount").getClass() == Long.class)){
                        user.setRatingCount(((Double) snapshotValue.get("ratingCount")));
                    }
                    else{
                        user.setRatingCount(((Long) snapshotValue.get("ratingCount")).doubleValue());
                    }
                    user.friendIDs = ((ArrayList<String>) snapshotValue.get("friendIDs"));
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
                        user.friendIDs = ((ArrayList<String>) snapshotValue.get("friendIDs"));
                        user.setEmail((String) snapshotValue.get("email"));
                        user.setFriends((ArrayList<User>) snapshotValue.get("friends"));
                        listener.onSuccess(user);
                    }
                });
            }


        //Check if the id's of two users are the same.
        public boolean equals(User anotherUser){
            if(this.getID().equals(anotherUser.getID())){
                return true;
            }
            return false;
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
        public void rateUser(User userToRate, double rating){
            double average = userToRate.getAverageRating();
            double ratingCount = userToRate.getRatingCount();
            double averageR = ((average)*ratingCount + rating)/(ratingCount + 1);
            userToRate.setAverageRating(averageR);
            String userID = userToRate.getID();

            FirebaseFirestore fStore = FirebaseFirestore.getInstance();

            DocumentReference documentReference = fStore.collection("users").document(userID);

            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    Map<String, Object> snapshotValue = task.getResult().getData();
                    snapshotValue.replace("averageRating",averageR);
                    snapshotValue.replace("ratingCount",(ratingCount + 1));
                    documentReference.update(snapshotValue);
                }
            });
        }

        /**
         * Returns a string representation of the user object.
         *
         * @return A string containing the username and biography of the user.
         */
        @Override
        public String toString() {
            return this.username + "\n" + this.biography;
        }

        /**
         * Sets the ID of the user.
         *
         * @param ID The new ID of the user.
         */
        public void setID(String ID) {
            this.ID = ID;
        }

        /**
         * Gets the username of the user.
         *
         * @return The username of the user.
         */
        public String getUsername() {
            return username;
        }

        /**
         * Sets the rating count of the user.
         *
         * @param ratingCount The new rating count of the user.
         */
        public void setRatingCount(double ratingCount) {
            this.ratingCount = ratingCount;
        }

        /**
         * Gets the rating count of the user.
         *
         * @return The rating count of the user.
         */
        public double getRatingCount() {
            return ratingCount;
        }

        /**
         * Sets the username of the user.
         *
         * @param username The new username of the user.
         */
        public void setUsername(String username) {
            this.username = username;
        }

        /**
         * Gets the ID of the user.
         *
         * @return The ID of the user.
         */
        public String getID() {
            return this.ID;
        }

        /**
         * Gets the password of the user.
         *
         * @return The password of the user.
         */
        public String getPassword() {
            return password;
        }

        /**
         * Sets the password of the user.
         *
         * @param password The new password of the user.
         */
        public void setPassword(String password) {
            this.password = password;
        }

        /**
         * Gets the email of the user.
         *
         * @return The email of the user.
         */
        public String getEmail() {
            return this.email;
        }

        /**
         * Sets the list of friends of the user.
         *
         * @param friends The new list of friends of the user.
         */
        public void setFriends(ArrayList<User> friends) {
            this.friends = friends;
        }

        /**
         * Sets the email of the user.
         *
         * @param newEmail The new email of the user.
         */
        public void setEmail(String newEmail) {
            this.email = newEmail;
        }

        /**
         * Gets the profile picture of the user.
         *
         * @return The profile picture of the user.
         */
        public String getProfilePicture() {
            return profilePicture;
        }

        /**
         * Sets the profile picture of the user.
         *
         * @param profilePicture The new profile picture of the user.
         */
        public void setProfilePicture(String profilePicture) {
            this.profilePicture = profilePicture;
        }


        /**
         * Retrieves the list of friends for the user.
         *
         * @param listener The listener to handle the retrieved list of friends.
         */
        public void getFriends(OnGetUsersListener listener) {
            FirebaseFirestore fStore = FirebaseFirestore.getInstance();

            ArrayList<User> friendsList = new ArrayList<User>();

            if(friendIDs == null){
                listener.onSuccess(null);
            }
            else{
                for(String userID : friendIDs){
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
                            if(snapshotValue.get("averageRating").getClass() == Long.class){
                                user.setAverageRating(((Long)snapshotValue.get("averageRating")).doubleValue());
                            }
                            else if(snapshotValue.get("averageRating").getClass() == Double.class){
                                user.setAverageRating(((Double)snapshotValue.get("averageRating")));
                            }
                            if(!(snapshotValue.get("ratingCount").getClass() == Long.class)){
                                user.setRatingCount(((Double) snapshotValue.get("ratingCount")));
                            }
                            else{
                                user.setRatingCount(((Long) snapshotValue.get("ratingCount")).doubleValue());
                            }
                            user.friendIDs = ((ArrayList<String>)(snapshotValue.get("friendIDs")));
                            user.setEmail((String) snapshotValue.get("email"));
                            user.setFriends((ArrayList<User>) snapshotValue.get("friends"));
                            friendsList.add(user);
                            listener.onSuccess(friendsList);
                        }
                    });
                }
            }

        }

        /**
         * Adds a user to the list of friends.
         *
         * @param user The user to add as a friend.
         */
        public void addFriend(User user) {
            if(this.friendIDs == null){
                ArrayList<String> strings = new ArrayList<>(1);
                strings.add(user.getID());
                this.friendIDs = strings;
            }
            else{
                this.friendIDs.add(user.getID());
            }
            setComponent("friendIDs",this.friendIDs);

            if(user.friendIDs == null){
                ArrayList<String> strings = new ArrayList<>(1);
                strings.add(this.getID());
                user.friendIDs = strings;
            }
            else{
                user.friendIDs.add(User.this.getID());
            }

            user.setComponent("friendIDs",user.friendIDs);
        }

        public void removeFriend(User user){
            this.friendIDs.remove(user.getID());
            setComponent("friendIDs",this.friendIDs);
            user.friendIDs.remove(this.getID());
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


        public void getPreviousZones(OnGetZonesListener listener) {
            ArrayList<Zone> zoness = new ArrayList<>();
            if(previousZoneIDs == null || previousZoneIDs.size() == 0){
                listener.onSuccess(zoness);
            }
            else{
                for(String ID : previousZoneIDs){
                    Zone.getZone(ID, new OnGetZoneListener() {
                        @Override
                        public void onSuccess(Zone zone) {
                            if(zone != null){
                                zoness.add(zone);
                            }
                                listener.onSuccess(zoness);

                        }
                    });
                }
            }

        }

        /**
         * Adds a previous zone to the user's list of previous zones.
         *
         * @param previousZone The previous zone to add.
         */
        public void addPreviousZone(Zone previousZone) {
            if(previousZoneIDs == null){
                previousZoneIDs = new ArrayList<>();
            }
            this.previousZoneIDs.add(previousZone.getZoneID());
        }

        /**
         * Sets the list of previous zones of the user.
         *
         * @param prevZones The new list of previous zones of the user.
         */
        public void setPreviousZones(ArrayList<Zone> prevZones){
            this.previousZones = prevZones;
        }

        /**
         * Gets the list of friend IDs of the user.
         *
         * @return The list of friend IDs of the user.
         */
        public ArrayList<String> getFriendIDs() {
            return friendIDs;
        }

    }
