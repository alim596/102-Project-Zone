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

public class SearchFragment extends Fragment
{
    private FragmentSearchBinding binding;
    ListView listView;
    String[] searchItems = {"Profiles...", "Zones..."};
    ArrayAdapter<String> adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listView = root.findViewById(R.id.search_list_view);
        adapter = new Adapter(root.getContext(), searchItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if(position == 0)
                    startActivity(new Intent(view.getContext(), SearchProfileActivity.class));
                if(position == 1)
                {
                    startActivity(new Intent(view.getContext(), SearchZoneAct.class));
                }
            }
        });
        return root;
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        binding = null;
    }

    class Adapter extends ArrayAdapter<String>
    {
        String[] options;
        public Adapter(@NonNull Context context, String[] options)
        {
            super(context, R.layout.searchfragment_listview, R.id.txt_options, options);
            this.options = options;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
        {
            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View line = layoutInflater.inflate(R.layout.searchfragment_listview, parent, false);
            TextView txt = line.findViewById(R.id.txt_options);
            txt.setText(options[position]);

            return line;
        }
    }
}