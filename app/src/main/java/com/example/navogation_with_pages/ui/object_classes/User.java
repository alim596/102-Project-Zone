    package com.example.navogation_with_pages.ui.object_classes;

    import androidx.annotation.NonNull;


    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.firestore.CollectionReference;
    import com.google.firebase.firestore.DocumentReference;
    import com.google.firebase.firestore.DocumentSnapshot;
    import com.google.firebase.firestore.FirebaseFirestore;

    import java.util.ArrayList;
    import java.util.HashMap;
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

        private ArrayList<String> friendIDs;

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


        public void getFriends(OnGetUsersListener listener) {
            /*
            Object[] friendss = this.friends.toArray();
            ArrayList<User> users = new ArrayList<User>();
            for(Object object : friendss){
                Map<String, Object> snapshotValue = (HashMap<String,Object>)object;
                User user2 = new User();
                user2.setID((String)snapshotValue.get("id"));
                user2.setPassword((String)snapshotValue.get("password"));
                user2.setUsername((String) snapshotValue.get("username"));
                user2.setBiography((String)snapshotValue.get("biography"));
                user2.setPreviousZones((ArrayList<Zone>)snapshotValue.get("previousZones"));
                user2.setAverageRating(((Double)snapshotValue.get("averageRating")));
                if(!(snapshotValue.get("ratingCount").getClass() == Long.class)){
                    user2.setRatingCount(((Double) snapshotValue.get("ratingCount")));
                }
                else{
                    user2.setRatingCount(((Long) snapshotValue.get("ratingCount")).doubleValue());
                }

                user2.setEmail((String) snapshotValue.get("email"));
                user2.setFriends((ArrayList<User>) snapshotValue.get("friends"));
                users.add(user2);
            }
            return users;
             */
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

        public ArrayList<String> getFriendIDs() {
            return friendIDs;
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
