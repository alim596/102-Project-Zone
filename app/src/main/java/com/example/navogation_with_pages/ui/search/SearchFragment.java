package com.example.navogation_with_pages.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.navogation_with_pages.R;
import com.example.navogation_with_pages.databinding.FragmentSearchBinding;

/**
 * A fragment that allows users to search for profiles and zones.
 * The fragment displays a ListView with search options and handles item clicks to start corresponding search activities.
 */
public class SearchFragment extends Fragment {
    private FragmentSearchBinding binding;
    ListView listView;
    String[] searchItems = {"Profiles...", "Zones..."};
    ArrayAdapter<String> adapter;

    /**
     * Called when creating the view hierarchy of the fragment.
     * Inflates the layout and initializes the ListView with search options.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container          The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState A Bundle containing previously saved state.
     * @return The root View of the fragment's layout.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listView = root.findViewById(R.id.search_list_view);
        adapter = new Adapter(root.getContext(), searchItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                    startActivity(new Intent(view.getContext(), SearchProfileActivity.class));
                if (position == 1) {
                    startActivity(new Intent(view.getContext(), SearchZoneAct.class));
                }
            }
        });
        return root;
    }

    /**
     * Called when the fragment's view is destroyed.
     * Cleans up any resources associated with the fragment.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Custom ArrayAdapter for populating the ListView with search options.
     */
    class Adapter extends ArrayAdapter<String> {
        String[] options;

        /**
         * Constructs a new Adapter instance.
         *
         * @param context The context in which the adapter is being used.
         * @param options The array of search options to be displayed in the ListView.
         */
        public Adapter(@NonNull Context context, String[] options) {
            super(context, R.layout.searchfragment_listview, R.id.txt_options, options);
            this.options = options;
        }

        /**
         * Retrieves a View that displays the data at the specified position in the data set.
         *
         * @param position    The position of the item within the adapter's data set.
         * @param convertView The old view to reuse, if possible.
         * @param parent      The parent ViewGroup containing the returned view.
         * @return A View corresponding to the data at the specified position.
         */
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View line = layoutInflater.inflate(R.layout.searchfragment_listview, parent, false);
            TextView txt = line.findViewById(R.id.txt_options);
            txt.setText(options[position]);

            return line;
        }
    }
}
