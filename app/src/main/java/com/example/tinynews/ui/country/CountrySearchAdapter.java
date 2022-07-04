package com.example.tinynews.ui.country;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.tinynews.R;
import com.example.tinynews.databinding.CountryCodeNameBinding;

import java.util.ArrayList;
import java.util.Map;

public class CountrySearchAdapter extends BaseAdapter{

    private final ArrayList country_Code; //full country list
    private ArrayList country_Code_search;//searched country list

    public CountrySearchAdapter(Map<String, String> map) {
        country_Code = new ArrayList<>();
        country_Code.addAll(map.entrySet());
        country_Code_search = new ArrayList<>(country_Code);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return country_Code_search.size();
    }

    @Override
    public Map.Entry getItem(int position) {

        return (Map.Entry) country_Code_search.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final View result;
        if (view == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_code_name, parent, false); //matching to the layout
        } else {
            result = view;
        }

        CountryCodeNameBinding binding = CountryCodeNameBinding.bind(result);

        Map.Entry<String, String> item = getItem(position);
        binding.countryCode.setText(item.getKey());
        binding.countryName.setText(item.getValue());

        return result;
    }


    public void getFilter(String query) {
        country_Code_search.clear();
        query = query.toUpperCase();
        for(Object item: country_Code){
            if(((Map.Entry<String, String>) item).getKey().toUpperCase().equals(query) || ((Map.Entry<String, String>) item).getValue().toUpperCase().equals(query)) {
                country_Code_search.add(item);
                notifyDataSetChanged();
                break;
            }
        }
    }

    public void reset() {
        if(country_Code_search.size() < country_Code.size()) {
            country_Code_search.clear();
            country_Code_search.addAll(country_Code);
            notifyDataSetChanged();
        }
    }

}
