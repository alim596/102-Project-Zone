package com.example.navogation_with_pages.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.navogation_with_pages.ui.object_classes.User;
import com.example.navogation_with_pages.R;
import com.example.navogation_with_pages.ui.profile.FriendsListActivity;

import java.util.ArrayList;

public class FriendsAdapter extends BaseAdapter {

    FriendsListActivity friendsListActivity;
    ArrayList<User> friends;

    public FriendsAdapter(FriendsListActivity friendsListActivity, ArrayList<User> friends) {
        this.friendsListActivity = friendsListActivity;
        this.friends = friends;
    }

    @Override
    public int getCount() {
        if(friends == null){
            return 0;
        }
        return friends.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

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
