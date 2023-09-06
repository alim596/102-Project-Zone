package com.example.navogation_with_pages.ui.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.navogation_with_pages.R;
import com.example.navogation_with_pages.ui.object_classes.User;

import java.util.ArrayList;

/**
 * A custom adapter for displaying a list of friends in a ListView. Each item in the list
 * represents a friend, which is a User object.
 */
public class FriendsAdapter extends BaseAdapter {

    /**
     * The activity where the ListView is placed.
     */
    private FriendsListActivity friendsListActivity;

    /**
     * The list of User objects that will be displayed in the ListView.
     */
    private ArrayList<User> friends;

    /**
     * The constructor for the FriendsAdapter class.
     *
     * @param friendsListActivity The activity where the ListView is placed.
     * @param friends The list of User objects that will be displayed in the ListView.
     */
    public FriendsAdapter(FriendsListActivity friendsListActivity, ArrayList<User> friends) {
        this.friendsListActivity = friendsListActivity;
        this.friends = friends;
    }

    /**
     * Get the count of items in the list.
     *
     * @return The size of the friends list.
     */
    @Override
    public int getCount() {
        if(friends == null){
            return 0;
        }
        return friends.size();
    }

    /**
     * Get the item at the specified position in the list.
     *
     * @param i The index position of the item.
     * @return The index position.
     */
    @Override
    public Object getItem(int i) {
        return i;
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param i The index position of the item.
     * @return The index position.
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * Get a View that displays the data at the specified position in the data set.
     *
     * @param i The index position of the item.
     * @param view The old view to reuse, if possible.
     * @param viewGroup The parent that this view will eventually be attached to.
     * @return The View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(friendsListActivity).inflate(R.layout.friends_list_item, viewGroup, false);
        TextView textView;
        LinearLayout ll_bg;
        ll_bg = view.findViewById(R.id.ll_bg);
        textView = view.findViewById(R.id.friend_name);
        textView.setText(friends.get(i).getUsername());
        return view;
    }
}

