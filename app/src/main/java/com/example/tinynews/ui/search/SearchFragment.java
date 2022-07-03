package com.example.tinynews.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.tinynews.databinding.FragmentSearchBinding;
import com.example.tinynews.repository.NewRepository;
import com.example.tinynews.repository.NewViewModelFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    private SearchViewModel viewModel;
    private FragmentSearchBinding binding; //for searching option, now we can first officially use binding

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_search, container, false);
        return binding.getRoot(); //the fragmentContainerView object
    }

    //This view is FragmentContainerView, due to binding
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Recycler View
        SearchNewsAdapter newsAdapter = new SearchNewsAdapter();
        newsAdapter.setItemCallBack(article -> {
            SearchFragmentDirections.ActionNavigationSearchToNavigationDetails directions
                    = SearchFragmentDirections.actionNavigationSearchToNavigationDetails(article);
            NavHostFragment.findNavController(SearchFragment.this).navigate(directions);
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2); //2 col and 2 row

        //(res/layout/fragment_search)
        binding.newsResultRecyclerView.setLayoutManager(gridLayoutManager);
        binding.newsResultRecyclerView.setAdapter(newsAdapter);


        NewRepository newRepository = new NewRepository();

        viewModel = new ViewModelProvider(this, new NewViewModelFactory(newRepository)).get(SearchViewModel.class);

        //dealing with the text input
        binding.newsSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { //query is input
                if(!query.isEmpty()) {
                    viewModel.setSearchInput(query);
                }
                binding.newsSearchView.clearFocus(); //clear the typing bar
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        viewModel.searchNews().observe(getViewLifecycleOwner(), newsResponse -> {
            if(newsResponse != null) {
                //Log.d("SearchFragment", newsResponse.toString());
                newsAdapter.setArticles(newsResponse.articles);
            }
        });

    }
}