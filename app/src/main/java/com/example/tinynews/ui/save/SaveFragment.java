package com.example.tinynews.ui.save;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.tinynews.databinding.FragmentSaveBinding;
import com.example.tinynews.model.Article;
import com.example.tinynews.repository.NewRepository;
import com.example.tinynews.repository.NewViewModelFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SaveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SaveFragment extends Fragment {
    private SaveViewModel viewModel;
    private FragmentSaveBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SaveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SaveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SaveFragment newInstance(String param1, String param2) {
        SaveFragment fragment = new SaveFragment();
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

        binding = FragmentSaveBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_save, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Recycler view
        SavedNewsAdapter newsAdapter = new SavedNewsAdapter(); //all similar

        newsAdapter.setItemCallBack(new SavedNewsAdapter.ItemCallBack() {
            @Override
            public void onOpenDetails(Article article) {
                SaveFragmentDirections.ActionNavigationSaveToNavigationDetails directions = SaveFragmentDirections.actionNavigationSaveToNavigationDetails(article); //article byte stream from save to details fragment
                NavHostFragment.findNavController(SaveFragment.this).navigate(directions); //access to outer class: SaveFragment
            }

            @Override
            public void onRemoveFavorite(Article article) {
                viewModel.deleteSavedArticle(article);
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(),1);

        binding.newsSaveRecyclerView.setAdapter(newsAdapter);
        binding.newsSaveRecyclerView.setLayoutManager(gridLayoutManager);

        NewRepository newRepository = new NewRepository();
        viewModel = new ViewModelProvider(this, new NewViewModelFactory(newRepository)).get(SaveViewModel.class);

        viewModel.getAllsavedArticles().observe(getViewLifecycleOwner(), newsResponse -> {
            if(newsResponse != null) {
                newsAdapter.setArticles(newsResponse);
            }
        });
    }
}