package com.example.navogation_with_pages.ui.search;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.navogation_with_pages.R;
import com.example.navogation_with_pages.ui.object_classes.User;
import com.example.navogation_with_pages.ui.profile.OthersProfileActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class SearchProfileActivity extends AppCompatActivity implements SearchView.OnQueryTextListener
{
    private final FirebaseFirestore DB = FirebaseFirestore.getInstance();
    ListView listView;
    ArrayList<String> profileNames;
    SearchViewModel searchViewModel;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_profile);

        profileNames = new ArrayList<>();
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        loadUsers();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, profileNames);
        listView = findViewById(R.id.listview_search_profile);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                Intent intent = (new Intent(view.getContext(), OthersProfileActivity.class));
                startActivity(intent.putExtra("ID", searchViewModel.getUsers().getValue().get(position).getID()));
            }
        });
    }

    public void loadUsers()
    {
        DB.collection("users").get().addOnSuccessListener(queryDocumentSnapshots ->
        {
            ArrayList<User> users1 = new ArrayList<>();
            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
            {
                User user = documentSnapshot.toObject(User.class);
                users1.add(user);
                profileNames.add(user.getUsername());
            }
            searchViewModel.setUsers(users1); // Update the users LiveData in the ViewModel
        }).addOnFailureListener(e -> { Log.e("SearchProfileActivity", "Error loading users", e); });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search for profiles!");
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String s) { return false; }

    @Override
    public boolean onQueryTextChange(String s)
    {
        adapter.getFilter().filter(s);
        return false;
    }
}