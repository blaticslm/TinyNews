package com.example.tinynews.ui.country;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.tinynews.TinyNewsApplication;
import com.example.tinynews.databinding.FragmentCountryBinding;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CountryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CountryFragment extends Fragment {
    private FragmentCountryBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CountryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CountryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CountryFragment newInstance(String param1, String param2) {
        CountryFragment fragment = new CountryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCountryBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_country, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CountrySearchAdapter countrySearchAdapter = new CountrySearchAdapter(TinyNewsApplication.currentCountry);
        binding.countryList.setAdapter(countrySearchAdapter);

        //search country;
        binding.countryInputView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(query != null) {
                    countrySearchAdapter.getFilter(query);
                }

                binding.countryInputView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText == null || newText.length() == 0) {
                    countrySearchAdapter.reset();
                }
                return false;
            }
        });

        binding.countryList.setOnItemClickListener((adapterView, view1, i, l) -> {
            TinyNewsApplication.country_setter(String.valueOf(countrySearchAdapter.getItem(i).getKey()));
            TinyNewsApplication.country_full_name_setter(String.valueOf(countrySearchAdapter.getItem(i).getValue()));

            //popup window
            Toast.makeText(getActivity(), "Change country successfully!", Toast.LENGTH_SHORT).show();
        });
    }
}